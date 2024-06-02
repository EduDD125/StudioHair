package com.example.sistemacabeleleiro.Domain.UseCases.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.UseCases.Client.ClientDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityAlreadyExistsException;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Notification;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.Validator;

public class CreateEmployeeUseCase {
    private EmployeeDAO employeeDAO;
    private ClientDAO clientDAO;

    public CreateEmployeeUseCase(EmployeeDAO employeeDAO, ClientDAO clientDAO) {
        this.employeeDAO = employeeDAO;
        this.clientDAO = clientDAO;
    }

    public Integer insert(Employee employee){
        Validator<Employee> validator = new EmployeeInputRequestValidator();
        Notification notification = validator.validate(employee);

        if (notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        CPF cpf = employee.getCpf();
        if (employeeDAO.findByCpf(cpf).isPresent() || clientDAO.findOneByCPF(cpf).isPresent()){
            throw new EntityAlreadyExistsException("This CPF is already in use");
        }
        return employeeDAO.create(employee);
    }
}
