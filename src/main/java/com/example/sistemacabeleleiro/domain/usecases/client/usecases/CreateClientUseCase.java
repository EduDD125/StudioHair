package com.example.sistemacabeleleiro.domain.usecases.client.usecases;

import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientInputDTO;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.usecases.client.repository.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
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

    public Integer insert(ClientInputDTO clientInputDTO) {
        Validator<ClientInputDTO> validator = new ClientInputRequestValidator();
        Notification notification = validator.validate(clientInputDTO);

        if(notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        CPF cpf = clientInputDTO.cpf();
        if(clientDAO.findOneByCPF(cpf).isPresent() || employeeDAO.findByCpf(cpf).isPresent())
            throw new EntityAlreadyExistsException("This CPF is already in use.");

        Client client = new Client(clientInputDTO.name(),clientInputDTO.cpf(),
                clientInputDTO.phone(), clientInputDTO.email());

        return clientDAO.create(client);
    }
}
