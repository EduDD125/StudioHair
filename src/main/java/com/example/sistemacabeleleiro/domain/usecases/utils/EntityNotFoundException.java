package com.example.sistemacabeleleiro.domain.usecases.utils;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message){
        super(message);
    }
}
