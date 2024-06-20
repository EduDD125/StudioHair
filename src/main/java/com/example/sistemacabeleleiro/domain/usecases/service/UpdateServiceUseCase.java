package com.example.sistemacabeleleiro.domain.usecases.service;

import com.example.sistemacabeleleiro.application.dtos.service.ServiceUpdateDTO;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class UpdateServiceUseCase {
    private ServiceDAO serviceDAO;

    public UpdateServiceUseCase(ServiceDAO serviceDAO){
            this.serviceDAO = serviceDAO;
        }
        public boolean update(ServiceUpdateDTO serviceUpdateDTO){
            Validator<ServiceUpdateDTO> validator = new ServiceUpdateRequestValidator();
            Notification notification = validator.validate(serviceUpdateDTO);

            if(notification.hasErros())
                throw new IllegalArgumentException(notification.errorMessage());

            int id = serviceUpdateDTO.id();

            Service service = serviceDAO.findOne(id)
                    .orElseThrow(()-> new EntityNotFoundException("Service not found"));
            return serviceDAO.update(service);
        }

}
