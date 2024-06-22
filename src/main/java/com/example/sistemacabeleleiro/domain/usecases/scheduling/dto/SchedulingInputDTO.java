package com.example.sistemacabeleleiro.domain.usecases.scheduling.dto;

import java.time.LocalDateTime;

public record SchedulingInputDTO(int clientId, int employeeId, int serviceId, LocalDateTime realizationDate) {
}
