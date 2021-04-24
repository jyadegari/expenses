package jyad.services.lucene;

import jyad.model.Expense;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class DocumentIndexer {


    @Value("${lucene.index-directory:./data/lucene}")
    private String indexDirectory;


    public void deleteAllIndexes() throws IOException {
        IndexWriter writer = null;
        try (Directory indexWriteToDir = FSDirectory.open(Paths.get(indexDirectory))) {
            writer = new IndexWriter(indexWriteToDir, new IndexWriterConfig());
            writer.deleteAll();
            writer.flush();
            writer.commit();
            writer.close();

        }
    }

    public void deleteDocument(String docId) throws IOException {
        IndexWriter writer = null;
        try (Directory indexWriteToDir = FSDirectory.open(Paths.get(indexDirectory))) {

            writer = new IndexWriter(indexWriteToDir, new IndexWriterConfig());
            writer.deleteDocuments(new Term("docId", docId));
            writer.flush();
            writer.commit();
            writer.close();

        }

    }

    public void updateDocumentIndex(String docId, Expense docToUpdate) throws IOException {
        Document docIndexToUpdate = createIndexDocument(docToUpdate);
        IndexWriter writer = null;

        try (Directory indexWriteToDir = FSDirectory.open(Paths.get(indexDirectory))) {
            writer = new IndexWriter(indexWriteToDir, new IndexWriterConfig());
            writer.updateDocument(new Term("docId", docId), docIndexToUpdate);
            writer.flush();
            writer.commit();
            writer.close();

        }
    }


    public void indexDocument(Document doc) throws IOException {
        IndexWriter writer = null;

        try (Directory indexWriteToDir = FSDirectory.open(Paths.get(indexDirectory))) {
            writer = new IndexWriter(indexWriteToDir, new IndexWriterConfig());
            writer.addDocument(doc);
            writer.flush();
            writer.commit();
            writer.close();
        }
    }

    public Document createIndexDocument(Expense expenseDoc) {

        Document document = new Document();

        IndexableField idField = new StringField("id", expenseDoc.getId().toString(), Field.Store.YES);
        IndexableField nameField = new TextField("name", expenseDoc.getName(), Field.Store.YES);
        IndexableField descriptionField = new TextField("description", expenseDoc.getDescription(), Field.Store.YES);
        IndexableField amountField = new TextField("amount", expenseDoc.getAmount().toString(), Field.Store.YES);
        IndexableField dateField = new StoredField("date", expenseDoc.getDate().toString());

        document.add(idField);
        document.add(nameField);
        document.add(descriptionField);
        document.add(amountField);
        document.add(dateField);


        return document;
    }


}
