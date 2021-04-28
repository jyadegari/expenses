package jyad.controllers;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jyad.exceptions.LuceneIndexingException;
import jyad.model.Expense;
import jyad.services.ExpensesService;
import jyad.services.lucene.DocumentIndexer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Expense")
public class ExpensesController {

    private final ExpensesService expensesService;
    private final DocumentIndexer documentIndexer;


    public ExpensesController(ExpensesService expensesService,
                              DocumentIndexer documentIndexer) {
        this.expensesService = expensesService;
        this.documentIndexer = documentIndexer;
    }

    @ApiOperation(value = "Find all expenses", response = Expense.class, tags = "Expense")
    @ApiResponse(responseCode = "200", description = "expenses found")
    @GetMapping(value = "/expense", produces = {"application/json"})
    public ResponseEntity<List<Expense>> getAllExpense() {
        return ResponseEntity.ok(expensesService.getAllExpenses());
    }

    @ApiOperation(
            value = "Find an expense by id",
            notes = "Provide an id to lookup specific expense",
            response = Expense.class,
            tags = "Expense")
    @GetMapping(value = "/expense/{expenseId}", produces = {"application/json"})
    public ResponseEntity<Expense> getExpenseById(
            @ApiParam(value = "Id for the expense you need to retrieve", required = true)
            @PathVariable("expenseId") int expenseId) {

        return ResponseEntity.status(HttpStatus.OK).body(expensesService.findOneById(expenseId));
    }

    @ApiOperation(value = "Add or update an expense", tags = "Expense")
    @ApiResponse(
            responseCode = "201",
            description = "item created.",
            content = @Content(schema = @Schema(implementation = Expense.class)))
    @PostMapping(value = "/expense", consumes = {"application/json"})
    public ResponseEntity<Expense> addOrUpdateExpense(@Valid @RequestBody Expense expense) {

        var savedExpense = expensesService.addExpense(expense);

        try {
            if (expense.getId() != null) {
                documentIndexer.updateDocumentIndex(savedExpense.getId().toString(), savedExpense);
            } else {
                documentIndexer.indexDocument(documentIndexer.createIndexDocument(savedExpense));
            }
        } catch (IOException e) {
            throw new LuceneIndexingException("error adding expense to index");
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    @ApiOperation(value = "Delete an expense by provide Id")
    @Operation(description = "Remove an expense by id", tags = {"Expense"})
    @ApiResponse(responseCode = "200", description = "item created.")
    @DeleteMapping(value = "/expense/{expenseId}")
    public ResponseEntity<String> deleteExpense(
            @ApiParam(value = "Id for the expense you need to delete", required = true)
            @PathVariable int expenseId) {
        expensesService.deleteExpense(expenseId);

        try {
            documentIndexer.deleteDocument(String.valueOf(expenseId));
        } catch (IOException e) {
            throw new LuceneIndexingException("error deleting expense from index");
        }


        return ResponseEntity.ok("Expense with id: " + expenseId + " deleted.");
    }


}
