package com.example.sistemacabeleleiro.domain.usecases.scheduling;

import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class CancelSchedulingUseCase {
    private SchedulingDAO schedulingDAO;

    public CancelSchedulingUseCase(SchedulingDAO schedulingDAO) {
        this.schedulingDAO = schedulingDAO;
    }

    public boolean cancel(Scheduling scheduling) {
        validateScheduling(scheduling);

        scheduling.cancel();
        return schedulingDAO.update(scheduling);
    }

    private void validateScheduling(Scheduling scheduling) {
        if (scheduling == null) {
            throw new IllegalArgumentException("Scheduling cannot be null");
        }

        Validator<Scheduling> validator = new SchedulingInputRequestValidator();
        Notification notification = validator.validate(scheduling);

        if (notification.hasErros()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }

        if (!schedulingDAO.findOne(scheduling.getId()).isPresent()) {
            throw new EntityNotFoundException("Scheduling not found");
        }

        if (scheduling.getStatus() != SchedulingStatus.SCHEDULED) {
            throw new IllegalArgumentException("The scheduling has already been canceled or made");
        }
    }
}
