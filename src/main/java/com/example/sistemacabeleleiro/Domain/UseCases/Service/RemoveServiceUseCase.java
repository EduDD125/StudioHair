package com.example.sistemacabeleleiro.Domain.UseCases.Service;

import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;

public class RemoveServiceUseCase {

    private ServiceDAO serviceDAO;

    public RemoveServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public boolean remove(Integer id){
        if(id == null || serviceDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Service not found.");
        return serviceDAO.deleteByKey(id);
    }

    public boolean remove (Service service){
        if(service == null || serviceDAO.findOne(service.getId()).isEmpty())
            throw new EntityNotFoundException("Service not found.");
        return serviceDAO.delete(service);
    }
}
