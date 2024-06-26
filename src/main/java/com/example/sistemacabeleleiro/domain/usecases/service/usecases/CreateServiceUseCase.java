package com.example.sistemacabeleleiro.domain.usecases.service.usecases;

import com.example.sistemacabeleleiro.domain.usecases.service.dto.ServiceInputDTO;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class CreateServiceUseCase {
    private ServiceDAO serviceDAO;

    public CreateServiceUseCase(ServiceDAO serviceDAO){
        this.serviceDAO = serviceDAO;
    }

    public Integer insert(ServiceInputDTO serviceInputDTO){
        Validator<ServiceInputDTO> validator = new ServiceInputRequestValidator();
        Notification notification = validator.validate(serviceInputDTO);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Service service = new Service(serviceInputDTO.name(),serviceInputDTO.description(),
                serviceInputDTO.price(),serviceInputDTO.category(),serviceInputDTO.subcategory());
        return serviceDAO.create(service);
    }
}
