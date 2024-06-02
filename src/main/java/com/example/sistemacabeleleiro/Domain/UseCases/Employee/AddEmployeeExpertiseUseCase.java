package com.example.sistemacabeleleiro.Domain.UseCases.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.Entities.Service.ServiceStatus;
import com.example.sistemacabeleleiro.Domain.UseCases.Service.ServiceDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;

public class AddEmployeeExpertiseUseCase {
    private EmployeeDAO employeeDAO;
    private ServiceDAO serviceDAO;


    public AddEmployeeExpertiseUseCase(EmployeeDAO employeeDAO, ServiceDAO serviceDAO) {
        this.employeeDAO = employeeDAO;
        this.serviceDAO = serviceDAO;
    }

    public boolean addExpertise(Employee employee, Service service){
        if (employee == null || service == null){
            throw new IllegalArgumentException("Employee and/or Service are/is null");
        }
        if (employeeDAO.findOne(employee.getId()).isEmpty()){
            throw new EntityNotFoundException("Can not find an employee");
        }
        if (serviceDAO.findOne(service.getId()).isEmpty()){
            throw new EntityNotFoundException("Can not find a service");
        }

        if (employee.getExpertise().contains(service))
            throw new IllegalArgumentException("Employee already has this expertise");

        if (service.getStatus().equals(ServiceStatus.INACTIVE))
            throw new IllegalArgumentException("Cannot add an inactive service to employee expertise");

        employee.addExpertise(service);
        return employeeDAO.update(employee);
    }
}
