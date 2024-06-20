package com.example.sistemacabeleleiro.domain.usecases.service;

import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.entities.service.ServiceStatus;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

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
