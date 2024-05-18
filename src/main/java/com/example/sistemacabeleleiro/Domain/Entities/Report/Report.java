package com.example.sistemacabeleleiro.Domain.Entities.Report;

import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import java.time.LocalDateTime;

public class Report {
    private Integer id;
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;
    private Employee employee;
    private Scheduling scheduling;

    public Report(LocalDateTime initialDate, LocalDateTime finalDate, Employee employee, Scheduling scheduling) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.employee = employee;
        this.scheduling = scheduling;
    }

    public Report(Integer id, LocalDateTime initialDate, LocalDateTime finalDate, Employee employee, Scheduling scheduling) {
        this.id = id;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.employee = employee;
        this.scheduling = scheduling;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }
}
