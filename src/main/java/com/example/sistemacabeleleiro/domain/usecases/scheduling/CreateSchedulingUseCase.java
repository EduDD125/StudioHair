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
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityAlreadyExistsException;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

import java.time.LocalDateTime;

import java.util.List;

public class CreateSchedulingUseCase {
    private SchedulingDAO schedulingDAO;
    private ClientDAO clientDAO;
    private EmployeeDAO employeeDAO;
    private ServiceDAO serviceDAO;

    public CreateSchedulingUseCase(SchedulingDAO schedulingDAO,
                                   ClientDAO clientDAO,
                                   EmployeeDAO employeeDAO,
                                   ServiceDAO serviceDAO) {
        this.schedulingDAO = schedulingDAO;
        this.clientDAO = clientDAO;
        this.employeeDAO = employeeDAO;
        this.serviceDAO = serviceDAO;
    }

    public Integer insert(Scheduling scheduling){
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(scheduling);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }
        if (scheduling.getRealizationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The scheduling date cannot be in the past.");
        }

        Client client = clientDAO.findOne(scheduling.getClient().getId())
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));
        Employee employee = employeeDAO.findOne(scheduling.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException("Can not find an employee"));
        Service service = serviceDAO.findOne(scheduling.getService().getId())
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service"));

        if(client.getStatus().equals(ClientStatus.INACTIVE)){
            throw new EntityNotFoundException("This client is inactive. Reactive to proceed with scheduling");
        }
        if(service.getStatus() == ServiceStatus.INACTIVE){
            throw new EntityNotFoundException("This service is inactive. Reactive to proceed with scheduling");
        }

        List<Service> expertises = employee.getExpertise();
        if(employee.getStatus() == EmployeeStatus.INACTIVE || !expertises.contains(scheduling.getService())){
            throw new EntityNotFoundException("This employee is inactive or does not have this specialty. Contact to staff to proceed with scheduling");
        }

        List<Scheduling> employeeSchedulings;
        List<Scheduling> clientSchedulings;

        employeeSchedulings = schedulingDAO.findByEmployee(scheduling.getEmployee().getId());
        clientSchedulings = schedulingDAO.findByClient(scheduling.getClient().getId());

        for(Scheduling existingDateAndTime: employeeSchedulings){
            if(existingDateAndTime.getRealizationDate().equals(scheduling.getRealizationDate())) {
                throw new EntityAlreadyExistsException("The employee has a schedule for this date and time.");
            }
        }
        for(Scheduling existingDateAndTime: clientSchedulings){
            if(existingDateAndTime.getRealizationDate().equals(scheduling.getRealizationDate())) {
                throw new EntityAlreadyExistsException("The client has a schedule for this date and time.");
            }
        }

        return schedulingDAO.create(scheduling);
    }
}
