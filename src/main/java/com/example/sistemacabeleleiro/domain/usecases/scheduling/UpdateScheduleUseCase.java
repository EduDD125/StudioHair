package com.example.sistemacabeleleiro.domain.usecases.scheduling;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.employee.EmployeeStatus;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.entities.service.ServiceStatus;
import com.example.sistemacabeleleiro.domain.usecases.client.repository.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.DAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

import java.time.LocalDateTime;


public class UpdateScheduleUseCase {
    private SchedulingDAO schedulingDAO;
    private ClientDAO clientDAO;
    private EmployeeDAO employeeDAO;
    private ServiceDAO serviceDAO;

    public UpdateScheduleUseCase(SchedulingDAO schedulingDAO, ClientDAO clientDAO, EmployeeDAO employeeDAO, ServiceDAO serviceDAO){
        this.schedulingDAO = schedulingDAO;
        this.clientDAO = clientDAO;
        this.employeeDAO = employeeDAO;
        this.serviceDAO = serviceDAO;
    }

    public boolean update(Scheduling scheduling, Client clientToUpdate, Employee employeeToUpdate, Service serviceToUpdate, LocalDateTime dateToUpdate){
        validateScheduling(scheduling);

        if(clientToUpdate == null || employeeToUpdate == null || serviceToUpdate == null || dateToUpdate == null){
            throw new IllegalArgumentException("No parameter can be null.");
        }

        updateClient(scheduling, clientToUpdate);
        updateEmployee(scheduling, employeeToUpdate);
        updateService(scheduling, serviceToUpdate);
        updateDate(scheduling, dateToUpdate);

        return schedulingDAO.update(scheduling);
    }

    private void validateScheduling(Scheduling scheduling) {
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(scheduling);

        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        if (schedulingDAO.findOne(scheduling.getId()).isEmpty()) {
            throw new EntityNotFoundException("Scheduling not found.");
        }
    }

    private <T, K> T findEntityById(DAO<T, K> dao, K id, String entityName) {
        return dao.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found."));
    }

    public boolean updateClient(Scheduling schedulingToUpdate, Client clientToUpdate) {
        validateScheduling(schedulingToUpdate);

        Client client = findEntityById(clientDAO, clientToUpdate.getId(), "Client");
        if (client == null || client.getStatus() == ClientStatus.INACTIVE) {
            throw new IllegalArgumentException("This client is inactive. Reactivate to proceed with scheduling update.");
        }

        schedulingToUpdate.setClient(client);
        return schedulingDAO.update(schedulingToUpdate);
    }

    public boolean updateEmployee(Scheduling schedulingToUpdate, Employee employeeToUpdate) {
        validateScheduling(schedulingToUpdate);

        Employee employee = findEntityById(employeeDAO, employeeToUpdate.getId(), "Employee");
        if (employee == null || employee.getStatus() == EmployeeStatus.INACTIVE || !employee.getExpertise().contains(schedulingToUpdate.getService())) {
            throw new IllegalArgumentException("This employee is inactive or does not have the required expertise.");
        }

        schedulingToUpdate.setEmployee(employee);
        return schedulingDAO.update(schedulingToUpdate);
    }

    public boolean updateService(Scheduling schedulingToUpdate, Service serviceToUpdate) {
        validateScheduling(schedulingToUpdate);

        Service service = findEntityById(serviceDAO, serviceToUpdate.getId(), "Service");
        if (service == null || service.getStatus() == ServiceStatus.INACTIVE || !schedulingToUpdate.getEmployee().getExpertise().contains(service)) {
            throw new IllegalArgumentException("This service is inactive or the employee does not have the required expertise.");
        }

        schedulingToUpdate.setService(service);
        return schedulingDAO.update(schedulingToUpdate);
    }

    public boolean updateDate(Scheduling schedulingToUpdate, LocalDateTime dateToUpdate) {
        validateScheduling(schedulingToUpdate);

        if (dateToUpdate == null || dateToUpdate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The scheduling date cannot be in the past.");
        }

        schedulingToUpdate.setRealizationDate(dateToUpdate);
        return schedulingDAO.update(schedulingToUpdate);
    }
}
