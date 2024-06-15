package com.example.sistemacabeleleiro.domain.entities.Schedulling;

import com.example.sistemacabeleleiro.domain.entities.Client.Client;
import com.example.sistemacabeleleiro.domain.entities.Employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.Service.Service;

import java.time.LocalDate;
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
        this.status = SchedulingStatus.SCHEDULED;
    }

    public Scheduling(Integer id, Client client, Employee employee,
                      LocalDateTime dataRealizacao, Service service) {
        this.id = id;
        this.client = client;
        this.employee = employee;
        this.dataRealizacao = dataRealizacao;
        this.status = SchedulingStatus.SCHEDULED;
        this.service = service;
    }

    public Scheduling(Integer id, Client client, Employee employee, LocalDateTime dataRealizacao, SchedulingStatus status, Service service) {
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

    public boolean isDateInsidePeriod(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        LocalDateTime dataRealizacao = getDataRealizacao();

        return (dataRealizacao.isEqual(startDateTime) || dataRealizacao.isAfter(startDateTime)) &&
                (dataRealizacao.isEqual(endDateTime) || dataRealizacao.isBefore(endDateTime));
    }
    public SchedulingStatus getStatus() {
        return status;
    }

    public void schedule() {
        this.status = SchedulingStatus.SCHEDULED;
    }

    public void cancel() {
        this.status = SchedulingStatus.CANCELED;
    }

    public void execute() {this.status = SchedulingStatus.PROVIDED;}

    public Service getService() {return service;}

    public void setService(Service service) {this.service = service;}

    @Override
    public String toString() {
        return "Scheduling{" +
                "id=" + id +
                ", client=" + client.getName() +
                ", employee=" + employee.getName() +
                ", employee expertises=" + employee.getExpertise() +
                ", dataRealizacao=" + dataRealizacao +
                ", status=" + status +
                ", service=" + service +
                '}';
    }
}


