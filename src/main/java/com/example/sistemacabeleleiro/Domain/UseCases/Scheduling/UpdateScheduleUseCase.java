package com.example.sistemacabeleleiro.Domain.UseCases.Scheduling;

import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

public class UpdateScheduleUseCase {
    private SchedulingDAO schedulingDAO;

    public UpdateScheduleUseCase(SchedulingDAO schedulingDAO){
        this.schedulingDAO = schedulingDAO;
    }

    public boolean update(Scheduling scheduling){
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(scheduling);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = scheduling.getId();

        if(schedulingDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Scheduling not found.");
        }

        return schedulingDAO.update(scheduling);
    }
}
