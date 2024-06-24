package com.example.sistemacabeleleiro.domain.usecases.service.usecases;

import com.example.sistemacabeleleiro.domain.usecases.service.dto.ServiceOutputDTO;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindServiceUseCase {
    private ServiceDAO serviceDAO;

    public FindServiceUseCase(ServiceDAO serviceDAO) {
        this.serviceDAO = serviceDAO;
    }

    public static Optional<ServiceOutputDTO> findOne(int id){
        return  serviceDAO.findOne(id).map(this::mapToDTO);
    }

    public List<ServiceOutputDTO> findAll(){
        List<Service> services = serviceDAO.findAll();
        return services.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ServiceOutputDTO> findByPriceRange(double minPrice, double maxPrice) {
        if (minPrice >= maxPrice)
            throw new IllegalArgumentException("Minimum price can not be greater than maximum price.");
        return serviceDAO.findByPriceRange(minPrice, maxPrice).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ServiceOutputDTO> findByCategory(String category) {
        if (category == null || category.isEmpty())
            throw new IllegalArgumentException("The category is null or empty.");
        return serviceDAO.findByCategory(category).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ServiceOutputDTO> findByDiscount(double discount) {
        if (discount == 0.0)
            throw new IllegalArgumentException("This service does not have a discount.");
        return serviceDAO.findByDiscount(discount).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ServiceOutputDTO mapToDTO(Service service){
        return new ServiceOutputDTO(service.getId(),service.getName(),service.getDescription(),
                service.getPrice(),service.getCategory(),service.getSubCategory(),service.getStatus());
    }
}
