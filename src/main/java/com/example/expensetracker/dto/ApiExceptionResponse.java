package com.example.expensetracker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class ApiExceptionResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime time;
}
