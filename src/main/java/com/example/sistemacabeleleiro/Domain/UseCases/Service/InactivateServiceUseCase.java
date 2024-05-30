package com.example.sistemacabeleleiro.Domain.UseCases.Service;

import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.Entities.Service.ServiceStatus;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

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
