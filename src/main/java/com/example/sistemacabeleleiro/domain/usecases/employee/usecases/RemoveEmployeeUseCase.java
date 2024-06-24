package com.example.sistemacabeleleiro.domain.usecases.employee.usecases;

import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.repository.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

import java.util.List;

public class RemoveEmployeeUseCase {
    private EmployeeDAO employeeDAO;
    private SchedulingDAO schedulingDAO;

    public RemoveEmployeeUseCase(EmployeeDAO employeeDAO, SchedulingDAO schedulingDAO) {
        this.employeeDAO = employeeDAO;
        this.schedulingDAO = schedulingDAO;
    }
    public boolean remove(int id){
        Employee employee = employeeDAO.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not find an employee with id " + id));

        validateEmployeeCanBeRemoved(employee);
        return employeeDAO.deleteByKey(id);
    }

    private void validateEmployeeCanBeRemoved(Employee employee) {
        if (!employeeSchedules(employee).isEmpty()) {
            throw new IllegalArgumentException("It's not possible to exclude employees who already have a schedule");
        }

        if (employee.isActive()) {
            throw new IllegalArgumentException("Can't delete an active employee");
        }
    }

    private List<Scheduling> employeeSchedules(Employee employee) {
        return schedulingDAO.findAll().stream()
                .filter(schedule -> schedule.getEmployee().equals(employee))
                .toList();
    }
}
