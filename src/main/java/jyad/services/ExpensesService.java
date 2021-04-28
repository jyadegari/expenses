package jyad.services;


import jyad.exceptions.ExpenseNotFoundException;
import jyad.model.Expense;
import jyad.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpensesService {

    private final ExpenseRepository repository;

    public ExpensesService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public Expense findOneById(int id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("No expense found with id: " + id));

    }

    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }

    public void deleteExpense(int id) {

        if (!repository.existsById(id)) {
            throw new ExpenseNotFoundException("No expense found with id: " + id);
        }
        repository.deleteById(id);
    }

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    public List<Expense> findByDateBetween(LocalDate start, LocalDate end) {
        return repository.findByDateBetween(start, end);
    }
}
