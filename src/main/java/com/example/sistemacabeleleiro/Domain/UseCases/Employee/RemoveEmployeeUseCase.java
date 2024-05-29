package com.example.sistemacabeleleiro.Domain.UseCases.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.EmployeeStatus;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.SchedulingDAO;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;

import java.util.List;

public class RemoveEmployeeUseCase {
    private EmployeeDAO employeeDAO;
    private SchedulingDAO schedulingDAO;

    public RemoveEmployeeUseCase(EmployeeDAO employeeDAO, SchedulingDAO schedulingDAO) {
        this.employeeDAO = employeeDAO;
        this.schedulingDAO = schedulingDAO;
    }
    public boolean remove(Integer id){
        var employee = employeeDAO.findOne(id);
        if (id == null || employee.isEmpty()){
            throw new EntityNotFoundException("Employee not found");
        }

        validateEmployeeStatus(employee.get());
        deleteEmployeeSchedules(employee.get());
        return employeeDAO.deleteByKey(id);
    }
    public boolean remove(Employee employee){
        if (employee == null || employeeDAO.findOne(employee.getId()).isEmpty()){
            throw new EntityNotFoundException("Employee not found");
        }

        validateEmployeeStatus(employee);
        deleteEmployeeSchedules(employee);
        return employeeDAO.delete(employee);
    }

    private void validateEmployeeStatus(Employee employee) {
        if (employee.getStatus() != EmployeeStatus.INACTIVE) {
            throw new IllegalArgumentException("Can't delete an active employee");
        }
    }

    private void deleteEmployeeSchedules(Employee employee) {
        List<Scheduling> schedulesToDelete = schedulingDAO.findAll().stream()
                .filter(schedule -> schedule.getEmployee().equals(employee))
                .toList();
        if (!schedulesToDelete.isEmpty()){
            for (Scheduling schedule : schedulesToDelete) {
                schedulingDAO.delete(schedule);
            }
        }
    }
}
