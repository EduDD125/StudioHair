package com.example.sistemacabeleleiro.domain.usecases.client;

import com.example.sistemacabeleleiro.domain.entities.Client.Client;
import com.example.sistemacabeleleiro.domain.entities.Client.ClientStatus;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class InactivateClientUseCase {
    private ClientDAO clientDAO;

    public InactivateClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public boolean inactivate(Client client){
        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if (notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Integer id = client.getId();
        if (clientDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Client not found");
        }
        if (client.getStatus().equals(ClientStatus.INACTIVE)){
            throw new IllegalArgumentException("Client is already inactive");
        }

        return clientDAO.activate(client);
    }
}
