package com.example.sistemacabeleleiro.domain.usecases.scheduling;

import com.example.sistemacabeleleiro.domain.entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.Schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class CancelSchedulingUseCase {
    private SchedulingDAO schedulingDAO;

    public CancelSchedulingUseCase(SchedulingDAO schedulingDAO){ this.schedulingDAO = schedulingDAO; }

    public Integer cancel(Scheduling scheduling){
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(scheduling);

        List<Scheduling> allSchedulings = schedulingDAO.findAll();
        List<Scheduling> schedulingsToCancel = new ArrayList<>();
        for(Scheduling s: allSchedulings){
            if(s.getStatus() == SchedulingStatus.SCHEDULED){
                schedulingsToCancel.add(s);
            }
        }

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }
        if(scheduling == null || !schedulingDAO.findOne(scheduling.getId()).isPresent()){
            throw new EntityNotFoundException("Scheduling not found");
        }
        if (scheduling.getStatus() != SchedulingStatus.SCHEDULED) {
            throw new IllegalArgumentException("The scheduling has already been canceled or made");
        }

        return schedulingDAO.cancel(scheduling);
    }
}
