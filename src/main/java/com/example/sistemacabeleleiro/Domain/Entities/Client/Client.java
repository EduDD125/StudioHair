package com.example.sistemacabeleleiro.Domain.Entities.Client;

import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Email.Email;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.EmployeeStatus;

public class Client {
    private Integer id;
    private String name;
    private CPF cpf;
    private String phone;
    private Email email;
    private ClientStatus status;

    public Client(){
        this.status = ClientStatus.ACTIVE;
    }

    public Client(String name, CPF cpf, String phone, Email email) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
        this.status = ClientStatus.ACTIVE;
    }

    public Client(String name, CPF cpf, String phone, Email email, ClientStatus status) {
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public Client(Integer id, String name, CPF cpf, String phone, Email email, ClientStatus status) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
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

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void activateStatus() {this.status = ClientStatus.ACTIVE;}
    public void inactivateStatus() {this.status = ClientStatus.INACTIVE;}
}
