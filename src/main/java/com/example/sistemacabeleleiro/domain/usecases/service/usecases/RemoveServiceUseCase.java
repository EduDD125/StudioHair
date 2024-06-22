package com.example.sistemacabeleleiro.domain.usecases.service.usecases;

import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.repository.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
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

    public boolean remove(int id){
        if(serviceDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        if (hasSchedulingsForService(id)) {
            throw new IllegalStateException("Cannot delete service with existing schedules.");
        }

        if (isServiceInEmployeeExpertise(id)) {
            throw new IllegalStateException("Cannot delete service linked to employee expertise.");
        }

        return serviceDAO.deleteByKey(id);
    }

    public boolean hasSchedulingsForService(int serviceId) {
        return !schedulingDAO.findAllByServiceId(serviceId).isEmpty();
    }

    public boolean isServiceInEmployeeExpertise(int serviceId) {
        return employeeDAO.findAll().stream()
                .anyMatch(employee -> employee.getExpertise().stream()
                        .anyMatch(service -> service.getId().equals(serviceId)));
    }
}
