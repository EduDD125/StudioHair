package com.example.sistemacabeleleiro.Domain.UseCases.Scheduling;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Client.ClientDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.EmployeeDAO;
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

    public CreateSchedulingUseCase(SchedulingDAO schedulingDAO, ClientDAO clientDAO, EmployeeDAO employeeDAO, ServiceDAO serviceDAO) {
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
        if(!clientDAO.findOneByCPF(scheduling.getClient().getCpf()).isPresent()){
            throw new EntityNotFoundException("This client does not exist");
        }
        if(!employeeDAO.findByCpf(scheduling.getEmployee().getCpf()).isPresent()){
            throw new EntityNotFoundException("This employee does not exist");
        }
        if(serviceDAO.findOne(scheduling.getService().getId()).isEmpty()){
            throw new EntityNotFoundException("This service does not exist");
        }

        List<Scheduling> employeeSchedulings;
        List<Scheduling> clientSchedulings;

        employeeSchedulings = schedulingDAO.findByEmployee(scheduling.getEmployee().getId());
        clientSchedulings = schedulingDAO.findByClient(scheduling.getClient().getId());

        for(Scheduling existingDateAndTime: employeeSchedulings){
            if(existingDateAndTime.getDataRealizacao() == scheduling.getDataRealizacao()) {
                throw new EntityAlreadyExistsException("The employee has a schedule for this date and time.");
            }
        }
        for(Scheduling existingDateAndTime: clientSchedulings){
            if(existingDateAndTime.getDataRealizacao() == scheduling.getDataRealizacao()) {
                throw new EntityAlreadyExistsException("The client has a schedule for this date and time.");
            }
        }

        return schedulingDAO.create(scheduling);
    }
}
