package com.example.sistemacabeleleiro.application.dtos.client;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;

public record ClientInputDTO(String name, CPF cpf, String phone, Email email) {
}
