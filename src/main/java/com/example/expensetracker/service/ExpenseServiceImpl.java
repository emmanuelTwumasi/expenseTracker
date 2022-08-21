package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseDto;
import com.example.expensetracker.dto.ExpenseMapper;
import com.example.expensetracker.exception.ApiException;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    ExpenseMapper expenseMapper;


    /**
     * @return
     * @throws ApiException
     */
    @Override
    public List<ExpenseDto> findAll() throws ApiException {
        var expense = expenseRepository.findAll().stream()
                .map(expenseMapper::expenseToExpenseDto).collect(Collectors.toList());
        if (!expense.isEmpty()) {
            return expense;
        } else {
            throw new ApiException("No expenses available", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param expenseDto
     * @return
     * @throws ApiException
     */
    @Override
    public ExpenseDto saveExpense(@Valid ExpenseDto expenseDto) throws ApiException {

        if (expenseDto.getId()!=null){
            this.updateExpense(expenseDto.getId(),expenseDto);
        }
        Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);
        return expenseMapper.expenseToExpenseDto(expenseRepository.save(expense));
    }

    /**
     * @param expenseDto
     * @return
     * @throws ApiException
     */
    @Override
    public ExpenseDto updateExpense(@NotNull long id, @Valid ExpenseDto expenseDto) throws ApiException {
        if (expenseDto.getId() == null) {
            throw new ApiException("Expense  id can not be null", HttpStatus.BAD_REQUEST);
        }
        Expense expense = expenseRepository.findById(id).orElseThrow(
                () -> new ApiException(String.format("Expense with id: %d can not be found ",
                        expenseDto.getId()), HttpStatus.NOT_FOUND));
        expenseMapper.updateExpenseFromExpenseDto(expenseDto, expense);
        return expenseMapper.expenseToExpenseDto(expenseRepository.save(expense));
    }

    /**
     * @param id
     * @return
     * @throws ApiException
     */
    public ExpenseDto getExpense(@NotNull long id) throws ApiException {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(String.format("Expense with id:%d can not be found", id),
                                HttpStatus.NOT_FOUND));
        return expenseMapper.expenseToExpenseDto(expense);
    }

    /**
     * @param id
     * @return
     * @throws ApiException
     */
    public void deleteExpense(@NotNull long id) throws ApiException {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(String.format("Expense with id:%d can not be found", id),
                                HttpStatus.NOT_FOUND));
       expenseRepository.deleteById(expense.getId());
    }
}
