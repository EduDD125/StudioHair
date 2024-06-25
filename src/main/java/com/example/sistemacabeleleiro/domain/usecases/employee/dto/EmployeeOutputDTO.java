package com.example.sistemacabeleleiro.domain.usecases.employee.dto;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;
import com.example.sistemacabeleleiro.domain.entities.employee.EmployeeStatus;

public record EmployeeOutputDTO(int id, String name, CPF cpf, String phone, Email email, String dateOfBirth, EmployeeStatus status) {
    public String expertise() {
        return "";
    }
}
