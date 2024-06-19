package com.example.sistemacabeleleiro.domain.entities.employee;

import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;

import java.util.Arrays;

public enum EmployeeStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private String label;

    EmployeeStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static EmployeeStatus toEnum(String value){
        return Arrays.stream(EmployeeStatus.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
