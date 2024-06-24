package com.example.sistemacabeleleiro.domain.usecases.scheduling.dto;

import com.example.sistemacabeleleiro.domain.entities.schedulling.SchedulingStatus;

import java.time.LocalDateTime;

public record SchedulingOutputDTO(int id, int clientId, String clientName, int employeeId, String employeeName,
                                  int serviceId, String serviceName, LocalDateTime realizationDate,
                                  SchedulingStatus status) {
}
