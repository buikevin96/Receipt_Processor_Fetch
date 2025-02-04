package com.fetch.receiptProcessor.Exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Handle the exception
        System.out.println("GlobalExceptionHandler:  Failed to convert UUID: " + ex.getMessage());
        // Perform additional error handling logic if needed
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        // Handle the exception
        System.out.println("GlobalExceptionHandler:  HttpRequestMethodNotSupportedException: " + ex.getMessage());
        // Perform additional error handling logic if needed
    }



    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException(ChangeSetPersister.NotFoundException ex) {
        // Handle the exception
        System.out.println("GlobalExceptionHandler: NOT_FOUND: " + ex.getMessage());
        // Perform additional error handling logic if needed
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleInternalServerError(Exception ex) {
        // Handle the exception
        System.out.println("GlobalExceptionHandler: handleInternalServerError: " + ex.getMessage());
        // Perform additional error handling logic if needed
    }

}