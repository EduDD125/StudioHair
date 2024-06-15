package com.example.sistemacabeleleiro.domain.usecases.client;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class UpdateClientUseCase {
    private ClientDAO clientDAO;

    public UpdateClientUseCase(ClientDAO clientDAO){this.clientDAO = clientDAO;}

    public boolean update(Client client) {
        Validator validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        CPF cpf = client.getCpf();
        if(clientDAO.findOneByCPF(cpf).isEmpty())
            throw new EntityNotFoundException("This CPF isn´t registered");

        return clientDAO.update(client);
    }
}
