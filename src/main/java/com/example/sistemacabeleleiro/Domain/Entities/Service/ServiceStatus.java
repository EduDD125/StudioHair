package com.example.sistemacabeleleiro.Domain.Entities.Service;

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
