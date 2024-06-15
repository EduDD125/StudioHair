package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.domain.entities.Employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.Service.Service;
import com.example.sistemacabeleleiro.domain.entities.Service.ServiceStatus;
import com.example.sistemacabeleleiro.domain.usecases.service.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

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
