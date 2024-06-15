package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.application.dtos.employee.EmployeeInputDTO;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class EmployeeInputRequestValidator extends Validator<EmployeeInputDTO> {
    @Override
    public Notification validate(EmployeeInputDTO employee) {
        Notification notification = new Notification();

        if (employee == null){
            notification.addError("Employee is null");
        }

        if (nullOrEmpty(employee.name())){
            notification.addError("Name is null or empty");
        }
        if (employee.cpf() == null){
            notification.addError("CPF is null");
        }
        else if (!validCPF(employee.cpf())){
            notification.addError("CPF is not valid: " + employee.cpf().getValue());
        }
        if (nullOrEmpty(employee.phone())){
            notification.addError("Phone is null or empty");
        }
        if (employee.email() == null){
            notification.addError("Email is null");
        }
        else if (!validEmail(employee.email())){
            notification.addError("E-mail is not valid: " + employee.email().getValue());
        }
        if (nullOrEmpty(employee.dateOfBirth())){
            notification.addError("Date of birth is null or empty");
        }

        return notification;
    }
}
