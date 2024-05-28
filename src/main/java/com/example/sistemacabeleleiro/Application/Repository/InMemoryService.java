package com.example.sistemacabeleleiro.Application.Repository;

import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Service.ServiceDAO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryService implements ServiceDAO {

    private static final Map<Integer, Service> db = new LinkedHashMap<>();
    private int idCounter;

    @Override
    public Integer create(Service service) {
        idCounter++;
        service.setId(idCounter);
        db.put(idCounter, service);
        return service.getId();
    }

    @Override
    public boolean update(Service service) {
        int id = service.getId();
        if (db.containsKey(id)) {
            db.replace(id, service);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (db.containsKey(key)) {
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Service service) {
        return deleteByKey(service.getId());
    }

    @Override
    public Optional<Service> findById(Integer id) {
        return db.values().stream().filter(service ->
                service.getId().equals(id)) .findAny();
    }

    @Override
    public Optional<Service> findOne(Integer key) {
        if(db.containsKey(key)) {
            return Optional.of(db.get(key));
        }
        return Optional.empty();
    }

    @Override
    public List<Service> findAll() {
        if(!db.isEmpty()) {
            return db.values().stream().toList();
        }
        return null;
    }

}
