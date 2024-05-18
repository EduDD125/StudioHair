package com.example.sistemacabeleleiro.Domain.UseCases.Utils;

import java.util.Collection;

public abstract class Validator<T>{
    public abstract Notification validate(T type);

    public static boolean nullOrEmpty(String string){
        return string == null || string.isEmpty();
    }

    public static boolean nullOrEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean nullOrNegativePrice(Double price) {
        return price == null || price < 0;
    }
}
