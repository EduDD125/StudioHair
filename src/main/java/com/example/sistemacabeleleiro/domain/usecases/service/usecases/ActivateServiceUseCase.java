package com.example.sistemacabeleleiro.domain.usecases.service.usecases;

import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class ActivateServiceUseCase {
    private ServiceDAO serviceDAO;

    public ActivateServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public boolean activate(int id){
        Service service = serviceDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        if (service.isActive()){
            throw new IllegalArgumentException("Service is already active.");
        }
        return serviceDAO.activate(service);
    }
}
