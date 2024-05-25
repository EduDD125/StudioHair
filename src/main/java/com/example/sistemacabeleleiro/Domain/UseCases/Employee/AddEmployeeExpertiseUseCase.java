package com.example.sistemacabeleleiro.Domain.UseCases.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Service.ServiceDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;

public class AddEmployeeExpertiseUseCase {
    private EmployeeDAO employeeDAO;
    private ServiceDAO serviceDAO;
    private UpdateEmployeeUseCase updateEmployeeUseCase;

    public AddEmployeeExpertiseUseCase(EmployeeDAO employeeDAO, ServiceDAO serviceDAO,
                                       UpdateEmployeeUseCase updateEmployeeUseCase) {
        this.employeeDAO = employeeDAO;
        this.serviceDAO = serviceDAO;
        this.updateEmployeeUseCase = updateEmployeeUseCase;
    }

    public boolean addExpertise(Integer employeeId, Integer serviceId){
        if (employeeId == null || serviceId == null){
            throw new IllegalArgumentException("Employee ID and/or Service ID are/is null");
        }
        Employee employee = employeeDAO.findOne(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find an employee with id " + employeeId));

        Service service = serviceDAO.findOne(serviceId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service with id " + serviceId));

        if (employee.getExpertise().contains(service)){
            throw new IllegalArgumentException("Employee already has this expertise");
        }
        employee.addExpertise(service);
        return updateEmployeeUseCase.update(employee);
    }
}
