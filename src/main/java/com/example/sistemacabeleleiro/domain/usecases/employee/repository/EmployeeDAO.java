package com.example.sistemacabeleleiro.domain.usecases.employee.repository;

import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.utils.DAO;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO extends DAO<Employee,Integer> {

    Optional<Employee> findByCpf(CPF cpf);
    List<Employee> findAll();
    boolean inactivate(Employee employee);
    boolean activate(Employee employee);
    boolean addExpertise(Integer employeeID, Integer serviceId);
    boolean removeExpertise(Integer employeeID, Integer serviceId);
}
