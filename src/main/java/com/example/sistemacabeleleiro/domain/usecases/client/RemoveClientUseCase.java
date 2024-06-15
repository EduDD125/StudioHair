package com.example.sistemacabeleleiro.domain.usecases.client;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

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
        if (!clientSchedules(client.get()).isEmpty())
            throw new IllegalArgumentException("It's not possible to exclude clients who already have a schedule");

        validateClientStatus(client.get());

        return clientDAO.deleteByKey(id);
    }

    public boolean remove(Client client) {

        if (client == null || clientDAO.findOne(client.getId()).isEmpty())
            throw new EntityNotFoundException("This client isn´t registered");
        if (!clientSchedules(client).isEmpty())
            throw new IllegalArgumentException("It's not possible to exclude clients who already have a schedule");

        validateClientStatus(client);

        return clientDAO.delete(client);
    }

    private void validateClientStatus(Client client) {
        if (client.getStatus().equals(ClientStatus.ACTIVE)) {
            throw new IllegalArgumentException("Can't delete an active client");
        }
    }

    private List<Scheduling> clientSchedules(Client client) {
        List<Scheduling> schedules = schedulingDAO.findAll().stream()
                .filter(schedule -> schedule.getClient().equals(client))
                .toList();

        return schedules;
    }
}
