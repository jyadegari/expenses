package jyad.services;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jyad.exceptions.CreateCsvFileException;
import jyad.model.Expense;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class CsvService {

    public void writeToResponse(PrintWriter writer, List<Expense> expenses) {

        ColumnPositionMappingStrategy<Expense> mappingStrategy = new ColumnPositionMappingStrategy<>();

        mappingStrategy.setType(Expense.class);
        mappingStrategy.setColumnMapping("name", "description", "amount", "date");

        StatefulBeanToCsv<Expense> csvBuilder = new StatefulBeanToCsvBuilder<Expense>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withMappingStrategy(mappingStrategy)
                .withSeparator(',')
                .build();

        try {
            csvBuilder.write(expenses);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new CreateCsvFileException("Error create csv file");
        }

    }
}
