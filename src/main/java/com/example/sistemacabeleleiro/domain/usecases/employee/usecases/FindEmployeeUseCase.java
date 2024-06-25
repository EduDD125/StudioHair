package com.example.sistemacabeleleiro.domain.usecases.employee.usecases;

import com.example.sistemacabeleleiro.domain.usecases.employee.dto.EmployeeOutputDTO;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindEmployeeUseCase {
    private EmployeeDAO employeeDAO;

    public FindEmployeeUseCase(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    public Optional<EmployeeOutputDTO> findOne(int id){
        return employeeDAO.findOne(id).map(this::mapToDTO);
    }
    public Optional<EmployeeOutputDTO> findOneByCpf(CPF cpf){
        if (Validator.nullOrEmpty(cpf.toString())){
            throw new IllegalArgumentException("CPF can't be null or empty");
        }
        if (!Validator.validCPF(cpf)){
            throw new IllegalArgumentException("CPF is not valid: " + cpf);
        }
        return employeeDAO.findByCpf(cpf).map(this::mapToDTO);
    }

    public Optional<EmployeeOutputDTO> findOneByName(String name){
        if (Validator.nullOrEmpty(name)){
            throw new IllegalArgumentException("Name can't be null or empty");
        }
        return employeeDAO.findByName(name).map(this::mapToDTO);
    }

    public List<EmployeeOutputDTO> findAll(){
        List<Employee> employees = employeeDAO.findAll();
        return employees.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private EmployeeOutputDTO mapToDTO(Employee employee){
        return new EmployeeOutputDTO(employee.getId(), employee.getName(),employee.getCpf(),
                employee.getPhone(), employee.getEmail(), employee.getDateOfBirth(),employee.getStatus());
    }
}
