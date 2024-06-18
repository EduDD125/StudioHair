package com.example.sistemacabeleleiro.application.dtos.employee;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;

public record EmployeeOutputDTO(int id, String name, CPF cpf, String phone, Email email, String dateOfBirth) {
}
