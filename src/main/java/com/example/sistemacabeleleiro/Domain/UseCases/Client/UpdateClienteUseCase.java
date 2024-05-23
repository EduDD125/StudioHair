package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

public class UpdateClienteUseCase {
    private ClientDAO clientDAO;

    public UpdateClienteUseCase(ClientDAO clientDAO){this.clientDAO = clientDAO;}

    public boolean update(Client client) {
        Validator validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String cpf = client.getCpf().toString();
        if(clientDAO.findOneCPF(cpf).isEmpty())
            throw new EntityNotFoundException("This CPF isn´t registered");

        return clientDAO.update(client);
    }
}
