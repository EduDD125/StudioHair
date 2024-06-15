package com.example.sistemacabeleleiro.domain.usecases.employee;

import com.example.sistemacabeleleiro.domain.entities.Employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.Employee.EmployeeStatus;
import com.example.sistemacabeleleiro.domain.entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

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
        if (!employeeSchedules(employee.get()).isEmpty()){
            throw new IllegalArgumentException("It's not possible to exclude employees who already have a schedule");
        }
        validateEmployeeStatus(employee.get());

        return employeeDAO.deleteByKey(id);
    }
    public boolean remove(Employee employee){
        if (employee == null || employeeDAO.findOne(employee.getId()).isEmpty()){
            throw new EntityNotFoundException("Employee not found");
        }
        if (!employeeSchedules(employee).isEmpty()){
            throw new IllegalArgumentException("It's not possible to exclude employees who already have a schedule");
        }

        validateEmployeeStatus(employee);

        return employeeDAO.delete(employee);
    }

    private void validateEmployeeStatus(Employee employee) {
        if (employee.getStatus() != EmployeeStatus.INACTIVE) {
            throw new IllegalArgumentException("Can't delete an active employee");
        }
    }

    private List<Scheduling> employeeSchedules(Employee employee) {
        List<Scheduling> schedules = schedulingDAO.findAll().stream()
                .filter(schedule -> schedule.getEmployee().equals(employee))
                .toList();

        return schedules;
    }
}
