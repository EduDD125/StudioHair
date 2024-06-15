package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.employee.EmployeeStatus;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class InactivateEmployeeUseCase {
    private EmployeeDAO employeeDAO;

    public InactivateEmployeeUseCase(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public boolean inactivate(Employee employee){
        Validator<Employee> validator = new EmployeeInputRequestValidator();
        Notification notification = validator.validate(employee);

        if (notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Integer id = employee.getId();
        if (employeeDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Employee not found");
        }
        if (employee.getStatus().equals(EmployeeStatus.INACTIVE)){
            throw new IllegalArgumentException("Employee is already inactive");
        }
        return employeeDAO.inactivate(employee);
    }
}
