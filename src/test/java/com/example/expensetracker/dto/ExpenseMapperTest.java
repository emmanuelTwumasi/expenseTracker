package com.example.expensetracker.dto;

import com.example.expensetracker.model.Expense;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;


public class ExpenseMapperTest {

    @Mock
    private ExpenseMapper expenseMapper = Mappers.getMapper(ExpenseMapper.class);

    @DisplayName("Convert Dto to entity with success")
    @Test
    public void expenseDtoToExpense() {

        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpense("yes");
        expenseDto.setId(1L);

        Expense expense = expenseMapper.expenseDtoToExpense(expenseDto);

        assertThat(expense.getId()).isEqualTo(expenseDto.getId());
        assertThat(expense.getExpense()).isEqualTo(expenseDto.getExpense());
    }

    @DisplayName("Convert entity to dto with success.")
    @Test
    public void expenseToExpenseDto() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setExpense("expense");

        ExpenseDto expenseDto = expenseMapper.expenseToExpenseDto(expense);

        assertThat(expense.getId()).isEqualTo(expenseDto.getId());
        assertThat(expense.getExpense()).isEqualTo(expenseDto.getExpense());
    }

    @DisplayName("Update an entity with dto.")
    @Test
    public void updateExpenseFromExpenseDto() {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setId(1L);
        expenseDto.setExpense("expense");

        Expense expense = new Expense();
        expense.setId(2L);

        Expense expense1 = expenseMapper.updateExpenseFromExpenseDto(expenseDto, expense);

        assertThat(expenseDto.getExpense()).isEqualTo(expense1.getExpense());
        assertThat(expenseDto.getId()).isEqualTo(expense1.getId());
    }
}