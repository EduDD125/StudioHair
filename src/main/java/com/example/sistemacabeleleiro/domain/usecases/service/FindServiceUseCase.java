package com.example.sistemacabeleleiro.domain.usecases.service;

import com.example.sistemacabeleleiro.domain.entities.service.Service;

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

    public List<Service> findByPriceRange(double minPrice, double maxPrice) {
        if (minPrice >= maxPrice)
            throw new IllegalArgumentException("Minimum price can not be greater than maximum price.");
        return serviceDAO.findByPriceRange(minPrice, maxPrice);
    }

    public List<Service> findByCategory(String category) {
        if (category == null || category.isEmpty())
            throw new IllegalArgumentException("The category is null or empty.");
        return serviceDAO.findByCategory(category);
    }

    public List<Service> findByDiscount(Double discount) {
        if (discount == 0.0)
            throw new IllegalArgumentException("This service does not have a discount.");
        return serviceDAO.findByDiscount(discount);
    }
}
