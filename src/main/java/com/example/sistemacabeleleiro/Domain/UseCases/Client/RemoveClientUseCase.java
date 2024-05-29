package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.SchedulingDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

import java.util.List;

public class RemoveClientUseCase {
    private SchedulingDAO schedulingDAO;

    private ClientDAO clientDAO;

    public RemoveClientUseCase(ClientDAO clientDAO) {this.clientDAO = clientDAO;}

    public boolean remove(Client client) {
        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String cpf = client.getCpf().toString();
        if (clientDAO.findOneByCPF(cpf).isEmpty())
            throw new EntityNotFoundException("This CPF isn´t registered");

        List<Scheduling> schedulesToDelete = schedulingDAO.findAll().stream().
                filter(schedule -> schedule.getClient().equals(client)).toList();
        if(!schedulesToDelete.isEmpty()) {
            for (Scheduling scheduling : schedulesToDelete) {
                schedulingDAO.delete(scheduling);
            }
        }
        return clientDAO.delete(client);
    }
}
