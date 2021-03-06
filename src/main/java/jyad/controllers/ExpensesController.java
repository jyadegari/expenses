package jyad.controllers;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jyad.model.Expense;
import jyad.services.ExpensesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class ExpensesController {


    private final ExpensesService service;

    public ExpensesController(ExpensesService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get all expenses")
    @Operation(description = "Get all expenses.", tags = {"Expense"})
    @ApiResponse(
            responseCode = "200",
            description = "gives list of all expenses.",
            content = @Content(schema = @Schema(implementation = Expense.class)))
    @GetMapping(value = "/expense", produces = {"application/json"})
    public ResponseEntity<List<Expense>> getAllExpense() {
        return ResponseEntity.ok(service.getAllExpenses());
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

        return ResponseEntity.status(HttpStatus.OK).body(service.findOneById(expenseId));
    }

    @ApiOperation(value = "Add or update an expense")
    @Operation(description = "Add an expense", tags = {"Expense"})
    @ApiResponse(
            responseCode = "201",
            description = "item created.",
            content = @Content(schema = @Schema(implementation = Expense.class)))
    @PostMapping(value = "/expense", consumes = {"application/json"})
    public ResponseEntity<Expense> addOrUpdateExpense(@Valid @RequestBody Expense expense) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addExpense(expense));
    }

    @ApiOperation(value = "Delete an expense by provide Id")
    @Operation(description = "Remove an expense by id", tags = {"Expense"})
    @ApiResponse(responseCode = "200", description = "item created.")
    @DeleteMapping(value = "/expense/{expenseId}")
    public ResponseEntity<String> deleteExpense(
            @ApiParam(value = "Id for the expense you need to delete", required = true)
            @PathVariable int expenseId) {
        service.deleteExpense(expenseId);
        return ResponseEntity.ok("Expense with id: " + expenseId + " deleted.");
    }



}
