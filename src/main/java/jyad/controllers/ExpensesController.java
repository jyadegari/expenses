package jyad.controllers;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jyad.model.Expense;
import jyad.services.ExpensesService;
import jyad.services.lucene.DocumentIndexer;
import jyad.services.lucene.DocumentLocator;
import org.apache.lucene.document.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpensesController {

    private final ExpensesService expensesService;
    private final DocumentIndexer documentIndexer;
    private final DocumentLocator documentLocator;


    public ExpensesController(ExpensesService expensesService,
                              DocumentIndexer documentIndexer,
                              DocumentLocator documentLocator) {
        this.expensesService = expensesService;
        this.documentIndexer = documentIndexer;
        this.documentLocator = documentLocator;
    }

    @ApiOperation(value = "Get all expenses")
    @Operation(description = "Get all expenses.", tags = {"Expense"})
    @ApiResponse(
            responseCode = "200",
            description = "gives list of all expenses.",
            content = @Content(schema = @Schema(implementation = Expense.class)))
    @GetMapping(value = "/expense", produces = {"application/json"})
    public ResponseEntity<List<Expense>> getAllExpense() {
        return ResponseEntity.ok(expensesService.getAllExpenses());
    }

    @ApiOperation(value = "Get an expense by provide Id")
    @Operation(description = "Get a specified expense by id", tags = {"Expense"})
    @ApiResponse(
            responseCode = "200",
            description = "shows selected expense based on expense id.",
            content = @Content(schema = @Schema(implementation = Expense.class)))
    @GetMapping(value = "/expense/{expenseId}", produces = {"application/json"})
    public ResponseEntity<Expense> getExpenseById(
            @ApiParam(value = "Id for the expense you need to retrieve", required = true)
            @PathVariable("expenseId") int expenseId) {

        return ResponseEntity.status(HttpStatus.OK).body(expensesService.findOneById(expenseId));
    }

    @ApiOperation(value = "Add or update an expense")
    @Operation(description = "Add an expense", tags = {"Expense"})
    @ApiResponse(
            responseCode = "201",
            description = "item created.",
            content = @Content(schema = @Schema(implementation = Expense.class)))
    @PostMapping(value = "/expense", consumes = {"application/json"})
    public ResponseEntity<Expense> addOrUpdateExpense(@Valid @RequestBody Expense expense) {

        Expense savedExpense = expensesService.addExpense(expense);

        try {
            if (expense.getId() != null) {
                documentIndexer.updateDocumentIndex(savedExpense.getId().toString(), savedExpense);
            } else {
                documentIndexer.indexDocument(documentIndexer.createIndexDocument(savedExpense));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }


        return ResponseEntity.ok("Expense with id: " + expenseId + " deleted.");
    }


}
