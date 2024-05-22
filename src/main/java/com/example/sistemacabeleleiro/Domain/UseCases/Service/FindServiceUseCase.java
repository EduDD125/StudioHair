package com.example.sistemacabeleleiro.Domain.UseCases.Service;

import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;

import java.util.List;
import java.util.Optional;

public class FindServiceUseCase {
    private ServiceDAO serviceDAO;

    public FindServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public Optional<Service> findOne(Integer id){
        if(id == null)
            throw new IllegalArgumentException("ID can not be null.");
        return  serviceDAO.findOne(id);
    }

    public List<Service> findAll(){
        return serviceDAO.findAll();
    }
}
