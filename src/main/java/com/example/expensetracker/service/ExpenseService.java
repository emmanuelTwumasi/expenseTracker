package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseDto;
import com.example.expensetracker.exception.ApiException;

import javax.validation.constraints.NotNull;
import java.util.List;


public interface ExpenseService {
    List<ExpenseDto> findAll() throws ApiException;

    ExpenseDto saveExpense(ExpenseDto expenseDto) throws ApiException;

    ExpenseDto updateExpense(long id, ExpenseDto expenseDto) throws ApiException;

    ExpenseDto getExpense(@NotNull long id) throws ApiException;
}
