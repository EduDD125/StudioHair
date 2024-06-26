package com.example.sistemacabeleleiro.domain.usecases.service.usecases;

import com.example.sistemacabeleleiro.domain.usecases.service.dto.ServiceUpdateDTO;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
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

            service.setName(serviceUpdateDTO.name());
            service.setDescription(serviceUpdateDTO.description());
            service.setPrice(serviceUpdateDTO.price());
            service.setCategory(serviceUpdateDTO.category());
            service.setSubCategory(serviceUpdateDTO.subcategory());
            return serviceDAO.update(service);
        }

}
