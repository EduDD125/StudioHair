package com.example.sistemacabeleleiro.domain.usecases.scheduling.usecases;

import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingInputDTO;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

import java.time.LocalDateTime;

public class SchedulingInputRequestValidator extends Validator<SchedulingInputDTO> {
    Notification notification = new Notification();
    @Override
    public Notification validate(SchedulingInputDTO scheduling) {
        if(scheduling == null){
            notification.addError("Scheduling is null");
            return notification;
        }
        if(scheduling.clientId() < 0)
            notification.addError("Client ID must be positive");
        if(scheduling.employeeId() < 0)
            notification.addError("Employee ID must be positive");
        if (scheduling.serviceId() < 0)
            notification.addError("Service ID must be positive");
        if (scheduling.realizationDate().isBefore(LocalDateTime.now()))
            notification.addError("The scheduling date cannot be in the past");
        return notification;
    }
}
