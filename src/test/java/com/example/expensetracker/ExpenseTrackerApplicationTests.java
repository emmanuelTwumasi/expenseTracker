package com.example.expensetracker;

import com.example.expensetracker.controller.ExpenseController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ExpenseTrackerApplicationTests {
    @Autowired
    ExpenseController expenseController;

    @DisplayName("Test if application loads correctly.")
    @Test
    void contextLoads() {
        assertThat(expenseController).isNotNull();
    }

}
