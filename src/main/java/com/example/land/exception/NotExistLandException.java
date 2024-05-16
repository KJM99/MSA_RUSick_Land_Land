package com.example.land.exception;

public class NotExistLandException
    extends IllegalArgumentException{
    public NotExistLandException() {
        super("land not exist");
    }
}
