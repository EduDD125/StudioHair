package com.example.sistemacabeleleiro.domain.usecases.client;

import com.example.sistemacabeleleiro.domain.entities.CPF.CPF;
import com.example.sistemacabeleleiro.domain.entities.Client.Client;
import com.example.sistemacabeleleiro.domain.usecases.employee.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityAlreadyExistsException;
import com.example.sistemacabeleleiro.domain.usecases.utils.Notification;
import com.example.sistemacabeleleiro.domain.usecases.utils.Validator;

public class CreateClientUseCase {
    private ClientDAO clientDAO;
    private EmployeeDAO employeeDAO;

    public CreateClientUseCase(ClientDAO clientDAO, EmployeeDAO employeeDAO) {
        this.clientDAO = clientDAO;
        this.employeeDAO = employeeDAO;
    }

    public Integer insert(Client client) {
        Validator<Client> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(client);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        CPF cpf = client.getCpf();
        if(clientDAO.findOneByCPF(cpf).isPresent() || employeeDAO.findByCpf(cpf).isPresent())
            throw new EntityAlreadyExistsException("This CPF is already in use.");

        return clientDAO.create(client);
    }
}
