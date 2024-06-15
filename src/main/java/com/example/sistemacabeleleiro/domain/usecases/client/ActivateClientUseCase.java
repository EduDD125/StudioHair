package com.example.sistemacabeleleiro.domain.usecases.client;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

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
