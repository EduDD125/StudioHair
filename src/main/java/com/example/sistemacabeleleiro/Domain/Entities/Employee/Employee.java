package com.example.sistemacabeleleiro.Domain.Entities.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Email.Email;
import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;

import java.util.List;

public class Employee {
    private Integer id;
    private String name;
    private CPF cpf;
    private List<Service> expertise;
    private String phone;
    private Email email;
    private String dateOfBirth;
    private EmployeeStatus status;

    public Employee(){
        this.status = EmployeeStatus.ACTIVE;
    }

    public Employee(String name, CPF cpf, List<Service> expertise, String phone,
                    Email email, String dateOfBirth, EmployeeStatus status) {
        this.name = name;
        this.cpf = cpf;
        this.expertise = expertise;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }

    public Employee(Integer id, String name, CPF cpf, List<Service> expertise, String phone,
                    Email email, String dateOfBirth, EmployeeStatus status) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.expertise = expertise;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }

    public void addExpertise(Service service){
        this.expertise.add(service);
    }
    public void removeExpertise(Service service){
        this.expertise.remove(service);
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Service> getExpertise() {
        return expertise;
    }

    public void setExpertise(List<Service> expertise) {
        this.expertise = expertise;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public EmployeeStatus getStatus() {
        return status;
    }
    public void activateStatus() {this.status = EmployeeStatus.ACTIVE;}
    public void inactivateStatus() {this.status = EmployeeStatus.INACTIVE;}
}
