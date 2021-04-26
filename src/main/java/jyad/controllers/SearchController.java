package jyad.controllers;


import jyad.exceptions.LuceneIndexingException;
import jyad.model.LuceneExpense;
import jyad.services.lucene.DocumentLocator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final DocumentLocator documentLocator;

    public SearchController(DocumentLocator documentLocator) {
        this.documentLocator = documentLocator;
    }

    @GetMapping("/search")
    public ResponseEntity<List<LuceneExpense>> findExpense(@RequestParam String query) {

        List<LuceneExpense> foundExpenses = null;

        try {
            foundExpenses = documentLocator.searchForDocument(query);
        } catch (IOException e) {
            throw new LuceneIndexingException("error finding expense from index");
        }
       return ResponseEntity.ok(foundExpenses);
    }

}
