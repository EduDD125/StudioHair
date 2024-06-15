package com.example.sistemacabeleleiro.domain.entities.Employee;

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
}
