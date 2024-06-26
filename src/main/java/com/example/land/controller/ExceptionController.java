package com.example.land.controller;


import com.example.land.exception.ExistLandException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ExistLandException.class)
    public String handleExistLandException(ExistLandException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }
}
