package com.example.expensetracker.controller;

import com.example.expensetracker.dto.ExpenseDto;
import com.example.expensetracker.exception.ApiException;
import com.example.expensetracker.service.ExpenseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/expenses")
@Slf4j
public class ExpenseController {
    @Autowired
    ExpenseServiceImpl expenseService;

//    Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @GetMapping("/")
    public ResponseEntity<List<ExpenseDto>> getAll() throws ApiException {
        return new ResponseEntity<>(expenseService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/expense")
    public ResponseEntity<ExpenseDto> save(@RequestBody @Valid ExpenseDto expenseDto) throws ApiException {
        if (expenseDto == null){
            log.error("Save error: Expense can not be null");
            throw new ApiException("Expense can not be null or id can not be null",HttpStatus.BAD_REQUEST);
        }

        ExpenseDto expense = expenseService.saveExpense(expenseDto);
        log.info("Save successful: expense with id {} save",expense.toString());
        return new ResponseEntity<>(expense, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ExpenseDto> updateExpense(@RequestParam("id") long id, @RequestBody ExpenseDto expenseDto) throws ApiException {
        return new ResponseEntity<>(expenseService.updateExpense(id, expenseDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpense(@PathVariable long id) throws ApiException {
        return new ResponseEntity<>(expenseService.getExpense(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable long id) throws ApiException {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
