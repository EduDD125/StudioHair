package com.example.sistemacabeleleiro.domain.usecases.service;


import com.example.sistemacabeleleiro.domain.entities.Service.Service;
import com.example.sistemacabeleleiro.domain.usecases.employee.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

public class RemoveServiceUseCase {

    private ServiceDAO serviceDAO;
    private EmployeeDAO employeeDAO;
    private SchedulingDAO schedulingDAO;


    public RemoveServiceUseCase(ServiceDAO serviceDAO, EmployeeDAO employeeDAO, SchedulingDAO schedulingDAO) {
        this.serviceDAO = serviceDAO;
        this.employeeDAO = employeeDAO;
        this.schedulingDAO = schedulingDAO;
    }

    public boolean hasSchedulingsForService(Integer serviceId) {
        return !schedulingDAO.findAllByServiceId(serviceId).isEmpty();
    }

    public boolean isServiceInEmployeeExpertise(Integer serviceId) {
        return employeeDAO.findAll().stream()
                .anyMatch(employee -> employee.getExpertise().stream()
                        .anyMatch(service -> service.getId().equals(serviceId)));
    }
    public boolean remove(Integer id){
        if(id == null || serviceDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        if (hasSchedulingsForService(id)) {
            throw new IllegalStateException("Cannot delete service with existing schedulings.");
        }

        if (isServiceInEmployeeExpertise(id)) {
            throw new IllegalStateException("Cannot delete service linked to employee expertise.");
        }

        return serviceDAO.deleteByKey(id);
    }

    public boolean remove (Service service){
        if(service == null || serviceDAO.findOne(service.getId()).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        if (hasSchedulingsForService(service.getId()))
            throw new IllegalStateException("Cannot delete service with existing schedulings.");

        if (isServiceInEmployeeExpertise(service.getId()))
            throw new IllegalStateException("Cannot delete service linked to employee expertise.");

        return serviceDAO.delete(service);
    }
}
