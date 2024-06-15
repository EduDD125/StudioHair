package com.example.sistemacabeleleiro.domain.usecases.service;

import com.example.sistemacabeleleiro.domain.entities.Service.Service;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class UpdateServiceUseCase {
    private ServiceDAO serviceDAO;

    public UpdateServiceUseCase(ServiceDAO serviceDAO){
            this.serviceDAO = serviceDAO;
        }
        public boolean update(Service service){
            Validator<Service> validator = new ServiceInputRequestValidator();
            Notification notification = validator.validate(service);

            if(notification.hasErros())
                throw new IllegalArgumentException(notification.errorMessage());

            Integer id = service.getId();
            if(serviceDAO.findOne(id).isEmpty())
                throw new EntityNotFoundException("Service not found.");

            return serviceDAO.update(service);
        }

}
