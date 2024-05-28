package com.example.sistemacabeleleiro.Domain.UseCases.Service;

import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.DAO;

import java.util.List;
import java.util.Optional;

public interface ServiceDAO extends DAO<Service, Integer> {
    Optional<Service> findById(Integer id);
    List<Service> findMostFrequent();
    List<Service> findByPriceRange(double minPrice, double maxPrice);
    List<Service> findByCategory(String category);
    List<Service> findWithDiscount(Double discount);
}
