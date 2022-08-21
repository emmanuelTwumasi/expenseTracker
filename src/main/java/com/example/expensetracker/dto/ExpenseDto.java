package com.example.expensetracker.dto;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpenseDto implements Serializable {
    private Long id;
    @NotBlank(message = "can not be empty")
    private String expense;
    @NotBlank(message = "can not be empty")
    @Size(min = 3, message = "should have at least three characters")
    private String description;
    @Min(0)
    @DecimalMin("0.0")
    @NotNull(message = "can not be empty")
    private Double amount;
}
