package com.example.sistemacabeleleiro.Application.Repository;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.EmployeeDAO;

import java.util.*;

public class InMemoryEmployee implements EmployeeDAO {

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
