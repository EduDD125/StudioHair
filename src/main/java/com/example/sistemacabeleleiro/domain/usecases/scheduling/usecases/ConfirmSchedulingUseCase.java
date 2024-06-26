package com.example.sistemacabeleleiro.domain.usecases.scheduling.usecases;

import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.repository.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class ConfirmSchedulingUseCase {
    private SchedulingDAO schedulingDAO;

    public ConfirmSchedulingUseCase(SchedulingDAO schedulingDAO) {
        this.schedulingDAO = schedulingDAO;
    }

    public Integer confirm(int id){
        Scheduling scheduling = schedulingDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Scheduling not found"));

        validateScheduling(scheduling);

        scheduling.execute();
        return schedulingDAO.confirm(scheduling);
    }

    private void validateScheduling(Scheduling scheduling){
        if (scheduling.getStatus() != SchedulingStatus.SCHEDULED)
            throw new IllegalArgumentException("The scheduling has already been canceled or made");

    }
}
