package com.example.sistemacabeleleiro.Domain.UseCases.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.EmployeeStatus;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

public class ActivateEmployeeUseCase {
    private EmployeeDAO employeeDAO;

    public ActivateEmployeeUseCase(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public boolean activate(Employee employee){
        Validator<Employee> validator = new EmployeeInputRequestValidator();
        Notification notification = validator.validate(employee);

        if (notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Integer id = employee.getId();
        if (employeeDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Employee not found");
        }
        if (employee.getStatus().equals(EmployeeStatus.ACTIVE)){
            throw new IllegalArgumentException("Employee is already active");
        }
        return employeeDAO.activate(employee);
    }
}
