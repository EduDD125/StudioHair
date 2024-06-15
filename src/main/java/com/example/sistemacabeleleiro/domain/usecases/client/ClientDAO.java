package com.example.sistemacabeleleiro.domain.usecases.client;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findOneByCPF(CPF cpf);
    Optional<Client> findOneByName(String name);
    List<Client> findAll();
    boolean inactivate(Client client);
    boolean activate(Client client);
}
