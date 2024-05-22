package com.example.sistemacabeleleiro.Domain.UseCases.Scheduling;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityAlreadyExistsException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

import java.time.LocalDateTime;

import java.util.List;

public class CreateSchedulingUseCase {
    private SchedulingDAO schedulingDAO;

    public CreateSchedulingUseCase(SchedulingDAO schedulingDAO){ this.schedulingDAO = schedulingDAO; }

    public Integer insert(Scheduling scheduling){
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(scheduling);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        LocalDateTime scheduledDate = scheduling.getDataRealizacao();
        if (scheduledDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The scheduling date cannot be in the past.");
        }

        Employee employee = scheduling.getEmployee();
        List<Scheduling> schedulings;

        schedulings = schedulingDAO.findAll();

        for(Scheduling existing: schedulings){
            if(existing.getEmployee().equals(employee)
                    && schedulingDAO.findByScheduledDate(scheduledDate).isPresent()){
                throw new EntityAlreadyExistsException("The employee has a schedule for this date and time.");
            }
        }
        return schedulingDAO.create(scheduling);
    }
}
