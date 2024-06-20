package com.example.sistemacabeleleiro.domain.usecases.employee.usecases;

import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class InactivateEmployeeUseCase {
    private EmployeeDAO employeeDAO;

    public InactivateEmployeeUseCase(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public boolean inactivate(int id){

        Employee employee = employeeDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        if (employee.isInactive()){
            throw new IllegalArgumentException("Employee is already inactive");
        }
        return employeeDAO.inactivate(employee);
    }
}
