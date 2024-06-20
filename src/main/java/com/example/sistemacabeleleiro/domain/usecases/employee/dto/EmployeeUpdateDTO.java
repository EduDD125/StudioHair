package com.example.sistemacabeleleiro.domain.usecases.employee.dto;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;

public record EmployeeUpdateDTO(int id, String name, CPF cpf, String phone, Email email, String dateOfBirth) {
}
