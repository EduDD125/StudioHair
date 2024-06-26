package com.example.sistemacabeleleiro.domain.usecases.client.dto;

import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;

public record ClientOutputDTO(int id, String name, CPF cpf, String phone, Email email, ClientStatus status) {
    @Override
    public int id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public CPF cpf() {
        return cpf;
    }

    @Override
    public String phone() {
        return phone;
    }

    @Override
    public Email email() {
        return email;
    }

    @Override
    public ClientStatus status() {
        return status;
    }
}
