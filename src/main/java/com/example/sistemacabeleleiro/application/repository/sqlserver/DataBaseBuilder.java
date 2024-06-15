package com.example.sistemacabeleleiro.application.repository.sqlserver;

public class DataBaseBuilder {

    private void buildTables(){

    }

    private String createClientTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE CLIENT (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT (\n");
        builder.append("name TEXT NOT NULL (\n");
        builder.append("cpf TEXT NOT NULL UNIQUE (\n");
        builder.append("phone TEXT NOT NULL (\n");
        builder.append("email TEXT NOT NULL (\n");
        builder.append("status TEXT NOT NULL (\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createEmployeeTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE EMPLOYEE (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT (\n");
        builder.append("name TEXT NOT NULL (\n");
        builder.append("cpf TEXT NOT NULL UNIQUE (\n");
        builder.append("phone TEXT NOT NULL (\n");
        builder.append("email TEXT NOT NULL (\n");
        builder.append("dateOfBirth TEXT NOT NULL (\n");
        builder.append("status TEXT NOT NULL (\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createServiceTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE SERVICE (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT (\n");
        builder.append("name TEXT NOT NULL (\n");
        builder.append("description TEXT NOT NULL (\n");
        builder.append("price REAL NOT NULL (\n");
        builder.append("category TEXT (\n");
        builder.append("subCategory TEXT (\n");
        builder.append("discount REAL (\n");
        builder.append("status TEXT NOT NULL (\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createExpertiseTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE EXPERTISE (\n");
        builder.append("employee_id INTEGER (\n");
        builder.append("service_id INTEGER (\n");
        builder.append("FOREIGN KEY (employee_id) REFERENCES Employee(id) (\n");
        builder.append("FOREIGN KEY (service_id) REFERENCES Service(id) (\n");
        builder.append("PRIMARY KEY (employee_id, service_id)(\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createSchedulingTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE SCHEDULING (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT (\n");
        builder.append("idClient INTEGER (\n");
        builder.append("idEmployee INTEGER (\n");
        builder.append("realizationDate TEXT NOT NULL (\n");
        builder.append("status TEXT NOT NULL (\n");
        builder.append("idService INTEGER(\n");
        builder.append("FOREIGN KEY (idClient) REFERENCES Client(id) (\n");
        builder.append("FOREIGN KEY (idEmployee) REFERENCES Employee(id) (\n");
        builder.append("FOREIGN KEY (idService) REFERENCES Service(id) (\n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
}
