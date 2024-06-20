package com.example.sistemacabeleleiro.application.dtos.service;

import com.example.sistemacabeleleiro.domain.entities.service.ServiceStatus;

public record ServiceOutputDTO(int id, String name, String description, double price,
                               String category, String subcategory, ServiceStatus status) {
}
