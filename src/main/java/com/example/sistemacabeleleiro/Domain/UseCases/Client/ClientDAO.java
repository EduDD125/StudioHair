package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.DAO;

import java.util.List;
import java.util.Optional;

public interface ClientDAO extends DAO<Client, Integer> {
    Optional<Client> findOneByCPF(CPF cpf);
    Optional<Client> findOneByName(String name);
    List<Client> findAll();
    boolean inactivate(Client client);
    boolean activate(Client client);
}
