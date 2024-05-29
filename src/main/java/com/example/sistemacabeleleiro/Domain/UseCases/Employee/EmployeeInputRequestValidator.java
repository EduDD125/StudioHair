package com.example.sistemacabeleleiro.Domain.UseCases.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

public class EmployeeInputRequestValidator extends Validator<Employee> {
    @Override
    public Notification validate(Employee employee) {
        Notification notification = new Notification();

        if (employee == null){
            notification.addError("Employee is null");
        }

        if (nullOrEmpty(employee.getName())){
            notification.addError("Name is null or empty");
        }
        if (nullOrEmpty(employee.getCpf().toString())){
            notification.addError("CPF is null or empty");
        }
        if (nullOrEmpty(employee.getPhone())){
            notification.addError("Phone is null or empty");
        }
        if (nullOrEmpty(employee.getEmail().toString())){
            notification.addError("Email is null or empty");
        }
        if (nullOrEmpty(employee.getDateOfBirth())){
            notification.addError("Date of birth is null or empty");
        }
        if (nullOrEmpty(employee.getStatus().toString())){
            notification.addError("Status is null or empty");
        }
        if (!validCPF(employee.getCpf())){
            notification.addError("CPF is not valid: " + employee.getCpf().getValue());
        }
        if (!validEmail(employee.getEmail())){
            notification.addError("E-mail is not valid: " + employee.getEmail().getValue());
        }

        return notification;
    }
}
