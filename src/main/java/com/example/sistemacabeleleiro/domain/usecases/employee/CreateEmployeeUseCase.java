package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.domain.entities.CPF.CPF;
import com.example.sistemacabeleleiro.domain.entities.Employee.Employee;
import com.example.sistemacabeleleiro.domain.usecases.client.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityAlreadyExistsException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

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
