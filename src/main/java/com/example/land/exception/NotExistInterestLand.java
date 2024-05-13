package com.example.land.exception;

public class NotExistInterestLand
        extends IllegalArgumentException{
    public NotExistInterestLand() {
        super("not exist interest land");
    }
}
