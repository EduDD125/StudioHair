package com.example.sistemacabeleleiro.domain.usecases.service;

import com.example.sistemacabeleleiro.application.dtos.service.ServiceInputDTO;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class ServiceInputRequestValidator extends Validator<ServiceInputDTO> {
    @Override
    public Notification validate(ServiceInputDTO service) {
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
