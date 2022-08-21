package com.example.expensetracker.dto;

import com.example.expensetracker.model.Expense;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ExpenseMapper {
    Expense expenseDtoToExpense(ExpenseDto expenseDto);

    ExpenseDto expenseToExpenseDto(Expense expense);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Expense updateExpenseFromExpenseDto(ExpenseDto expenseDto, @MappingTarget Expense expense);
}
