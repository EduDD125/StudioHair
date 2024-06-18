package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.entities.service.ServiceStatus;
import com.example.sistemacabeleleiro.domain.usecases.service.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class AddEmployeeExpertiseUseCase {
    private EmployeeDAO employeeDAO;
    private ServiceDAO serviceDAO;


    public AddEmployeeExpertiseUseCase(EmployeeDAO employeeDAO, ServiceDAO serviceDAO) {
        this.employeeDAO = employeeDAO;
        this.serviceDAO = serviceDAO;
    }

    public boolean addExpertise(int employeeId, int serviceId){
        Employee employee = employeeDAO.findOne(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find an employee with id " + employeeId));

        Service service = serviceDAO.findOne(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service with id " + serviceId));

        if (employee.getExpertise().contains(service))
            throw new IllegalArgumentException("Employee already has this expertise");

        if (service.isInactive())
            throw new IllegalArgumentException("Can not add an inactive service to employee expertise");

        employee.addExpertise(service);
        return employeeDAO.update(employee);
    }
}
