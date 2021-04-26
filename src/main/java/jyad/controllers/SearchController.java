package jyad.controllers;


import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Search")
public class SearchController {

    private final DocumentLocator documentLocator;

    public SearchController(DocumentLocator documentLocator) {
        this.documentLocator = documentLocator;
    }

    @ApiOperation(value = "Get list of expenses by search query provided", tags = "Search")
    @Parameter(
            in = ParameterIn.QUERY,
            name = "query",
            required = true,
            description = "query parameter for search through all expenses")
    @ApiResponse(
            responseCode = "200",
            description = "expenses found",
            content = @Content(schema = @Schema(implementation = LuceneExpense.class)))
    @GetMapping("/search")
    public ResponseEntity<List<LuceneExpense>> findExpense(@RequestParam String query) {

        List<LuceneExpense> foundExpenses ;

        try {
            foundExpenses = documentLocator.searchForDocument(query);
        } catch (IOException e) {
            throw new LuceneIndexingException("error finding expense from index");
        }
       return ResponseEntity.ok(foundExpenses);
    }

}
