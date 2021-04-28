package jyad.services.lucene;

import jyad.model.LuceneExpense;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentLocator {

    @Value("${lucene.index-directory:./data/lucene}")
    private String indexDirectory;

    @SuppressWarnings("all")
    public Document getDocumentById(String docId) throws IOException {
        Document retVal = null;
        try (
                Directory dirOfIndexes = FSDirectory.open(Paths.get(indexDirectory));
                StandardAnalyzer analyzer = new StandardAnalyzer()) {


            IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(dirOfIndexes));
            QueryBuilder queryBuilder = new QueryBuilder(analyzer);

            Query idQuery = queryBuilder.createPhraseQuery("docId", docId);

            TopDocs foundDocs = searcher.search(idQuery, 1);

            if (foundDocs != null &&
                    foundDocs.scoreDocs != null &&
                    foundDocs.scoreDocs.length > 0) {

                System.out.println("Score: " + foundDocs.scoreDocs[0].score);
                retVal = searcher.doc(foundDocs.scoreDocs[0].doc);

            }
        }

        return retVal;
    }


    public List<LuceneExpense> searchForDocument(String searchVal) throws IOException {

        List<LuceneExpense> retVal = new ArrayList<>();

        try (Directory dirOfIndexes = FSDirectory.open(Paths.get(indexDirectory))) {

            IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(dirOfIndexes));

            QueryBuilder builder = new QueryBuilder(new StandardAnalyzer());
            Query q1 = builder.createPhraseQuery("name", searchVal);
            Query q2 = builder.createPhraseQuery("description", searchVal);
            Query q3 = builder.createPhraseQuery("amount", searchVal);


            BooleanQuery.Builder chainQryBuildr = new BooleanQuery.Builder();
            chainQryBuildr.add(q1, BooleanClause.Occur.SHOULD);
            chainQryBuildr.add(q2, BooleanClause.Occur.SHOULD);
            chainQryBuildr.add(q3, BooleanClause.Occur.SHOULD);


            BooleanQuery finalQry = chainQryBuildr.build();

            TopDocs allFound = searcher.search(finalQry, 100);

            if (allFound.scoreDocs != null) {
                for (ScoreDoc doc : allFound.scoreDocs) {
                    System.out.println("Score: " + doc.score);

                    int docIndex = doc.doc;
                    Document docRetrieved = searcher.doc(docIndex);
                    if (docRetrieved != null) {
                        LuceneExpense docToAdd = new LuceneExpense();

                        IndexableField field = docRetrieved.getField("name");
                        if (field != null) {
                            docToAdd.setName(field.stringValue());
                        }

                        field = docRetrieved.getField("id");
                        if (field != null) {
                            docToAdd.setId(field.stringValue());
                        }

                        field = docRetrieved.getField("description");
                        if (field != null) {
                            docToAdd.setDescription(field.stringValue());
                        }

                        field = docRetrieved.getField("amount");
                        if (field != null) {
                            docToAdd.setAmount(field.stringValue());
                        }

                        field = docRetrieved.getField("date");
                        if (field != null) {
                            docToAdd.setDate(field.stringValue());
                        }

                        retVal.add(docToAdd);

                    }
                }
            }
        }

        return retVal;
    }

}
