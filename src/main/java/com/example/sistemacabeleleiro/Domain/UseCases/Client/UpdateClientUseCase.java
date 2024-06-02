package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

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
