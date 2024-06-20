package com.example.sistemacabeleleiro.domain.usecases.client.usecases;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.usecases.client.repository.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

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
