package com.example.sistemacabeleleiro.domain.usecases.client.dto;

import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;

public record ClientOutputDTO(int id, String name, CPF cpf, String phone, Email email, ClientStatus status) {
}
