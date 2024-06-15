package com.example.sistemacabeleleiro.domain.usecases.service;

import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class ServiceInputRequestValidator extends Validator<Service> {
    @Override
    public Notification validate(Service service) {
        Notification notification = new Notification();
        if(service == null ){
            notification.addError("Service is null.");
            return notification;
        }
        if(nullOrEmpty(service.getName()))
            notification.addError("Name is null or empty.");
        if(nullOrEmpty(service.getDescription()))
            notification.addError("Description is null or empty.");
        if(Validator.nullOrNegativePrice(service.getPrice()))
            notification.addError("Price is null or empty.");

        return notification;
    }
}
