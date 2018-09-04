package ru.orindev.BostonGeneTest.SpringUserService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EmailAlreadyExistsException.class, UserNotFoundException.class})
    public String handleException(Exception ex) {
        return ex.getMessage();
    }
}
