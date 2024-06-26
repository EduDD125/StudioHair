package com.example.sistemacabeleleiro.domain.usecases.employee.usecases;

import com.example.sistemacabeleleiro.domain.usecases.employee.dto.EmployeeUpdateDTO;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
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

        int id = employeeUpdateDTO.id();

        Employee employee = employeeDAO.findOne(id)
                .orElseThrow(()-> new EntityNotFoundException("Employee not found"));

        employee.setName(employeeUpdateDTO.name());
        employee.setCpf(employeeUpdateDTO.cpf());
        employee.setPhone(employeeUpdateDTO.phone());
        employee.setEmail(employeeUpdateDTO.email());
        employee.setDateOfBirth(employeeUpdateDTO.dateOfBirth());
        return employeeDAO.update(employee);
    }
}
