package com.example.sistemacabeleleiro.Domain.UseCases.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.DAO;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO extends DAO<Employee,Integer> {

    Optional<Employee> findByCpf(CPF cpf);
    List<Employee> findAll();
}
