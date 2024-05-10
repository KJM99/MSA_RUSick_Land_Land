package com.example.land.exception;

public class NotEqualOwnerException
extends IllegalArgumentException {
    public NotEqualOwnerException(String ownerId) {
        super("Owner id " + ownerId + " is not equal to UserId");
    }
}
