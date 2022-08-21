package com.example.expensetracker.service;

import com.example.expensetracker.dto.ExpenseDto;
import com.example.expensetracker.dto.ExpenseMapper;
import com.example.expensetracker.exception.ApiException;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    ExpenseMapper expenseMapper;
    @InjectMocks
    ExpenseServiceImpl expenseService = new ExpenseServiceImpl();

    @Mock
    AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @DisplayName("Get all expenses with success")
    @Test
    void findALl() throws ApiException {

        //given
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setExpense("first");

        List<Expense> expenseList = List.of(expense);

        when(expenseRepository.findAll()).thenReturn(expenseList);

        //test
        List<ExpenseDto> expenseDtoList = expenseService.findAll();
        assertThat(expenseList.size()).isEqualTo(expenseDtoList.size());

        verify(expenseRepository, times(1)).findAll();
        reset(expenseRepository);
    }

    @DisplayName("Save expenses with success")
    @Test
    void saveExpense() throws ApiException {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setExpense("ui");

        //given
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setId(1L);
        expenseDto.setExpense("ui");

        when(expenseRepository.save(expense)).thenReturn(expense);
        when(expenseMapper.expenseToExpenseDto(expense)).thenReturn(expenseDto);
        when(expenseMapper.expenseDtoToExpense(expenseDto)).thenReturn(expense);

        ExpenseDto expense2 = expenseService.saveExpense(expenseDto);

        assertThat(expense2).isNotNull();
        assertThat(expense2.getId()).isEqualTo(expenseDto.getId());

        verify(expenseRepository,times(1)).save(expense);
        reset(expenseRepository);

    }

    @DisplayName("Update expense with success")
    @Test
    void updateExpense() throws ApiException {
        //before
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setExpense("ui");

        when(expenseRepository.findById(expense.getId())).thenReturn(Optional.of(expense));
        when(expenseRepository.save(expense)).thenReturn(expense);


        //given
        long id = 1L;
        ExpenseDto dto = new ExpenseDto();
        dto.setId(1L);
        dto.setExpense("juice");

        when(expenseMapper.expenseToExpenseDto(expense)).thenReturn(dto);

        //test
        ExpenseDto expenseDto = expenseService.updateExpense(id, dto);
        assertThat(expenseDto.getExpense()).isEqualTo(dto.getExpense());

        verify(expenseRepository, times(1)).save(expense);
        reset(expenseRepository);
    }

    @DisplayName("get aa expense with success")
    @Test
    void getExpense() throws ApiException {

        //before
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setExpense("ui");

        ExpenseDto dto = new ExpenseDto();
        dto.setId(1L);

        when(expenseMapper.expenseToExpenseDto(expense)).thenReturn(dto);

        when(expenseRepository.findById(expense.getId())).thenReturn(Optional.of(expense));

        //given
        long id = 1L;
        ExpenseDto found = expenseService.getExpense(id);

        //test
        assertThat(found.getId()).isEqualTo(expense.getId());
        verify(expenseRepository, times(1)).findById(id);
        reset(expenseRepository);
    }

    @DisplayName("Delete with success")
    @Test
    void deleteExpense(){
        Expense expense = new Expense();
        expense.setId(1L);

        //given
        Long id = 1L;
        expenseRepository.deleteById(id);

        verify(expenseRepository,times(1)).deleteById(id);

        reset(expenseRepository);
    }
}