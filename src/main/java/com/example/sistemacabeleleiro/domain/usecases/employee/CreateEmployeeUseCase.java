package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.application.dtos.employee.EmployeeInputDTO;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
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

    public Integer insert(EmployeeInputDTO employeeInputDTO){
        Validator<EmployeeInputDTO> validator = new EmployeeInputRequestValidator();
        Notification notification = validator.validate(employeeInputDTO);

        if (notification.hasErros()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        CPF cpf = employeeInputDTO.cpf();
        if (employeeDAO.findByCpf(cpf).isPresent() || clientDAO.findOneByCPF(cpf).isPresent()){
            throw new EntityAlreadyExistsException("This CPF is already in use");
        }

        Employee employee = new Employee(employeeInputDTO.name(),employeeInputDTO.cpf(),
                employeeInputDTO.phone(),employeeInputDTO.email(), employeeInputDTO.dateOfBirth());


        return employeeDAO.create(employee);
    }
}
