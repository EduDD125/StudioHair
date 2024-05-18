package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.DAO;

import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findOneCPF(String cpf);
}
