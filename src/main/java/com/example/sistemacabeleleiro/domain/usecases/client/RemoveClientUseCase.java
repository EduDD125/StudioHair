package com.example.sistemacabeleleiro.domain.usecases.client;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

import java.util.List;

public class RemoveClientUseCase {
    private ClientDAO clientDAO;
    private SchedulingDAO schedulingDAO;

    public RemoveClientUseCase(ClientDAO clientDAO, SchedulingDAO schedulingDAO) {
        this.clientDAO = clientDAO;
        this.schedulingDAO = schedulingDAO;
    }

    public boolean remove(int id) {
        Client client = clientDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client with id " + id));

        validateClientCanBeRemoved(client);
        return clientDAO.deleteByKey(id);
    }

    private void validateClientCanBeRemoved(Client client) {
        if (!clientSchedules(client).isEmpty()) {
            throw new IllegalArgumentException("It's not possible to exclude employees who already have a schedule");
        }

        if (client.isActive()) {
            throw new IllegalArgumentException("Can't delete an active employee");
        }
    }

    private List<Scheduling> clientSchedules(Client client) {
        List<Scheduling> schedules = schedulingDAO.findAll().stream()
                .filter(schedule -> schedule.getClient().equals(client))
                .toList();

        return schedules;
    }
}
