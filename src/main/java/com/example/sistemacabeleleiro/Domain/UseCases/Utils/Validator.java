package com.example.sistemacabeleleiro.Domain.UseCases.Utils;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Email.Email;

import java.util.Collection;

public abstract class Validator<T>{
    public abstract Notification validate(T type);

    public static boolean nullOrEmpty(String string){
        return string == null || string.isEmpty();
    }

    public static boolean validCPF(CPF cpf){
        return cpf.isValidCPF(cpf.getValue());
    }
    public static boolean validEmail(Email email){
        return email.isValidEmail(email.getValue());
    }

    public static boolean nullOrEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }

}
