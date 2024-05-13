package com.example.land.exception;

import java.util.UUID;

public class ExistLandException
        extends IllegalArgumentException {
    public ExistLandException() {
        super("land already exist");
    }
}
