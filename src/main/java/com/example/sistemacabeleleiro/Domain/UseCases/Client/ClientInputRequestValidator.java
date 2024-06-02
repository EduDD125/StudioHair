package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

public class ClientInputRequestValidator extends Validator<Client> {

    @Override
    public Notification validate(Client client) {
        Notification notification = new Notification();

        if (client == null) {
            notification.addError("Client is null");
            return notification;
        }

        if(nullOrEmpty(client.getName()))
            notification.addError("Name is null or empty");
        if(client.getEmail() == null)
            notification.addError("Email is null");
        else if (!validEmail(client.getEmail()))
            notification.addError("Email is not valid: " + client.getEmail().getValue());
        if(nullOrEmpty(client.getPhone()))
            notification.addError("Phone is null or empty");
        if(nullOrEmpty(client.getStatus().toString()))
            notification.addError("Client status is null or empty");
        if(client.getCpf() == null)
            notification.addError("CPF is null");
        else if(!validCPF(client.getCpf()))
            notification.addError("CPF is not valid: " + client.getCpf().getValue());

        return notification;
    }
}
