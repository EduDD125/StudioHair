package com.example.sistemacabeleleiro.domain.entities.Client;

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
}
