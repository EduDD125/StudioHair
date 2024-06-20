package com.example.sistemacabeleleiro.domain.usecases.client.usecases;

import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientUpdateDTO;
import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.usecases.client.repository.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class UpdateClientUseCase {
    private ClientDAO clientDAO;

    public UpdateClientUseCase(ClientDAO clientDAO){this.clientDAO = clientDAO;}

    public boolean update(ClientUpdateDTO clientUpdateDTO) {
        Validator<ClientUpdateDTO> validator = new ClientUpdateRequestValidator();
        Notification notification = validator.validate(clientUpdateDTO);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        int id = clientUpdateDTO.id();

        Client client = clientDAO.findOne(id)
                .orElseThrow(()-> new EntityNotFoundException("Client not found"));
        return clientDAO.update(client);
    }
}
