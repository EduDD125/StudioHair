package com.example.sistemacabeleleiro.Domain.UseCases.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

import java.util.List;
import java.util.Optional;

public class FindEmployeeUseCase {
    private EmployeeDAO employeeDAO;

    public FindEmployeeUseCase(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    public Optional<Employee> findOne(Integer id){
        if (id == null){
            throw new IllegalArgumentException("ID can't be null");
        }
        return employeeDAO.findOne(id);
    }
    public Optional<Employee> findOneByCpf(CPF cpf){
        if (Validator.nullOrEmpty(cpf.toString())){
            throw new IllegalArgumentException("CPF can't be null or empty");
        }
        if (!Validator.validCPF(cpf)){
            throw new IllegalArgumentException("CPF is not valid: " + cpf);
        }
        return employeeDAO.findByCpf(cpf);
    }
    public static List<Employee> findAll(){
        return employeeDAO.findAll();
    }
}
