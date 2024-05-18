package com.example.sistemacabeleleiro.Domain.Entities.Employee;

import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;

import java.util.List;

public class Employee {
    private Integer id;
    private String name;
    private List<Service> expertise;
    private String phone;
    private String email;
    private String dateOfBirth;

    public Employee(String name, List<Service> expertise, String phone, String email, String dateOfBirth) {
        this.name = name;
        this.expertise = expertise;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee(int id, String name, List<Service> expertise, String phone, String email, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.expertise = expertise;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
