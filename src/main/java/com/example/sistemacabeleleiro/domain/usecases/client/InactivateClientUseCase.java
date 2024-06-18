package com.example.sistemacabeleleiro.domain.usecases.client;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class InactivateClientUseCase {
    private ClientDAO clientDAO;

    public InactivateClientUseCase(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public boolean inactivate(int id){
        Client client = clientDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        if (client.isInactive()){
            throw new IllegalArgumentException("Client is already inactive");
        }
        return clientDAO.inactivate(client);
    }
}
