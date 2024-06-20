package com.example.sistemacabeleleiro.domain.usecases.client.usecases;

import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientUpdateDTO;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class ClientUpdateRequestValidator extends Validator<ClientUpdateDTO> {
    @Override
    public Notification validate(ClientUpdateDTO client) {
        Notification notification = new Notification();

        if (client == null) {
            notification.addError("Client is null");
            return notification;
        }

        if(nullOrEmpty(client.name()))
            notification.addError("Name is null or empty");
        if(client.email() == null)
            notification.addError("Email is null");
        else if (!validEmail(client.email()))
            notification.addError("Email is not valid: " + client.email().getValue());
        if(nullOrEmpty(client.phone()))
            notification.addError("Phone is null or empty");
        if(client.cpf() == null)
            notification.addError("CPF is null");
        else if(!validCPF(client.cpf()))
            notification.addError("CPF is not valid: " + client.cpf().getValue());

        return notification;
    }
}
