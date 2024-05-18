package com.example.sistemacabeleleiro.Domain.Entities.Schedulling;

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
}
