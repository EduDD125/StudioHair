package com.example.sistemacabeleleiro.domain.usecases.scheduling.usecases;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.client.repository.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingInputDTO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.repository.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityAlreadyExistsException;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

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

    public Integer insert(SchedulingInputDTO schedulingInputDTO){
        Validator<SchedulingInputDTO> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(schedulingInputDTO);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Client client = clientDAO.findOne(schedulingInputDTO.clientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Employee employee = employeeDAO.findOne(schedulingInputDTO.employeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Service service = serviceDAO.findOne(schedulingInputDTO.serviceId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        validateEntities(client,employee,service);

        Scheduling scheduling = new Scheduling(client,employee,schedulingInputDTO.realizationDate(),service);

        validateSchedulingConflicts(scheduling);

        return schedulingDAO.create(scheduling);
    }

    private void validateEntities(Client client, Employee employee, Service service){
        if(client.isInactive())
            throw new IllegalArgumentException("This client is inactive. Reactive to proceed with scheduling");
        if (service.isInactive())
            throw new IllegalArgumentException("This service is inactive. Reactive to proceed with scheduling");

        List<Service> expertises = employee.getExpertise();
        if (employee.isInactive() || !expertises.contains(service))
            throw new IllegalArgumentException("Employee is inactive or does not have this specialty. Contact to staff to proceed with scheduling");
    }

    private void validateSchedulingConflicts(Scheduling scheduling){
        List<Scheduling> clientSchedules = schedulingDAO.findByClient(scheduling.getClient().getId());
        List<Scheduling> employeeSchedules = schedulingDAO.findByEmployee(scheduling.getEmployee().getId());

        for (Scheduling existingScheduling: clientSchedules){
            if (existingScheduling.getRealizationDate().equals(scheduling.getRealizationDate())
                    && existingScheduling.getStatus().equals(SchedulingStatus.SCHEDULED))
                throw new EntityAlreadyExistsException("The client has a schedule for this date and time.");
        }

        for (Scheduling existingScheduling: employeeSchedules){
            if (existingScheduling.getRealizationDate().equals(scheduling.getRealizationDate())
                    && existingScheduling.getStatus().equals(SchedulingStatus.SCHEDULED))
                throw new EntityAlreadyExistsException("The employee has a schedule for this date and time.");
        }
    }
}
