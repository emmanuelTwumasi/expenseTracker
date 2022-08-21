package com.example.expensetracker.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApiException extends Exception {
    private HttpStatus status;

    /**
     * @param message
     */
    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }
}
