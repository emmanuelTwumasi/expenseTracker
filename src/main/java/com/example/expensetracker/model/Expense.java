package com.example.expensetracker.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "expenses")
@ToString
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
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
