package com.example.sistemacabeleleiro.Domain.UseCases.Scheduling;

import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

import java.time.LocalDateTime;

public class CancelSchedulingUseCase {
    private SchedulingDAO schedulingDAO;

    public CancelSchedulingUseCase(SchedulingDAO schedulingDAO){ this.schedulingDAO = schedulingDAO; }

    public Integer cancel(Scheduling scheduling){
        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(scheduling);

        if(notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }
        if(!schedulingDAO.findOne(scheduling.getId()).isPresent()){
            throw new EntityNotFoundException("There is no Scheduling with this ID");
        }
        if (scheduling.getStatus() != SchedulingStatus.SCHEDULED) {
            throw new IllegalArgumentException("The scheduling has already been canceled or made");
        }

        return schedulingDAO.cancel(scheduling);
    }
}
