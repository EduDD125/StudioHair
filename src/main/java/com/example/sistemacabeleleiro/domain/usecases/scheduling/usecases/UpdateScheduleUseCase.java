package com.example.sistemacabeleleiro.domain.usecases.scheduling.usecases;

import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.client.repository.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingUpdateDTO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.repository.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.*;

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

    public boolean update(SchedulingUpdateDTO schedulingUpdateDTO){
        validateScheduling(schedulingUpdateDTO);

        Scheduling scheduling = schedulingDAO.findOne(schedulingUpdateDTO.schedulingId())
                .orElseThrow(() -> new EntityNotFoundException("Scheduling not found."));

        Client client = findEntityById(clientDAO,schedulingUpdateDTO.clientId(), "Client");
        Employee employee = findEntityById(employeeDAO,schedulingUpdateDTO.employeeId(), "Employee");
        Service service = findEntityById(serviceDAO,schedulingUpdateDTO.serviceId(), "Service");

        validateEntities(client,employee,service);

        scheduling.setClient(client);
        scheduling.setEmployee(employee);
        scheduling.setService(service);
        scheduling.setRealizationDate(schedulingUpdateDTO.realizationDate());

        validateSchedulingConflicts(scheduling);

        return schedulingDAO.update(scheduling);
    }

    private void validateScheduling(SchedulingUpdateDTO scheduling) {
        Validator<SchedulingUpdateDTO> validator = new SchedulingUpdateRequestValidator();
        Notification notification = validator.validate(scheduling);

        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        if (schedulingDAO.findOne(scheduling.schedulingId()).isEmpty()) {
            throw new EntityNotFoundException("Scheduling not found.");
        }
    }

    private void validateEntities(Client client, Employee employee, Service service){
        if (client.isInactive())
            throw new IllegalArgumentException("Client is inactive. Reactivate to proceed with scheduling update.");
        if (service.isInactive())
            throw new IllegalArgumentException("Service is inactive. Reactivate to proceed with scheduling update.");
        if (employee.isInactive() || !employee.getExpertise().contains(service))
            throw new IllegalArgumentException("Employee is inactive or does not have this specialty.");


    }

    private void validateSchedulingConflicts(Scheduling scheduling){
        List<Scheduling> clientSchedules = schedulingDAO.findByClient(scheduling.getClient().getId());
        List<Scheduling> employeeSchedules = schedulingDAO.findByEmployee(scheduling.getEmployee().getId());

        for (Scheduling existingScheduling: clientSchedules){
            if (existingScheduling.getRealizationDate().equals(scheduling.getRealizationDate()))
                throw new EntityAlreadyExistsException("The client has a schedule for this date and time.");
        }

        for (Scheduling existingScheduling: employeeSchedules){
            if (existingScheduling.getRealizationDate().equals(scheduling.getRealizationDate()))
                throw new EntityAlreadyExistsException("The employee has a schedule for this date and time.");
        }
    }

    private <T, K> T findEntityById(DAO<T, K> dao, K id, String entityName) {
        return dao.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found."));
    }
}
