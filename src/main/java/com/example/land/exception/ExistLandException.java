package com.example.land.exception;

import java.util.UUID;

public class ExistLandException
        extends IllegalArgumentException {
    public ExistLandException(UUID id) {
        super("land already exist");
    }
}
