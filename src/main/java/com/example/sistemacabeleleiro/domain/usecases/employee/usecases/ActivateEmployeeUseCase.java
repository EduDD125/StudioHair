package com.example.sistemacabeleleiro.domain.usecases.employee.usecases;

import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class ActivateEmployeeUseCase {
    private EmployeeDAO employeeDAO;

    public ActivateEmployeeUseCase(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public boolean activate(int id){

        Employee employee = employeeDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        if (employee.isActive()){
            throw new IllegalArgumentException("Employee is already active");
        }
        return employeeDAO.activate(employee);

    }
}
