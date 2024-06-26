package com.example.sistemacabeleleiro.application.repository.inmemory;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;

import java.util.*;

public class InMemoryEmployeeDAO implements EmployeeDAO {

    private static final Map<Integer, Employee> db = new LinkedHashMap<>();
    int idCounter;

    @Override
    public Integer create(Employee employee) {
        idCounter++;
        employee.setId(idCounter);
        db.put(idCounter, employee);
        return employee.getId();
    }

    @Override
    public boolean update(Employee employee) {
        int id = employee.getId();
        if(db.containsKey(id)){
            db.replace(id, employee);
            return true;
        }
        return false;
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
    public boolean delete(Employee employee) {
        return deleteByKey(employee.getId());
    }

    @Override
    public boolean inactivate(Employee employee) {
        employee.inactivateStatus();
        return update(employee);
    }

    @Override
    public boolean activate(Employee employee) {
        employee.activateStatus();
        return update(employee);
    }

    @Override
    public boolean addExpertise(Integer employeeID, Integer serviceId) {
        return false;
    }

    @Override
    public boolean removeExpertise(Integer employeeID, Integer serviceId) {
        return false;
    }

    @Override
    public Optional<Employee> findByCpf(CPF cpf) {
        return db.values().stream().filter(employee -> employee.getCpf().equals(cpf)).findAny();
    }
    @Override
    public Optional<Employee> findOne(Integer key) {
        if(db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {
        if(!db.isEmpty())
            return new ArrayList<>(db.values());
        return null;
    }


}
