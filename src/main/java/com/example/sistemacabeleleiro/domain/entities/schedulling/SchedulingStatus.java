package com.example.sistemacabeleleiro.domain.entities.schedulling;

import java.util.Arrays;

public enum SchedulingStatus {
    SCHEDULED("Agendado"),
    CANCELED ("Cancelado"),
    PROVIDED("Realizado");

    private String label;

    SchedulingStatus(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static SchedulingStatus toEnum(String value){
        return Arrays.stream(SchedulingStatus.values())
                .filter(c -> value.equals(c.toString()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
