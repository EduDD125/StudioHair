package com.example.sistemacabeleleiro.Domain.UseCases.Scheduling;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.DAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulingDAO extends DAO<Scheduling, Integer> {
    Optional<Scheduling> findByScheduledDate(LocalDateTime scheduledDate);
    List<Scheduling> findByEmployee(Integer id);
    List<Scheduling> findByClient(Integer id);
    List<Scheduling> findByService(Integer id);
    List<Scheduling> findByTimePeriod(LocalDate startDate, LocalDate endDate);

    List<Scheduling> findAllByServiceId(Integer serviceId);
}
