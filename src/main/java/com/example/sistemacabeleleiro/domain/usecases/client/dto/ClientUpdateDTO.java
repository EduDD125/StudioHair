package com.example.sistemacabeleleiro.domain.usecases.client.dto;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;

public record ClientUpdateDTO (int id, String name, CPF cpf, String phone, Email email){
}
