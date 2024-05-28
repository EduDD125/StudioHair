package com.example.sistemacabeleleiro.Domain.Entities.Schedulling;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;

import java.time.LocalDateTime;

public class Scheduling {
    private Integer id;
    private Client client;
    private Employee employee;
    private LocalDateTime dataRealizacao;
    private SchedulingStatus status;
    private Service service;

    public Scheduling(){this.status = SchedulingStatus.SCHEDULED;}

    public Scheduling(Client client, Employee employee, LocalDateTime dataRealizacao, Service service) {
        this.client = client;
        this.employee = employee;
        this.dataRealizacao = dataRealizacao;
        this.service = service;
    }

    public Scheduling(Integer id, Client client, Employee employee,
                      LocalDateTime dataRealizacao, Service service) {
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.dataRealizacao = dataRealizacao;
        this.status = status;
        this.service = service;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDateTime dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }
    public SchedulingStatus getStatus() {
        return status;
    }

    public void schedule(SchedulingStatus status) {
        this.status = SchedulingStatus.SCHEDULED;
    }
    public void cancel(SchedulingStatus status) {
        this.status = SchedulingStatus.CANCELED;
    }
    public void finish(SchedulingStatus status) {
        this.status = SchedulingStatus.SCHEDULED;
    }
}


