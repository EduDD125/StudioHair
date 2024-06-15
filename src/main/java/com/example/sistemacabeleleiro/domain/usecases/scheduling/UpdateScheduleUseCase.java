package com.example.sistemacabeleleiro.domain.usecases.scheduling;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.employee.EmployeeStatus;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.entities.service.ServiceStatus;
import com.example.sistemacabeleleiro.domain.usecases.client.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.employee.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.service.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

import java.time.LocalDateTime;
import java.util.List;

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
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(scheduling);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = scheduling.getId();

        if(schedulingDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Scheduling not found.");
        }
        if(clientToUpdate == null || employeeToUpdate == null || serviceToUpdate == null || dateToUpdate == null){
            throw new EntityNotFoundException("No one parameter can be null ");
        }
        updateClient(scheduling, clientToUpdate);
        updateEmployee(scheduling, employeeToUpdate);
        updateService(scheduling, serviceToUpdate);
        updateDate(scheduling, dateToUpdate);

        return schedulingDAO.update(scheduling);
    }

    public boolean updateClient(Scheduling schedulingToUpdate, Client clientToUpdate){
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(schedulingToUpdate);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = schedulingToUpdate.getId();

        if(schedulingDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Scheduling not found.");
        }
        Client client = clientDAO.findOne(clientToUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));

        if(client.getStatus() == ClientStatus.INACTIVE){
            throw new IllegalArgumentException("This client is inactive. Reactive to proceed with scheduling update");
        }

        schedulingToUpdate.setClient(client);
        return schedulingDAO.update(schedulingToUpdate);
    }

    public boolean updateEmployee(Scheduling schedulingToUpdate, Employee employeeToUpdate){
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(schedulingToUpdate);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = schedulingToUpdate.getId();

        if(schedulingDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Scheduling not found.");
        }
        Employee employee = employeeDAO.findOne(employeeToUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can not find an employee"));

        List<Service> expertises = employee.getExpertise();
        if(employee.getStatus() == EmployeeStatus.INACTIVE || !expertises.contains(schedulingToUpdate.getService())){
            throw new EntityNotFoundException("This employee is inactive or does not have this specialty. Contact to staff to proceed with scheduling update");
        }

        schedulingToUpdate.setEmployee(employee);
        return schedulingDAO.update(schedulingToUpdate);
    }

    public boolean updateService(Scheduling schedulingToUpdate, Service serviceToUpdate){
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(schedulingToUpdate);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = schedulingToUpdate.getId();

        if(schedulingDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Scheduling not found.");
        }

        Service service = serviceDAO.findOne(serviceToUpdate.getId())
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service"));

        List<Service> expertises = schedulingToUpdate.getEmployee().getExpertise();
        if(service.getStatus() == ServiceStatus.INACTIVE || !expertises.contains(service)){
            throw new EntityNotFoundException("This service is inactive or the employee do not have this expertise. Contact to staff to proceed with scheduling update");
        }

        schedulingToUpdate.setService(service);
        return schedulingDAO.update(schedulingToUpdate);
    }

    public boolean updateDate(Scheduling schedulingToUpdate, LocalDateTime dateToUpdate){
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(schedulingToUpdate);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = schedulingToUpdate.getId();

        if(schedulingDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Scheduling not found.");
        }

        if (dateToUpdate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The scheduling date cannot be in the past.");
        }

        schedulingToUpdate.setRealizationDate(dateToUpdate);
        return schedulingDAO.update(schedulingToUpdate);
    }

}
