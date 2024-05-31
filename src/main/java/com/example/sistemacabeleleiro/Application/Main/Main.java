package com.example.sistemacabeleleiro.Application.Main;

import com.example.sistemacabeleleiro.Application.Repository.InMemoryClientDAO;
import com.example.sistemacabeleleiro.Application.Repository.InMemoryEmployeeDAO;
import com.example.sistemacabeleleiro.Application.Repository.InMemorySchedulingDAO;
import com.example.sistemacabeleleiro.Application.Repository.InMemoryServiceDAO;
import com.example.sistemacabeleleiro.Domain.Entities.CPF.CPF;
import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Email.Email;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Client.*;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.*;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.*;
import com.example.sistemacabeleleiro.Domain.UseCases.Service.*;

import java.time.LocalDateTime;

public class Main {

    private static CreateClientUseCase createClientUseCase;
    private static RemoveClientUseCase removeClientUseCase;
    private static ShowClientUseCase showClientUseCase;
    private static UpdateClientUseCase updateClientUseCase;
    private static ActivateClientUseCase activateClientUseCase;
    private static InactivateClientUseCase inactivateClientUseCase;

    private static ActivateEmployeeUseCase activateEmployeeUseCase;
    private static AddEmployeeExpertiseUseCase addEmployeeExpertiseUseCase;
    private static CreateEmployeeUseCase createEmployeeUseCase;
    private static FindEmployeeUseCase findEmployeeUseCase;
    private static InactivateEmployeeUseCase inactivateEmployeeUseCase;
    private static RemoveEmployeeUseCase removeEmployeeUseCase;
    private static RemoveExpertiseFromEmployeeUseCase removeExpertiseFromEmployeeUseCase;
    private static UpdateEmployeeUseCase updateEmployeeUseCase;

    private static CancelSchedulingUseCase cancelSchedulingUseCase;
    private static CreateSchedulingUseCase createSchedulingUseCase;
    private static FindSchedulingUseCase findSchedulingUseCase;
    private static UpdateScheduleUseCase updateScheduleUseCase;

    private static CreateServiceUseCase createServiceUseCase;
    private static FindServiceUseCase findServiceUseCase;
    private static RemoveServiceUseCase removeServiceUseCase;
    private static UpdateServiceUseCase updateServiceUseCase;
    private static ActivateServiceUseCase activateServiceUseCase;
    private static InactivateServiceUseCase inactivateServiceUseCase;

    public static void main(String[] args) {
        configureInjection();

        Service service1 = new Service("Corte básico", "Corte de cabelo masculino", 30.0);
        Service service2 = new Service("Barba", "Fazer a barba simples", 25.0);
        Service service3 = new Service("Corte e barba",
                "Corte de cabelo e barba feita masculino", 40.0);
        createServiceUseCase.insert(service1);
        createServiceUseCase.insert(service2);
        createServiceUseCase.insert(service3);

        Email client1Email = Email.of("lucas@gmail.com");
        CPF client1Cpf = CPF.of("111.222.333-44");
        Client client1 = new Client("Lucas",client1Cpf,"16998765432",client1Email);

        Email client2Email = Email.of("jorge@gmail.com");
        CPF client2Cpf = CPF.of("000.111.222-33");
        Client client2 = new Client("Jorge",client2Cpf,"16998765431",client2Email);

        Email client3Email = Email.of("carloslinux@gmail.com");
        CPF client3Cpf = CPF.of("123.456.789-10");
        Client client3 = new Client("Carlos",client3Cpf,"16998765430",client3Email);

        createClientUseCase.insert(client1);
        createClientUseCase.insert(client2);
        createClientUseCase.insert(client3);

        Email employee1Email = Email.of("eduardo@gmail.com");
        CPF employee1Cpf = CPF.of("999.888.777-66");
        Employee employee1 = new Employee
                ("Eduardo",employee1Cpf,"16912345678",employee1Email,"11/11/2001");

        Email employee2Email = Email.of("victor@gmail.com");
        CPF employee2Cpf = CPF.of("000.888.777-66");
        Employee employee2 = new Employee
                ("Victor",employee2Cpf,"16912345679",employee2Email,"20/10/1999");

        Email employee3Email = Email.of("ariadne@gmail.com");
        CPF employee3Cpf = CPF.of("111.888.777-66");
        Employee employee3 = new Employee
                ("Ariadne",employee3Cpf,"16912345670",employee3Email,"30/01/1970");

        createEmployeeUseCase.insert(employee1);
        createEmployeeUseCase.insert(employee2);
        createEmployeeUseCase.insert(employee3);

        addEmployeeExpertiseUseCase.addExpertise(employee1, service1);
        addEmployeeExpertiseUseCase.addExpertise(employee2,service2);
        addEmployeeExpertiseUseCase.addExpertise(employee3,service3);

        Scheduling scheduling1 = new Scheduling(client1,employee1,
                LocalDateTime.of(2024,6,1,19,0),service1);
        Scheduling scheduling2 = new Scheduling(client2,employee2,
                LocalDateTime.of(2024,6,1,20,0),service2);
        Scheduling scheduling3 = new Scheduling(client3,employee3,
                LocalDateTime.of(2024,6,1,21,0),service3);

        createSchedulingUseCase.insert(scheduling1);
        createSchedulingUseCase.insert(scheduling2);
        createSchedulingUseCase.insert(scheduling3);

    }

