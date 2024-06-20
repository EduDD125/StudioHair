package com.example.sistemacabeleleiro.domain.usecases.service.dto;

public record ServiceUpdateDTO(int id, String name, String description, double price,
                               String category, String subcategory) {
}
