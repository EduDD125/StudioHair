package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Client.ClientStatus;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

public class ActivateClientUseCase {
    private ClientDAO clientDAO;

    public ActivateClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public boolean activate(Client client){
        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if (notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Integer id = client.getId();
        if (clientDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Client not found");
        }
        if (client.getStatus().equals(ClientStatus.ACTIVE)){
            throw new IllegalArgumentException("Client is already active");
        }

        return clientDAO.activate(client);
    }
}
