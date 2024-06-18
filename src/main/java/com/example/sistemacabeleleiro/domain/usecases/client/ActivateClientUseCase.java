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

    public boolean activate(int id){
        Client client = clientDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        if (client.isActive()){
            throw new IllegalArgumentException("Client is already active");
        }

        return clientDAO.activate(client);
    }
}
