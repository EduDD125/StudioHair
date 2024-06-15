package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.service.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class RemoveExpertiseFromEmployeeUseCase {
    private EmployeeDAO employeeDAO;
    private ServiceDAO serviceDAO;
    private UpdateEmployeeUseCase updateEmployeeUseCase;

    public RemoveExpertiseFromEmployeeUseCase(EmployeeDAO employeeDAO, ServiceDAO serviceDAO,
                                       UpdateEmployeeUseCase updateEmployeeUseCase) {
        this.employeeDAO = employeeDAO;
        this.serviceDAO = serviceDAO;
        this.updateEmployeeUseCase = updateEmployeeUseCase;
    }

    public boolean removeExpertise(Integer employeeId, Integer serviceId){
        if (employeeId == null || serviceId == null)
            throw new IllegalArgumentException("Employee ID and/or Service ID are/is null");

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
