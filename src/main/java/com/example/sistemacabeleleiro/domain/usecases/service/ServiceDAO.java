package com.example.sistemacabeleleiro.domain.usecases.service;

import com.example.sistemacabeleleiro.domain.entities.Service.Service;
import com.example.sistemacabeleleiro.domain.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface ServiceDAO extends DAO<Service, Integer> {
    Optional<Service> findById(Integer id);
    List<Service> findByPriceRange(double minPrice, double maxPrice);
    List<Service> findByCategory(String category);
    List<Service> findByDiscount(Double discount);

    boolean inactivate(Service service);
    boolean activate(Service service);
}
