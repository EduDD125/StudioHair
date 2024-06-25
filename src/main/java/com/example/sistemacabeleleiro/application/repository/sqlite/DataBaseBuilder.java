package com.example.sistemacabeleleiro.application.repository.sqlite;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseBuilder {

    public void buildDataBaseIfMissing(){
        if(isDatabaseMissing()){
            System.out.println("Database is missing. Building database: \n");
            buildTables();
        }
    }

    private boolean isDatabaseMissing(){
        return !Files.exists(Paths.get("database.db"));
    }

    private void buildTables(){
        try(Statement statement = ConnectionFactory.createStatement()) {
            statement.addBatch(createClientTable());
            statement.addBatch(createEmployeeTable());
            statement.addBatch(createServiceTable());
            statement.addBatch(createExpertiseTable());
            statement.addBatch(createSchedulingTable());

            System.out.println("Database successfully created.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    private String createClientTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE CLIENT (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("name TEXT NOT NULL, \n");
        builder.append("cpf TEXT NOT NULL UNIQUE, \n");
        builder.append("phone TEXT NOT NULL, \n");
        builder.append("email TEXT NOT NULL, \n");
        builder.append("status TEXT NOT NULL \n");
        builder.append("); \n");

        /*CREATE TABLE "Client" (
	        "id"	INTEGER NOT NULL,
	        "name"	TEXT NOT NULL,
	        "cpf"	TEXT NOT NULL UNIQUE,
	        "phone"	TEXT NOT NULL,
	        "email"	TEXT NOT NULL,
	        "status"	TEXT NOT NULL,
	        PRIMARY KEY("id" AUTOINCREMENT)
        );*/

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createEmployeeTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE EMPLOYEE (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("name TEXT NOT NULL, \n");
        builder.append("cpf TEXT NOT NULL UNIQUE, \n");
        builder.append("phone TEXT NOT NULL, \n");
        builder.append("email TEXT NOT NULL, \n");
        builder.append("dateOfBirth TEXT NOT NULL, \n");
        builder.append("status TEXT NOT NULL \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createServiceTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE SERVICE (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("name TEXT NOT NULL, \n");
        builder.append("description TEXT NOT NULL, \n");
        builder.append("price REAL NOT NULL, \n");
        builder.append("category TEXT, \n");
        builder.append("subCategory TEXT, \n");
        builder.append("discount REAL, \n");
        builder.append("status TEXT NOT NULL \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createExpertiseTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE EXPERTISE (\n");
        builder.append("employee_id INTEGER, \n");
        builder.append("service_id INTEGER, \n");
        builder.append("FOREIGN KEY (employee_id) REFERENCES Employee(id), \n");
        builder.append("FOREIGN KEY (service_id) REFERENCES Service(id), \n");
        builder.append("PRIMARY KEY (employee_id, service_id) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }

    private String createSchedulingTable(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE SCHEDULING (\n");
        builder.append("id INTEGER PRIMARY KEY AUTOINCREMENT, \n");
        builder.append("idClient INTEGER, \n");
        builder.append("idEmployee INTEGER, \n");
        builder.append("realizationDate TEXT NOT NULL, \n");
        builder.append("status TEXT NOT NULL, \n");
        builder.append("idService INTEGER, \n");
        builder.append("FOREIGN KEY (idClient) REFERENCES Client(id), \n");
        builder.append("FOREIGN KEY (idEmployee) REFERENCES Employee(id), \n");
        builder.append("FOREIGN KEY (idService) REFERENCES Service(id) \n");
        builder.append("); \n");

        System.out.println(builder.toString());
        return builder.toString();
    }
}
