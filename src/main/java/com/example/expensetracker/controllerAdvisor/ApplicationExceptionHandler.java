package com.example.expensetracker.controllerAdvisor;


import com.example.expensetracker.dto.ApiExceptionResponse;
import com.example.expensetracker.exception.ApiException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = fieldError.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleInvalidException(TypeMismatchException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("property_",ex.getPropertyName());
        errors.put("message",ex.getMessage());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String,String>> handleInvalidException(InvalidFormatException ex){
        Map<String, String> errors = new HashMap<>();
        errors.put("property_", String.valueOf(ex.getTargetType()));
        errors.put("message",ex.getMessage());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handleException(ApiException e) {
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder()
                .message(e.getMessage())
                .status(e.getStatus())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(apiExceptionResponse,e.getStatus());
    }
}
