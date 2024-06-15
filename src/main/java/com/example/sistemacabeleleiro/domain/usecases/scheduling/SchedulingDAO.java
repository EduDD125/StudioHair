package com.example.sistemacabeleleiro.domain.usecases.scheduling;

import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.usecases.utils.DAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulingDAO extends DAO<Scheduling, Integer> {
    Integer cancel(Scheduling schedule);
    Optional<Scheduling> findByScheduledDate(LocalDateTime scheduledDate);
    List<Scheduling> findByEmployee(Integer id);
    List<Scheduling> findByClient(Integer id);
    List<Scheduling> findByService(Integer id);
    List<Scheduling> findByTimePeriod(LocalDate startDate, LocalDate endDate);

    List<Scheduling> findAllByServiceId(Integer serviceId);
}
