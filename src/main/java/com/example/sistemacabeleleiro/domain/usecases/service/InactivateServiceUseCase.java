package com.example.sistemacabeleleiro.domain.usecases.service;

import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.entities.service.ServiceStatus;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class InactivateServiceUseCase {
    private ServiceDAO serviceDAO;

    public InactivateServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public boolean inactivate(Service service){
        Validator<Service> validator = new ServiceInputRequestValidator();
        Notification notification = validator.validate(service);

        if (notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Integer id = service.getId();
        if (serviceDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Service not found.");
        }

        if (service.getStatus() == ServiceStatus.INACTIVE){
            throw new IllegalArgumentException("Service is already inactive.");
        }
        return serviceDAO.inactivate(service);
    }
}
