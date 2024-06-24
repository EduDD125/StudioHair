package com.example.sistemacabeleleiro.application.main;

import com.example.sistemacabeleleiro.application.repository.sqlite.SqliteClientDAO;
import com.example.sistemacabeleleiro.application.repository.sqlite.SqliteEmployeeDAO;
import com.example.sistemacabeleleiro.application.repository.sqlite.SqliteServiceDAO;
import com.example.sistemacabeleleiro.application.repository.sqlite.SqliteSchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.client.repository.ClientDAO;
import com.example.sistemacabeleleiro.domain.usecases.client.usecases.*;
import com.example.sistemacabeleleiro.domain.usecases.employee.repository.EmployeeDAO;
import com.example.sistemacabeleleiro.domain.usecases.employee.usecases.*;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.repository.SchedulingDAO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.usecases.*;
import com.example.sistemacabeleleiro.domain.usecases.service.repository.ServiceDAO;
import com.example.sistemacabeleleiro.domain.usecases.service.usecases.*;
import com.example.sistemacabeleleiro.domain.usecases.reports.ExportReportUseCase;
import com.example.sistemacabeleleiro.domain.usecases.reports.GenerateReportUseCase;
import com.example.sistemacabeleleiro.application.repository.sqlite.DataBaseBuilder;

public class Main {

    public static CreateClientUseCase createClientUseCase;
    public static RemoveClientUseCase removeClientUseCase;
    public static FindClientUseCase findClientUseCase;
    public static UpdateClientUseCase updateClientUseCase;
    public static ActivateClientUseCase activateClientUseCase;
    public static InactivateClientUseCase inactivateClientUseCase;

    public static ActivateEmployeeUseCase activateEmployeeUseCase;
    public static AddEmployeeExpertiseUseCase addEmployeeExpertiseUseCase;
    public static CreateEmployeeUseCase createEmployeeUseCase;
    public static FindEmployeeUseCase findEmployeeUseCase;
    public static InactivateEmployeeUseCase inactivateEmployeeUseCase;
    public static RemoveEmployeeUseCase removeEmployeeUseCase;
    public static RemoveExpertiseFromEmployeeUseCase removeExpertiseFromEmployeeUseCase;
    public static UpdateEmployeeUseCase updateEmployeeUseCase;

    public static CancelSchedulingUseCase cancelSchedulingUseCase;
    public static CreateSchedulingUseCase createSchedulingUseCase;
    public static FindSchedulingUseCase findSchedulingUseCase;
    public static UpdateScheduleUseCase updateScheduleUseCase;

    public static CreateServiceUseCase createServiceUseCase;
    public static FindServiceUseCase findServiceUseCase;
    public static RemoveServiceUseCase removeServiceUseCase;
    public static UpdateServiceUseCase updateServiceUseCase;
    public static ActivateServiceUseCase activateServiceUseCase;
    public static InactivateServiceUseCase inactivateServiceUseCase;

    public static GenerateReportUseCase generateReportUseCase;
    public static ExportReportUseCase exportReportUseCase;

    public static void main(String[] args) {
        configureInjection();
        setupDatabase();
    }

    private static void setupDatabase() {
        DataBaseBuilder dbBuilder = new DataBaseBuilder();
        dbBuilder.buildDataBaseIfMissing();
    }

    public static void configureInjection() {
        ClientDAO clientDAO = new SqliteClientDAO();
        EmployeeDAO employeeDAO = new SqliteEmployeeDAO();
        SchedulingDAO schedulingDAO = new SqliteSchedulingDAO();
        ServiceDAO serviceDAO = new SqliteServiceDAO();

        createClientUseCase = new CreateClientUseCase(clientDAO, employeeDAO);
        removeClientUseCase = new RemoveClientUseCase(clientDAO, schedulingDAO);
        findClientUseCase = new FindClientUseCase(clientDAO);
        updateClientUseCase = new UpdateClientUseCase(clientDAO);
        activateClientUseCase = new ActivateClientUseCase(clientDAO);
        inactivateClientUseCase = new InactivateClientUseCase(clientDAO);

        activateEmployeeUseCase = new ActivateEmployeeUseCase(employeeDAO);
        updateEmployeeUseCase = new UpdateEmployeeUseCase(employeeDAO);
        addEmployeeExpertiseUseCase = new AddEmployeeExpertiseUseCase(employeeDAO, serviceDAO);
        createEmployeeUseCase = new CreateEmployeeUseCase(employeeDAO, clientDAO);
        findEmployeeUseCase = new FindEmployeeUseCase(employeeDAO);
        inactivateEmployeeUseCase = new InactivateEmployeeUseCase(employeeDAO);
        removeEmployeeUseCase = new RemoveEmployeeUseCase(employeeDAO, schedulingDAO);
        removeExpertiseFromEmployeeUseCase = new RemoveExpertiseFromEmployeeUseCase(employeeDAO, serviceDAO);

        cancelSchedulingUseCase = new CancelSchedulingUseCase(schedulingDAO);
        createSchedulingUseCase = new CreateSchedulingUseCase(schedulingDAO, clientDAO, employeeDAO, serviceDAO);
        findSchedulingUseCase = new FindSchedulingUseCase(schedulingDAO);
        updateScheduleUseCase = new UpdateScheduleUseCase(schedulingDAO, clientDAO, employeeDAO, serviceDAO);

        inactivateServiceUseCase = new InactivateServiceUseCase(serviceDAO);
        activateServiceUseCase = new ActivateServiceUseCase(serviceDAO);
        createServiceUseCase = new CreateServiceUseCase(serviceDAO);
        findServiceUseCase = new FindServiceUseCase(serviceDAO);
        removeServiceUseCase = new RemoveServiceUseCase(serviceDAO, employeeDAO, schedulingDAO);
        updateServiceUseCase = new UpdateServiceUseCase(serviceDAO);

        generateReportUseCase = new GenerateReportUseCase(schedulingDAO, employeeDAO);
        exportReportUseCase = new ExportReportUseCase(generateReportUseCase);
    }
}
