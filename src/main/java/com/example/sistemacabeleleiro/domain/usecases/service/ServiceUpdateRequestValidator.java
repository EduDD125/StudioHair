package com.example.sistemacabeleleiro.domain.usecases.service;

import com.example.sistemacabeleleiro.application.dtos.service.ServiceUpdateDTO;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class ServiceUpdateRequestValidator extends Validator<ServiceUpdateDTO> {
    @Override
    public Notification validate(ServiceUpdateDTO service) {
        Notification notification = new Notification();

        if(service == null ){
            notification.addError("Service is null.");
            return notification;
        }

        if(nullOrEmpty(service.name()))
            notification.addError("Name is null or empty.");
        if(nullOrEmpty(service.description()))
            notification.addError("Description is null or empty.");
        if(Validator.nullOrNegativePrice(service.price()))
            notification.addError("Price is null or empty.");

        return notification;
    }
}
