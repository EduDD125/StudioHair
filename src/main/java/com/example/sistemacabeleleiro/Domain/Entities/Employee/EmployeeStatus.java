package com.example.sistemacabeleleiro.Domain.Entities.Employee;

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
