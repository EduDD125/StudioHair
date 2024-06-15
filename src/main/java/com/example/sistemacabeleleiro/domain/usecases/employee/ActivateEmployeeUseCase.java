package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.application.dtos.employee.EmployeeInputDTO;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.employee.EmployeeStatus;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class ActivateEmployeeUseCase {
    private EmployeeDAO employeeDAO;

    public ActivateEmployeeUseCase(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public boolean activate(Integer id){

        Employee employee = employeeDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        if (employee.getStatus().equals(EmployeeStatus.ACTIVE)){
            throw new IllegalArgumentException("Employee is already active");
        }
        return employeeDAO.activate(employee);

    }
}
