package com.example.sistemacabeleleiro.Domain.UseCases.Utils;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException (String message) {
        super(message);
    }
}
