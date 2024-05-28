package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.DAO;

import java.util.List;
import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findOneByCPF(String cpf);
    Optional<Client> findOneByName(String name);
    List<Client> findAll();

    Integer inactivate(Client client);
}
