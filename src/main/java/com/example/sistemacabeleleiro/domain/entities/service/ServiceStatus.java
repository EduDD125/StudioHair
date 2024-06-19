package com.example.sistemacabeleleiro.domain.entities.service;

import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;

import java.util.Arrays;

public enum ServiceStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private String label;

    ServiceStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static ServiceStatus toEnum(String value){
        return Arrays.stream(ServiceStatus.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
