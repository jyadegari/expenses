package jyad.controllers;


import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jyad.exceptions.CreateCsvFileException;
import jyad.exceptions.LuceneIndexingException;
import jyad.exceptions.ParseDateFromQueryException;
import jyad.model.Expense;
import jyad.model.LuceneExpense;
import jyad.repository.ExpenseRepository;
import jyad.services.CsvService;
import jyad.services.lucene.DocumentLocator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Search")
@Validated
public class SearchController {

    private final DocumentLocator documentLocator;
    private final ExpenseRepository expenseRepository;
    private final CsvService csvService;

    public SearchController(DocumentLocator documentLocator,
                            ExpenseRepository expenseRepository,
                            CsvService csvService) {
        this.documentLocator = documentLocator;
        this.expenseRepository = expenseRepository;
        this.csvService = csvService;
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

        List<LuceneExpense> foundExpenses;

        try {
            foundExpenses = documentLocator.searchForDocument(query);
        } catch (IOException e) {
            throw new LuceneIndexingException("error finding expense from index");
        }
        return ResponseEntity.ok(foundExpenses);
    }



    @ApiOperation(value = "Get a CSV file of expenses by start and end date provided", tags = "Search")
    @Parameter(
            in = ParameterIn.QUERY,
            name = "start",
            required = true,
            description = "query parameter for start date")
    @Parameter(
            in = ParameterIn.QUERY,
            name = "end",
            required = true,
            description = "query parameter for end date")
    @ApiResponse(
            responseCode = "200",
            description = "expenses found",
            content = @Content(mediaType = "text/csv"))

    @GetMapping(value = "/csv", produces = "text/csv")
    public void getCsvFile(@RequestParam String start,
                           @RequestParam String end,
                           HttpServletResponse response) {


        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate;
        LocalDate endDate;

        try {
            startDate = LocalDate.parse(start, formatter);
            endDate = LocalDate.parse(end, formatter);

        } catch (Exception e) {
            throw new ParseDateFromQueryException("Error parsing date. Date format should be like 'yyyy-MM-dd");
        }

        List<Expense> expenses = expenseRepository.findByDateBetween(startDate, endDate);

        try {
            csvService.writeToResponse(response.getWriter(), expenses);
        } catch (IOException e) {
            throw new CreateCsvFileException("Error create csv file");
        }

    }

}
