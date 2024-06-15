package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.application.dtos.employee.EmployeeInputDTO;
import com.example.sistemacabeleleiro.application.dtos.employee.EmployeeUpdateDTO;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class UpdateEmployeeUseCase {
    private EmployeeDAO employeeDAO;

    public UpdateEmployeeUseCase(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public boolean update(EmployeeUpdateDTO employeeUpdateDTO){
        Validator<EmployeeUpdateDTO> validator = new EmployeeUpdateRequestValidator();
        Notification notification = validator.validate(employeeUpdateDTO);

        if (notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Integer id = employeeUpdateDTO.id();

        Employee employee = employeeDAO.findOne(id)
                .orElseThrow(()-> new EntityNotFoundException("Employee not found"));
        return employeeDAO.update(employee);
    }
}
