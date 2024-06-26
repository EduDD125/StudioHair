package com.example.sistemacabeleleiro.domain.usecases.scheduling.usecases;

import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.repository.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class CancelSchedulingUseCase {
    private SchedulingDAO schedulingDAO;

    public CancelSchedulingUseCase(SchedulingDAO schedulingDAO) {
        this.schedulingDAO = schedulingDAO;
    }

    public Integer cancel(int id) {
        Scheduling scheduling = schedulingDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Scheduling not found"));

        validateScheduling(scheduling);

        scheduling.cancel();
        return schedulingDAO.cancel(scheduling);
    }

    private void validateScheduling(Scheduling scheduling) {
        if (scheduling.getStatus() != SchedulingStatus.SCHEDULED) {
            throw new IllegalArgumentException("The scheduling has already been canceled or made");
        }
    }
}
