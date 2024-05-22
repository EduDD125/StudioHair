package com.example.sistemacabeleleiro.Domain.UseCases.Scheduling;

import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;

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

    public List<Scheduling> findAll(){
        return schedulingDAO.findAll();
    }
}
