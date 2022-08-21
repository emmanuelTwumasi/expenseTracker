package com.example.expensetracker.controller;

import com.example.expensetracker.dto.ExpenseDto;
import com.example.expensetracker.dto.ExpenseMapper;
import com.example.expensetracker.service.ExpenseServiceImpl;
import com.example.expensetracker.utility.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {
    @Autowired
    private ExpenseController expenseController;
    @MockBean
    ExpenseServiceImpl expenseService;

    @Mock
    ExpenseMapper mapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void whenUserControllerInjected_thenNotNull() {
        assertThat(expenseController).isNotNull();
    }

    @DisplayName("Get all expenses available")
    @Test
    void getAll() throws Exception {
        List<ExpenseDto> expenseDto = List.of(ExpenseDto.builder().id(1L).expense("me").build());

        when(expenseService.findAll()).thenReturn(expenseDto);

        mockMvc.perform(get("/api/v1/expenses/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].expense", Matchers.is("me")));
        verify(expenseService,times(1)).findAll();
        reset(expenseService);
    }

    @Test
    void save() throws Exception {

        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAmount(10.0);
        expenseDto.setDescription("you");
        expenseDto.setExpense("me");

        when(expenseService.saveExpense(Mockito.any())).thenReturn(expenseDto);

        mockMvc.perform(post("/api/v1/expenses/expense").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(expenseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath ("$.expense", Matchers.is("me")));
        verify(expenseService, times(1)).saveExpense(Mockito.any());
        reset(expenseService);
    }

    @Test
    void updateExpense() {
    }

    @Test
    void getExpense() {
    }

    @Test
    void deleteExpense_success() throws Exception {
        mockMvc.perform(delete("/api/v1/expenses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(expenseService,times(1)).deleteExpense(Mockito.anyLong());
        reset(expenseService);
    }
}