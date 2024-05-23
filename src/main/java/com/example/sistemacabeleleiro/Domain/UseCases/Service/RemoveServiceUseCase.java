package com.example.sistemacabeleleiro.Domain.UseCases.Service;

import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.EmployeeDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;

public class RemoveServiceUseCase {

    private ServiceDAO serviceDAO;
    private EmployeeDAO employeeDAO;

    public RemoveServiceUseCase(ServiceDAO serviceDAO, EmployeeDAO employeeDAO) {
        this.serviceDAO = serviceDAO;
        this.employeeDAO = employeeDAO;
    }

    public void removeExpertiseFromAllEmployees(Integer serviceId) {
        employeeDAO.findAll().forEach(employee -> {
            employee.getExpertise().removeIf(service -> service.getId().equals(serviceId));
        });
    }

    public boolean remove(Integer id){
        if(id == null || serviceDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        removeExpertiseFromAllEmployees(id);

        return serviceDAO.deleteByKey(id);
    }

    public boolean remove (Service service){
        if(service == null || serviceDAO.findOne(service.getId()).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        removeExpertiseFromAllEmployees(service.getId());

        return serviceDAO.delete(service);
    }
}
