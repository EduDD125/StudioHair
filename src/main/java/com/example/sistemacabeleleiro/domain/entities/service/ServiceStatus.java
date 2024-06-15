package com.example.sistemacabeleleiro.domain.entities.service;

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
}
