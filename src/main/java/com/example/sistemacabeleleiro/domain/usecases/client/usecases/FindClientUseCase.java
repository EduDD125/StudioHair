package com.example.sistemacabeleleiro.domain.usecases.client.usecases;

import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientOutputDTO;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.usecases.client.repository.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindClientUseCase {
    private static ClientDAO clientDAO;

    public FindClientUseCase(ClientDAO clientDAO) {this.clientDAO = clientDAO;}

    public Optional<ClientOutputDTO> findOne(int id){
        return clientDAO.findOne(id).map(this::mapToDTO);
    }

    public Optional<ClientOutputDTO> findOneByCPF(CPF cpf) {
        if (Validator.nullOrEmpty(cpf.toString()))
            throw new IllegalArgumentException("CPF can not be null empty.");
        if (!Validator.validCPF(cpf))
            throw new IllegalArgumentException("CPF is not valid: " + cpf);
        return clientDAO.findOneByCPF(cpf).map(this::mapToDTO);
    }

    public Optional<ClientOutputDTO> findOneByName(String name) {
        if(Validator.nullOrEmpty(name))
            throw new IllegalArgumentException("Name can not be null or empty.");
        return clientDAO.findOneByName(name).map(this::mapToDTO);
    }

    public List<ClientOutputDTO> findAll() {
        List<Client> clients = clientDAO.findAll();
        return clients.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ClientOutputDTO mapToDTO(Client client){
        return new ClientOutputDTO(client.getId(),client.getName(),client.getCpf(),
                client.getPhone(),client.getEmail(),client.getStatus());
    }
}
