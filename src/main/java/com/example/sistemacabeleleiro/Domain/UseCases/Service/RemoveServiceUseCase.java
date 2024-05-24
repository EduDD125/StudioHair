package com.example.sistemacabeleleiro.Domain.UseCases.Service;

import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.EmployeeDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.CancelSchedulingUseCase;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.SchedulingDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;

import java.util.List;

public class RemoveServiceUseCase {

    private ServiceDAO serviceDAO;
    private EmployeeDAO employeeDAO;
    private SchedulingDAO schedulingDAO;
    private CancelSchedulingUseCase cancelSchedulingUseCase;


    public RemoveServiceUseCase(ServiceDAO serviceDAO, EmployeeDAO employeeDAO, SchedulingDAO schedulingDAO) {
        this.serviceDAO = serviceDAO;
        this.employeeDAO = employeeDAO;
        this.schedulingDAO = schedulingDAO;
        this.cancelSchedulingUseCase = new CancelSchedulingUseCase(schedulingDAO);
    }

    public void removeExpertiseFromAllEmployees(Integer serviceId) {
        employeeDAO.findAll().forEach(employee -> {
            employee.getExpertise().removeIf(service -> service.getId().equals(serviceId));
        });
    }

    public void cancelAllSchedulingsForService(Integer serviceId) {
        List<Scheduling> schedulings = schedulingDAO.findAllByServiceId(serviceId);
        schedulings.forEach(scheduling -> cancelSchedulingUseCase.cancel(scheduling));
    }

    public boolean remove(Integer id){
        if(id == null || serviceDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        removeExpertiseFromAllEmployees(id);
        cancelAllSchedulingsForService(id);

        return serviceDAO.deleteByKey(id);
    }

    public boolean remove (Service service){
        if(service == null || serviceDAO.findOne(service.getId()).isEmpty())
            throw new EntityNotFoundException("Service not found.");

        removeExpertiseFromAllEmployees(service.getId());
        cancelAllSchedulingsForService(service.getId());

        return serviceDAO.delete(service);
    }
}
