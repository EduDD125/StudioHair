package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.domain.entities.Employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

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
        if (employee.getCpf() == null){
            notification.addError("CPF is null");
        }
        else if (!validCPF(employee.getCpf())){
            notification.addError("CPF is not valid: " + employee.getCpf().getValue());
        }
        if (nullOrEmpty(employee.getPhone())){
            notification.addError("Phone is null or empty");
        }
        if (employee.getEmail() == null){
            notification.addError("Email is null");
        }
        else if (!validEmail(employee.getEmail())){
            notification.addError("E-mail is not valid: " + employee.getEmail().getValue());
        }
        if (nullOrEmpty(employee.getDateOfBirth())){
            notification.addError("Date of birth is null or empty");
        }
        if (nullOrEmpty(employee.getStatus().toString())){
            notification.addError("Status is null or empty");
        }

        return notification;
    }
}
