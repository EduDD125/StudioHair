package com.example.sistemacabeleleiro.Domain.UseCases.Service;

import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityAlreadyExistsException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

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
