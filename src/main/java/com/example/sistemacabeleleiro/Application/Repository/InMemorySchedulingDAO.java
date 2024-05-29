package com.example.sistemacabeleleiro.Application.Repository;

import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.SchedulingDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class InMemorySchedulingDAO implements SchedulingDAO {

    private static final Map<Integer, Scheduling> db = new LinkedHashMap<>();
    int idCounter;

    @Override
    public Integer create(Scheduling scheduling) {
        idCounter++;
        scheduling.setId(idCounter);
        db.put(idCounter, scheduling);
        return scheduling.getId();
    }

    @Override
    public boolean update(Scheduling scheduling) {
        Integer id = scheduling.getId();
        if(db.containsKey(id)) {
            db.replace(id, scheduling);
            return true;
        }
        return false;
    }

    @Override
    public Integer cancel(Scheduling schedule) {
        schedule.cancel();
        update(schedule);
        return schedule.getId();
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if(db.containsKey(key)){
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Scheduling scheduling) {
        return deleteByKey(scheduling.getId());
    }


    @Override
    public Optional<Scheduling> findByScheduledDate(LocalDateTime scheduledDate) {
        return db.values().stream().filter(
                scheduling -> scheduling.getDataRealizacao().equals(scheduledDate)
            ).findAny();
    }

    @Override
    public List<Scheduling> findByEmployee(Integer id) {
        return db.values().stream().filter(
                scheduling -> scheduling.getEmployee().getId().equals(id)
            ).toList();
    }

    @Override
    public List<Scheduling> findByClient(Integer id) {
        return db.values().stream().filter(
                scheduling -> scheduling.getClient().getId().equals(id)
        ).toList();
    }

    @Override
    public List<Scheduling> findByService(Integer id) {
        return db.values().stream().filter(
                scheduling -> scheduling.getId().equals(id)
        ).toList();
    }

    @Override
    public List<Scheduling> findByTimePeriod(LocalDate startDate, LocalDate endDate) {
        return db.values().stream().filter( scheduling ->
                scheduling.isDateInsidePeriod(startDate, endDate)).toList();
    }

    @Override
    public List<Scheduling> findAllByServiceId(Integer serviceId) {
        return db.values().stream().filter(scheduling ->
                scheduling.getService().getId().equals(serviceId)).toList();
    }

    @Override
    public Optional<Scheduling> findOne(Integer key) {
        if (db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Scheduling> findAll() {
        if (!db.isEmpty())
            return db.values().stream().toList();
        return null;
    }
}
