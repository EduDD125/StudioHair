package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

import java.util.List;
import java.util.Optional;

public class FindClientUseCase {
    private ClientDAO clientDAO;

    public FindClientUseCase(ClientDAO clientDAO) {this.clientDAO = clientDAO;}

    public Optional<Client> findOne(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return clientDAO.findOne(id);
    }

    public Optional<Client> findOneByCPF(String cpf) {
        if (Validator.nullOrEmpty(cpf))
            throw new IllegalArgumentException("CPF can not be null empty.");
        return clientDAO.findOneByCPF(cpf);
    }

    public Optional<Client> findOneByName(String name) {
        if(Validator.nullOrEmpty(name))
            throw new IllegalArgumentException("Name can not be null or empty.");
        return clientDAO.findOneByName(name);
    }

    public List<Client> findAll() {
        return clientDAO.findAll();
    }
}
