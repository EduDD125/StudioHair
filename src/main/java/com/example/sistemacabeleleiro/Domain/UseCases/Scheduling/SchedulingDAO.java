package com.example.sistemacabeleleiro.Domain.UseCases.Scheduling;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.DAO;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SchedulingDAO extends DAO<Scheduling, Integer> {
    Optional<Scheduling> findByScheduledDate(LocalDateTime scheduledDate);
    Optional<Scheduling> findByEmployee(Employee employee);
}
