package com.example.sistemacabeleleiro.Domain.UseCases.Service;

import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityAlreadyExistsException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

public class CreateServiceUseCase {
    private ServiceDAO serviceDAO;

    public CreateServiceUseCase(ServiceDAO serviceDAO){
        this.serviceDAO = serviceDAO;
    }

    public Integer insert(Service service){
        Validator<Service> validator = new ServiceInputRequestValidator();
        Notification notification = validator.validate(service);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = service.getId();
        if(serviceDAO.findById(id).isPresent())
            throw new EntityAlreadyExistsException("This service ID is already in use.");
        return serviceDAO.create(service);
    }
}
