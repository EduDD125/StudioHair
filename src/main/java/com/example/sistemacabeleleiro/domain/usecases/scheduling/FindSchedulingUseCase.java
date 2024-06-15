package com.example.sistemacabeleleiro.domain.usecases.scheduling;

import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class FindSchedulingUseCase {
    private SchedulingDAO schedulingDAO;

    public FindSchedulingUseCase(SchedulingDAO schedulingDAO){
        this.schedulingDAO = schedulingDAO;
    }

    public Optional<Scheduling> findOne(Integer id){
        if(id == null){
            throw new IllegalArgumentException("ID can not be null");
        }
        return schedulingDAO.findOne(id);
    }

    public List<Scheduling> findByClient(Integer id){
        if(id == null){
            throw new IllegalArgumentException("ID can not be null");
        }
        return schedulingDAO.findByClient(id);
    }

    public List<Scheduling> findByEmployee(Integer id){
        if(id == null){
            throw new IllegalArgumentException("ID can not be null");
        }
        return schedulingDAO.findByEmployee(id);
    }

    public List<Scheduling> findByService(Integer id){
        if(id == null){
            throw new IllegalArgumentException("ID can not be null");
        }
        return schedulingDAO.findByService(id);
    }

    public Optional<Scheduling> findByScheduledDate(LocalDateTime date){
        if(date == null){
            throw new IllegalArgumentException("Date can not be null");
        }
        return schedulingDAO.findByScheduledDate(date);
    }

    public List<Scheduling> findByTimePeriod(LocalDate startDate, LocalDate endDate){
        if(startDate == null || endDate == null){
            throw new IllegalArgumentException("Dates can not be null");
        }
        return schedulingDAO.findByTimePeriod(startDate, endDate);
    }

    public List<Scheduling> findAll(){
        return schedulingDAO.findAll();
    }
}
