package com.example.sistemacabeleleiro.Domain.UseCases.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.EmployeeStatus;
import com.example.sistemacabeleleiro.Domain.UseCases.Utils.EntityNotFoundException;

public class RemoveEmployeeUseCase {
    private EmployeeDAO employeeDAO;

    public RemoveEmployeeUseCase(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
    public boolean remove(Integer id){
        if (id == null || employeeDAO.findOne(id).isEmpty()){
            throw new EntityNotFoundException("Employee not found");
        }
        if (employeeDAO.findOne(id).get().getStatus() != EmployeeStatus.INACTIVE){
            throw new IllegalArgumentException("Can't delete an active employee");
        }
        return employeeDAO.deleteByKey(id);
    }
    public boolean remove(Employee employee){
        if (employee == null || employeeDAO.findOne(employee.getId()).isEmpty()){
            throw new EntityNotFoundException("Employee not found");
        }
        if (employeeDAO.findOne(employee.getId()).get().getStatus() != EmployeeStatus.INACTIVE){
            throw new IllegalArgumentException("Can't delete an active employee");
        }
        return employeeDAO.delete(employee);
    }
}
