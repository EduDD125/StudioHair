package com.example.sistemacabeleleiro.domain.usecases.scheduling.usecases;

import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.repository.SchedulingDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindSchedulingUseCase {
    private SchedulingDAO schedulingDAO;

    public FindSchedulingUseCase(SchedulingDAO schedulingDAO){
        this.schedulingDAO = schedulingDAO;
    }

    public Optional<SchedulingOutputDTO> findOne(int id){
        return schedulingDAO.findOne(id).map(this::mapToDTO);
    }

    public List<SchedulingOutputDTO> findByClient(int clientId){
        return schedulingDAO.findByClient(clientId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<SchedulingOutputDTO> findByEmployee(int employeeId){
        return schedulingDAO.findByEmployee(employeeId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<SchedulingOutputDTO> findByService(int id){
        return schedulingDAO.findByService(id).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<SchedulingOutputDTO> findByScheduledDate(LocalDateTime date){
        if(date == null){
            throw new IllegalArgumentException("Date can not be null");
        }
        return schedulingDAO.findByScheduledDate(date).map(this::mapToDTO);
    }

    public List<SchedulingOutputDTO> findByTimePeriod(LocalDate startDate, LocalDate endDate){
        if(startDate == null || endDate == null){
            throw new IllegalArgumentException("Dates can not be null");
        }
        return schedulingDAO.findByTimePeriod(startDate, endDate).stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<SchedulingOutputDTO> findAll(){
        return schedulingDAO.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private SchedulingOutputDTO mapToDTO(Scheduling scheduling){
        return new SchedulingOutputDTO(
                scheduling.getId(),
                scheduling.getClient().getId(),
                scheduling.getClient().getName(),
                scheduling.getEmployee().getId(),
                scheduling.getEmployee().getName(),
                scheduling.getService().getId(),
                scheduling.getService().getName(),
                scheduling.getRealizationDate(),
                scheduling.getStatus()
        );
    }
}
