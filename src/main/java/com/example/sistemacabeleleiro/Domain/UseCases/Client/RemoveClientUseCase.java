package com.example.sistemacabeleleiro.Domain.UseCases.Client;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.SchedulingDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

import java.util.List;

public class RemoveClientUseCase {
    private SchedulingDAO schedulingDAO;

    private ClientDAO clientDAO;

    public RemoveClientUseCase(ClientDAO clientDAO, SchedulingDAO schedulingDAO) {
        this.clientDAO = clientDAO;
        this.schedulingDAO = schedulingDAO;
    }

    public boolean remove(Integer id) {
        var client = clientDAO.findOne(id);
        if (id == null || client.isEmpty())
            throw new EntityNotFoundException("Client not found");

        deleteClientSchedules(client.get());
        return clientDAO.deleteByKey(id);
    }

    public boolean remove(Client client) {

        CPF cpf = client.getCpf();
        if (client == null || clientDAO.findOneByCPF(cpf).isEmpty())
            throw new EntityNotFoundException("This CPF isn´t registered");

        deleteClientSchedules(client);
        return clientDAO.delete(client);
    }

    private void deleteClientSchedules(Client client) {
        List<Scheduling> schedulesToDelete = schedulingDAO.findAll().stream()
                .filter(schedule -> schedule.getClient().equals(client))
                .toList();
        if (!schedulesToDelete.isEmpty()){
            for (Scheduling schedule : schedulesToDelete) {
                schedulingDAO.delete(schedule);
            }
        }
    }
}
