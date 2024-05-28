package com.example.sistemacabeleleiro.Domain.UseCases.Scheduling;

import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

public class SchedulingInputRequestValidator extends Validator<Scheduling> {
    Notification notification = new Notification();
    @Override
    public Notification validate(Scheduling scheduling) {
        if(scheduling == null){
            notification.addError("Schedulling is null");
            return notification;
        }
        if(nullOrEmpty(scheduling.getClient().getName()))
            notification.addError("Client is null or empty");
        if(nullOrEmpty(scheduling.getEmployee().getName()))
            notification.addError("Employee is null or empty");
        if(nullOrEmpty(scheduling.getService().getName()))
            notification.addError("Service is null or empty");
        if(nullOrEmpty(scheduling.getDataRealizacao().toString()))
            notification.addError("Scheduled date is null or empty");

        return notification;
    }
}
