package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

import java.lang.ref.Cleaner;
import java.util.List;
import java.util.Optional;

public class ShowClientUseCase {
    private ClientDAO clientDAO;

    public ShowClientUseCase(ClientDAO clientDAO) {this.clientDAO = clientDAO;}

    public Optional<Client> findOne(String id){
        if (Validator.nullOrEmpty(id))
            throw new IllegalArgumentException("ID can not be null or empty.");
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
