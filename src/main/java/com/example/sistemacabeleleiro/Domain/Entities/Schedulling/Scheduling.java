package com.example.sistemacabeleleiro.Domain.Entities.Schedulling;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;

import java.time.LocalDateTime;

public class Scheduling {
    private Integer id;
    private Client client;
    private Employee employee;
    private LocalDateTime dataRealizacao;


    public Scheduling(Client client, Employee employee, LocalDateTime dataRealizacao) {
        this.client = client;
        this.employee = employee;
        this.dataRealizacao = dataRealizacao;
    }

    public Scheduling(Integer id, Client client, Employee employee, LocalDateTime dataRealizacao) {
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.dataRealizacao = dataRealizacao;
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
}
