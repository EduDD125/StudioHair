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

    public boolean cancel(int id) {
        Scheduling scheduling = schedulingDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Scheduling not found"));

        validateScheduling(scheduling);

        scheduling.cancel();
        return schedulingDAO.update(scheduling);
    }

    private void validateScheduling(Scheduling scheduling) {
        if (scheduling.getStatus() != SchedulingStatus.SCHEDULED) {
            throw new IllegalArgumentException("The scheduling has already been canceled or made");
        }
    }
}
