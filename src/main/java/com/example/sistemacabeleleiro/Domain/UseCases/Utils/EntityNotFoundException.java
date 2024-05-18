package com.example.sistemacabeleleiro.Domain.UseCases.Utils;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
