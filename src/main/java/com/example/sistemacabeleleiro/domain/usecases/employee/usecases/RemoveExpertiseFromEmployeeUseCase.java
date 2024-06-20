package com.example.sistemacabeleleiro.domain.usecases.employee.usecases;

import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class RemoveExpertiseFromEmployeeUseCase {
    private EmployeeDAO employeeDAO;
    private ServiceDAO serviceDAO;


    public RemoveExpertiseFromEmployeeUseCase(EmployeeDAO employeeDAO, ServiceDAO serviceDAO) {
        this.employeeDAO = employeeDAO;
        this.serviceDAO = serviceDAO;
    }

    public boolean removeExpertise(int employeeId, int serviceId){
        Employee employee = employeeDAO.findOne(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find an employee with id " + employeeId));

        Service service = serviceDAO.findOne(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service with id " + serviceId));

        if (!employee.getExpertise().contains(service))
            throw new IllegalArgumentException("Employee does not have this specialty");

        employee.removeExpertise(service);
        return employeeDAO.update(employee);
    }
}
