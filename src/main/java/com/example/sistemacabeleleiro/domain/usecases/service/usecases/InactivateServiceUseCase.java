package com.example.sistemacabeleleiro.domain.usecases.service.usecases;

import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class InactivateServiceUseCase {
    private ServiceDAO serviceDAO;

    public InactivateServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public boolean inactivate(int id){
        Service service = serviceDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));

        if (service.isInactive()){
            throw new IllegalArgumentException("Service is already inactive.");
        }
        return serviceDAO.inactivate(service);
    }
}
