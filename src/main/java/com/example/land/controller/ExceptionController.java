package com.example.land.controller;


import com.example.land.exception.ExistLandException;
import com.example.land.exception.NotEqualOwnerException;
import com.example.land.exception.NotExistInterestLand;
import com.example.land.exception.NotExistLandException;
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

    @ExceptionHandler(NotExistLandException.class)
    public String handleNotExistLandException(NotExistLandException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(NotEqualOwnerException.class)
    public String handleNotEqualOwnerException(NotEqualOwnerException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(NotExistInterestLand.class)
    public String handleNotExistInterestLand(NotExistInterestLand e) {
        log.error(e.getMessage());
        return e.getMessage();
    }


}
