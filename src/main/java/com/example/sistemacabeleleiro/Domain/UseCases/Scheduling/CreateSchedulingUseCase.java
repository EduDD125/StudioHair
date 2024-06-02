package com.example.sistemacabeleleiro.Domain.UseCases.Scheduling;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Client.ClientStatus;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.EmployeeStatus;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.Entities.Service.ServiceStatus;
import com.example.sistemacabeleleiro.Domain.UseCases.Client.ClientDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Client.FindClientUseCase;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.EmployeeDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.FindEmployeeUseCase;
import com.example.sistemacabeleleiro.Domain.UseCases.Service.FindServiceUseCase;
import com.example.sistemacabeleleiro.Domain.UseCases.Service.ServiceDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityAlreadyExistsException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

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
        if (scheduling.getDataRealizacao().isBefore(LocalDateTime.now())) {
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
            if(existingDateAndTime.getDataRealizacao().equals(scheduling.getDataRealizacao())) {
                throw new EntityAlreadyExistsException("The employee has a schedule for this date and time.");
            }
        }
        for(Scheduling existingDateAndTime: clientSchedulings){
            if(existingDateAndTime.getDataRealizacao().equals(scheduling.getDataRealizacao())) {
                throw new EntityAlreadyExistsException("The client has a schedule for this date and time.");
            }
        }

        return schedulingDAO.create(scheduling);
    }
}
