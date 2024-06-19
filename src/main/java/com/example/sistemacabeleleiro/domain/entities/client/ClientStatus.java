package com.example.sistemacabeleleiro.domain.entities.client;

import java.util.Arrays;

public enum ClientStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private String label;

    ClientStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static ClientStatus toEnum(String value){
        return Arrays.stream(ClientStatus.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