    public static void scheduleService(Client client, Employee employee,
                                       LocalDateTime localDateTime, Service service){
        try {
            Scheduling scheduling = new Scheduling(client,employee,localDateTime,service);
            createSchedulingUseCase.insert(scheduling);
            System.out.println("Schedule completed successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void cancelScheduleService(Scheduling scheduling){
        if (scheduling.getStatus().equals(SchedulingStatus.CANCELED)){
            return;
        }
        try {
            cancelSchedulingUseCase.cancel(scheduling);
            System.out.println("Cancellation of the scheduled service successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void configureInjection() {
        ClientDAO clientDAO = new InMemoryClientDAO();
        EmployeeDAO employeeDAO = new InMemoryEmployeeDAO();
        SchedulingDAO schedulingDAO = new InMemorySchedulingDAO();
        ServiceDAO serviceDAO = new InMemoryServiceDAO();

        createClientUseCase = new CreateClientUseCase(clientDAO);
        removeClientUseCase = new RemoveClientUseCase(clientDAO,schedulingDAO);
        showClientUseCase = new ShowClientUseCase(clientDAO);
        updateClientUseCase = new UpdateClientUseCase(clientDAO);
        inactivateClientUseCase = new InactivateClientUseCase(clientDAO);
        activateClientUseCase = new ActivateClientUseCase(clientDAO);

        activateEmployeeUseCase = new ActivateEmployeeUseCase(employeeDAO);
        updateEmployeeUseCase = new UpdateEmployeeUseCase(employeeDAO);
        addEmployeeExpertiseUseCase = new AddEmployeeExpertiseUseCase(employeeDAO,serviceDAO,updateEmployeeUseCase);
        createEmployeeUseCase = new CreateEmployeeUseCase(employeeDAO);
        findEmployeeUseCase = new FindEmployeeUseCase(employeeDAO);
        inactivateEmployeeUseCase = new InactivateEmployeeUseCase(employeeDAO);
        removeEmployeeUseCase = new RemoveEmployeeUseCase(employeeDAO,schedulingDAO);
        removeExpertiseFromEmployeeUseCase = new RemoveExpertiseFromEmployeeUseCase(employeeDAO,serviceDAO,updateEmployeeUseCase);

        cancelSchedulingUseCase = new CancelSchedulingUseCase(schedulingDAO);
        createSchedulingUseCase = new CreateSchedulingUseCase(schedulingDAO,clientDAO,employeeDAO,serviceDAO);
        findSchedulingUseCase = new FindSchedulingUseCase(schedulingDAO);
        updateScheduleUseCase = new UpdateScheduleUseCase(schedulingDAO);
        inactivateServiceUseCase = new InactivateServiceUseCase(serviceDAO);
        activateServiceUseCase = new ActivateServiceUseCase(serviceDAO);

        createServiceUseCase = new CreateServiceUseCase(serviceDAO);
        findServiceUseCase = new FindServiceUseCase(serviceDAO);
        removeServiceUseCase = new RemoveServiceUseCase(serviceDAO,employeeDAO,schedulingDAO);
        updateServiceUseCase = new UpdateServiceUseCase(serviceDAO);

    }
}
