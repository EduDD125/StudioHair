package com.example.sistemacabeleleiro.domain.usecases.scheduling;

import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingUpdateDTO;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

import java.time.LocalDateTime;

public class SchedulingUpdateRequestValidator extends Validator<SchedulingUpdateDTO> {
    Notification notification = new Notification();

    @Override
    public Notification validate(SchedulingUpdateDTO scheduling) {
        if (scheduling == null){
            notification.addError("Scheduling is null");
            return notification;
        }

        if (scheduling.schedulingId() < 0)
            notification.addError("Scheduling ID must be positive");
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
