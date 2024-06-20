package com.example.sistemacabeleleiro.application.main;

import com.example.sistemacabeleleiro.application.dtos.client.ClientInputDTO;
import com.example.sistemacabeleleiro.application.dtos.client.ClientOutputDTO;
import com.example.sistemacabeleleiro.application.dtos.employee.EmployeeInputDTO;
import com.example.sistemacabeleleiro.application.dtos.employee.EmployeeOutputDTO;
import com.example.sistemacabeleleiro.application.dtos.service.ServiceInputDTO;
import com.example.sistemacabeleleiro.application.dtos.service.ServiceOutputDTO;
import com.example.sistemacabeleleiro.application.repository.inmemory.InMemoryClientDAO;
import com.example.sistemacabeleleiro.application.repository.inmemory.InMemoryEmployeeDAO;
import com.example.sistemacabeleleiro.application.repository.inmemory.InMemorySchedulingDAO;
import com.example.sistemacabeleleiro.application.repository.inmemory.InMemoryServiceDAO;
import com.example.sistemacabeleleiro.application.repository.sqlite.DataBaseBuilder;
import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.client.*;
import com.example.sistemacabeleleiro.domain.usecases.employee.*;
import com.example.sistemacabeleleiro.domain.usecases.reports.ExportReportUseCase;
import com.example.sistemacabeleleiro.domain.usecases.reports.GenerateReportUseCase;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.*;
import com.example.sistemacabeleleiro.domain.usecases.service.*;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    private static CreateClientUseCase createClientUseCase;
    private static RemoveClientUseCase removeClientUseCase;
    private static FindClientUseCase findClientUseCase;
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

    private static GenerateReportUseCase generateReportUseCase;
    private static ExportReportUseCase exportReportUseCase;

    public static void main(String[] args) {
        configureInjection();
        setupDatabase();
        mockData();

        List<ClientOutputDTO> clients = findClientUseCase.findAll();
        for(ClientOutputDTO c:clients){
            System.out.println(c);
        }
        List<EmployeeOutputDTO> employees = findEmployeeUseCase.findAll();
        for(EmployeeOutputDTO e:employees){
            System.out.println(e);
        }
        List<ServiceOutputDTO> services = findServiceUseCase.findAll();
        for(ServiceOutputDTO s:services){
            System.out.println(s);
        }

        List<Scheduling> schedules = findSchedulingUseCase.findAll();
        for (Scheduling s:schedules){
            System.out.println(s);
        }

        System.out.println("-- CASOS DE TESTE --");

        // CASOS DE TESTE PARA RELATÓRIOS
        gerarPDF();
    }
    private static void setupDatabase() {
        DataBaseBuilder dbBuilder = new DataBaseBuilder();
        dbBuilder.buildDataBaseIfMissing();
    }

    private static void configureInjection() {
        ClientDAO clientDAO = new InMemoryClientDAO();
        EmployeeDAO employeeDAO = new InMemoryEmployeeDAO();
        SchedulingDAO schedulingDAO = new InMemorySchedulingDAO();
        ServiceDAO serviceDAO = new InMemoryServiceDAO();

        createClientUseCase = new CreateClientUseCase(clientDAO,employeeDAO);
        removeClientUseCase = new RemoveClientUseCase(clientDAO,schedulingDAO);
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




         //TESTS OF SYSTEM USE CASES

        //MOCK DE DADOS, CRIAÇÃO DE AGENDAMENTO NO "SUNNY DAY"
        {

        //gerarRelatoriosEmSunnyDay();

        // CASOS DE TESTE PARA FUNCIONÁRIO
        /*removerFuncionarioAtivo();
        removerFuncionarioComAgendamento();
        inativarFuncionarioJaInativo();
        ativarFuncionarioJaAtivo();
        findFuncionarioComCpfInexistente();
        findFuncionarioComIdNull();
        findFuncionarioComCpfInvalido();
        criarFuncionarioVazio();
        criarFuncionarioComCpfExistente();
        criarFuncionarioComEmailECpfInvalidos();
        atualizarFuncionario();

        CASOS DE TESTE PARA CLIENTE
        removerClienteAtivo();
        removerClienteComAgendamento();
        inativarClienteJaInativo();
        ativarClienteJaAtivo();
        findClienteComCpfInexistente();
        findClienteComNomeInexistente();
        findClienteComIdNull();
        findClienteComCpfInvalido();
        criarClienteVazio();
        criarClienteComCpfExistente();
        criarClienteComEmailECpfInvalidos();
        atualizarCliente();

        CASOS DE TESTE PARA SERVIÇOS
        criarServicoVazio();
        findServico();
        findTodosServicos();
        findServicosPorCategoria();
        findServicosComDesconto();
        findServicoPorFaixaDePreco();
        atualizarServico();
        removerServico();
        removerServicoComAgendamento();
        inativarServico();
        adicionarServicoInativoNasEspecialidades();

        // CASOS DE TESTE PARA AGENDAMENTO
        atualizarAgendamento();
        findAgendamento();
        findTodosAgendamentos();
        findAgendamentoPorCliente();
        findAgendamentoPorFuncionario();
        findAgendamentoPorServico();
        findAgendamentoPorDataEspecifica();
        findAgendamentoPorPeriodo();
        criarAgendamentoVazio();
        criarAgendamentoFuncionarioSemEspecialidade();
        criarAgendamentoClienteInativo();
        criarAgendamentoFuncionarioInativo();
        criarAgendamentoServicoInativo();
        criarAgendamentoPassado();
        cancelarAgendamentoJaCancelado();
        cancelarAgendamentoJaFeito();
        alterarAgendamentoClienteInativo();
        alterarAgendamentoFuncionarioInativo();
        alterarAgendamentoServicoInativo();
        alterarAgendamentoDataInvalida();*/
}

    private static void gerarPDF(){
        exportReportUseCase.exportSchedules("report_all");
        System.out.println("PDF gerado com sucesso!");
    }


    /*private static void alterarAgendamentoClienteInativo(){
        Scheduling scheduling = findSchedulingUseCase.findOne(1)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a schedule"));

        System.out.println(scheduling);

        Client client = findClientUseCase.findOne(2)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));

        updateScheduleUseCase.update(scheduling);
        System.out.println(scheduling);
    }
    private static void alterarAgendamentoFuncionarioInativo(){
        Scheduling scheduling = findSchedulingUseCase.findOne(1)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a schedule"));

        System.out.println(scheduling);

        Employee employee = findEmployeeUseCase.findOne(1)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));

        inactivateEmployeeUseCase.inactivate(employee);
        updateScheduleUseCase.update(scheduling);
        System.out.println(scheduling);
    }
    private static void alterarAgendamentoServicoInativo(){
        Scheduling scheduling = findSchedulingUseCase.findOne(1)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a schedule"));

        System.out.println(scheduling);

        Service service = findServiceUseCase.findOne(1)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));

        inactivateServiceUseCase.inactivate(service);
        updateScheduleUseCase.update(scheduling);
        System.out.println(scheduling);
    }

    private static void alterarAgendamentoDataInvalida(){
        Scheduling scheduling = findSchedulingUseCase.findOne(1)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a schedule"));

        System.out.println(scheduling);

        LocalDateTime dateTime = LocalDateTime.of(2023, 6, 1, 15, 30);

        updateScheduleUseCase.update(scheduling);
        System.out.println(scheduling);
    }

    private static void criarAgendamentoVazio(){
        Scheduling scheduling = new Scheduling();
        createSchedulingUseCase.insert(scheduling);
        System.out.println(scheduling);
    }

    private static void criarAgendamentoFuncionarioSemEspecialidade(){
        Client client = findClientUseCase.findOne(1)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));
        Employee employee = findEmployeeUseCase.findOne(2)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a employee"));
        Service service = findServiceUseCase.findOne(7)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service"));
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 1, 15, 30);
        Scheduling scheduling = new Scheduling(client, employee, dateTime, service);

        createSchedulingUseCase.insert(scheduling);
    }
    private static void criarAgendamentoClienteInativo(){
        Client client = findClientUseCase.findOne(6)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));
        Employee employee = findEmployeeUseCase.findOne(7)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a employee"));
        Service service = findServiceUseCase.findOne(6)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service"));
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 1, 15, 30);
        Scheduling scheduling = new Scheduling(100, client, employee, dateTime, service);

        createSchedulingUseCase.insert(scheduling);
    }
    private static void criarAgendamentoFuncionarioInativo(){
        Client client = findClientUseCase.findOne(7)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));
        Employee employee = findEmployeeUseCase.findOne(6)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a employee"));
        Service service = findServiceUseCase.findOne(4)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service"));
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 1, 15, 30);
        Scheduling scheduling = new Scheduling(100, client, employee, dateTime, service);

        createSchedulingUseCase.insert(scheduling);
    }
    private static void criarAgendamentoServicoInativo(){
        Client client = findClientUseCase.findOne(7)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));
        Employee employee = findEmployeeUseCase.findOne(7)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a employee"));
        Service service = findServiceUseCase.findOne(4)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service"));
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 1, 15, 30);
        service.inactivateStatus();
        Scheduling scheduling = new Scheduling(100, client, employee, dateTime, service);

        createSchedulingUseCase.insert(scheduling);
    }

    private static void criarAgendamentoPassado(){
        Client client = findClientUseCase.findOne(7)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a client"));
        Employee employee = findEmployeeUseCase.findOne(6)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a employee"));
        Service service = findServiceUseCase.findOne(3)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a service"));
        LocalDateTime dateTime = LocalDateTime.of(2023, 6, 1, 15, 30);
        Scheduling scheduling = new Scheduling(100, client, employee, dateTime, service);

        createSchedulingUseCase.insert(scheduling);
    }

    private static void cancelarAgendamentoJaCancelado(){
        Scheduling scheduling = findSchedulingUseCase.findOne(1)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a scheduling"));
        cancelSchedulingUseCase.cancel(scheduling);
        System.out.println(scheduling);
        cancelSchedulingUseCase.cancel(scheduling);
    }
    private static void cancelarAgendamentoJaFeito(){
        Scheduling scheduling = findSchedulingUseCase.findOne(1)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a scheduling"));
        scheduling.execute();
        System.out.println(scheduling);
        cancelSchedulingUseCase.cancel(scheduling);
    }

    private static void findAgendamentoPorPeriodo() {
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 30);
        List<Scheduling> schedulings = findSchedulingUseCase.findByTimePeriod(startDate, endDate);
        System.out.println(schedulings);
    }
    private static void findAgendamentoPorDataEspecifica() {
        LocalDateTime date = LocalDateTime.of(2025, 6, 10, 20, 0);
        Optional<Scheduling> schedulings = findSchedulingUseCase.findByScheduledDate(date);
        System.out.println(schedulings);
    }
    private static void findAgendamentoPorServico() {
        List<Scheduling> schedulings = findSchedulingUseCase.findByService(4);
        System.out.println(schedulings);
    }
    private static void findAgendamentoPorFuncionario() {
        List<Scheduling> schedulings = findSchedulingUseCase.findByEmployee(2);
        System.out.println(schedulings);
    }
    private static void findAgendamentoPorCliente() {
        List<Scheduling> schedulings = findSchedulingUseCase.findByClient(1);
        System.out.println(schedulings);
    }

    private static void findTodosAgendamentos() {
        List<Scheduling> schedulings = findSchedulingUseCase.findAll();
        for (Scheduling scheduling : schedulings)
            System.out.println(scheduling);
    }

    private static void findAgendamento() {
        Optional<Scheduling> scheduling = findSchedulingUseCase.findOne(1);
        System.out.println(scheduling);
    }
    private static void adicionarServicoInativoNasEspecialidades() {
        Optional<Employee> employeeOpt = findEmployeeUseCase.findOne(1);
        Optional<Service> serviceOpt = findServiceUseCase.findOne(7);
        Employee employee = employeeOpt.get();
        Service service = serviceOpt.get();
        addEmployeeExpertiseUseCase.addExpertise(employee, service);
        System.out.println(employeeOpt);
}
    private static void inativarServico() {
        Optional<Service> serviceOpt = findServiceUseCase.findOne(7);
        Service service = serviceOpt.get();
        inactivateServiceUseCase.inactivate(service);
        System.out.println("Service inactivated: " + service);
}
    private static void atualizarFuncionario() {
        Optional<Employee> employeeOpt = findEmployeeUseCase.findOne(1);
        Employee employee = employeeOpt.get();
        employee.setName("José Silva");
        employee.setPhone("(11) 98765-4321");
        employee.setDateOfBirth("1990-01-01");
        employee.activateStatus();
        updateEmployeeUseCase.update(employee);
        System.out.println(employee);
}
    private static void atualizarCliente() {
        Optional<Client> clientOpt = findClientUseCase.findOne(4);
        Client client = clientOpt.get();
        client.setName("Maria Silva");
        client.setPhone("(11) 98765-4321");
        client.activateStatus();
        updateClientUseCase.update(client);
        System.out.println(client);
}
    private static void removerServicoComAgendamento() {
        removeServiceUseCase.remove(1);
    }

    private static void removerServico() {
        boolean result = removeServiceUseCase.remove(6);
    }

    private static void atualizarAgendamento() {
        Optional<Scheduling> schedulingOpt = findSchedulingUseCase.findOne(1);
        Scheduling scheduling = schedulingOpt.get();

        Optional<Client> client = findClientUseCase.findOne(2);
        Optional<Employee> employee = findEmployeeUseCase.findOne(3);
        Optional<Service> service = findServiceUseCase.findOne(4);

        scheduling.setClient(client.get());
        scheduling.setEmployee(employee.get());
        scheduling.setDataRealizacao(LocalDateTime.of(2025, 6, 30, 18, 0));  // Atualiza a data de realização
        scheduling.setService(service.get());
        updateScheduleUseCase.update(scheduling);
        System.out.println(scheduling);
    } */

    /*private static void atualizarServico() {
        Optional<Service> serviceOpt = findServiceUseCase.findOne(1);
        Service service = serviceOpt.get();

        service.setName("Corte Avançado");
        service.setDescription("Corte de cabelo avançado feminino");
        service.setPrice(50.0);
        service.setCategory("Feminino");
        service.setSubCategory("Corte avançado");
        service.setDiscount(0.15);
        updateServiceUseCase.update(service);
        System.out.println(service);
    }

    private static void findTodosServicos() {
        List<Service> services = findServiceUseCase.findAll();
        System.out.println(services);
    }
    private static void findServicosComDesconto() {
        List<Service> services = findServiceUseCase.findByDiscount(0.1);
        System.out.println(services);
    }
    private static void findServicosPorCategoria() {
        List<Service> services = findServiceUseCase.findByCategory("Feminino");
        System.out.println(services);
    }
    private static void findServicoPorFaixaDePreco(){
        List<Service> services = findServiceUseCase.findByPriceRange(30.0, 70.0);
        System.out.println(services);
    }

    private static void findServico() {
        Optional<Service> service = findServiceUseCase.findOne(1);
        System.out.println(service);
    }

    private static void criarServicoVazio(){
        Service service = new Service();
        createServiceUseCase.insert(service);
        System.out.println(service);
    }
    private static void removerFuncionarioAtivo(){
        removeEmployeeUseCase.remove(7);
    }

    private static void removerClienteAtivo(){
        removeClientUseCase.remove(7);
    }

    private static void removerFuncionarioComAgendamento(){
        removeEmployeeUseCase.remove(1);
    }
    private static void removerClienteComAgendamento(){
        removeClientUseCase.remove(1);
    }

    private static void findClienteComCpfInexistente(){
        Optional<Client> client = findClientUseCase.findOneByCPF(CPF.of("111.111.111-11"));
        if (client.isEmpty()){
            throw new EntityNotFoundException("Client not found");
        }
        System.out.println(client);
    }

    private static void findFuncionarioComCpfInexistente(){
        Optional<Employee> employee = findEmployeeUseCase.findOneByCpf(CPF.of("111.111.111-11"));
        if (employee.isEmpty()){
            throw new EntityNotFoundException("Employee not found");
        }
        System.out.println(employee);
    }

    private static void findClienteComNomeInexistente(){
        Optional<Client> client = findClientUseCase.findOneByName("Fulano");
        if (client.isEmpty()){
            throw new EntityNotFoundException("Client not found");
        }
        System.out.println(client);
    }

    private static void inativarClienteJaInativo(){
        Client client = findClientUseCase.findOne(6).get();
        inactivateClientUseCase.inactivate(client);
    }

    private static void inativarFuncionarioJaInativo(){
        inactivateEmployeeUseCase.inactivate(6);
    }

    private static void ativarClienteJaAtivo(){
        Client client = findClientUseCase.findOne(1).get();
        activateClientUseCase.activate(client);
    }

    private static void ativarFuncionarioJaAtivo(){
        activateEmployeeUseCase.activate(1);
    }



    private static void gerarRelatoriosEmSunnyDay(){
        System.out.println("////////////");
        System.out.println("Caso 1: Nenhum filtro");
        System.out.println(generateReportUseCase.findSchedulesByFilters());

        System.out.println("////////////");
        System.out.println("Caso 2: Filtrar por funcionário");
        System.out.println(generateReportUseCase.findSchedulesByFilters(3));

        System.out.println("////////////");
        System.out.println("Caso 3: Filtrar por data inicial");
        System.out.println(generateReportUseCase.findSchedulesByFilters
                (LocalDate.of(2025,06,15)));

        System.out.println("////////////");
        System.out.println("Caso 4: Filtrar por data final");
        System.out.println(generateReportUseCase.findSchedulesByFilters
                (LocalDate.of(2025,06,15),true));

        System.out.println("////////////");
        System.out.println("Caso 5: Filtrar por data inicial e data final");
        System.out.println(generateReportUseCase.findSchedulesByFilters
                (LocalDate.of(2025,06,10),LocalDate.of(2025,06,20)));

        System.out.println("////////////");
        System.out.println("Caso 6: Filtrar por data inicial e funcionário");
        System.out.println(generateReportUseCase.findSchedulesByFilters
                (LocalDate.of(2025,06,5),3));

        System.out.println("////////////");
        System.out.println("Caso 7: Filtrar por data final e funcionário");
        System.out.println(generateReportUseCase.findSchedulesByFilters
                (LocalDate.of(2025,06,30),true,4));


        System.out.println("////////////");
        System.out.println("Caso 8: Filtrar por data inicial, data final e funcionário");
        System.out.println(generateReportUseCase.findSchedulesByFilters
                (LocalDate.of(2025,06,1),LocalDate.of(2025,06,30),2));
    }
    private static void findFuncionarioComIdNull(){
        findEmployeeUseCase.findOne(null);
    }
    private static void findFuncionarioComCpfInvalido(){
        CPF invalidCpf = CPF.of("12e.344.88");
        findEmployeeUseCase.findOneByCpf(invalidCpf);
    }
    private static void findClienteComIdNull(){
        findClientUseCase.findOne(null);
    }
    private static void findClienteComCpfInvalido(){
        CPF invalidCpf = CPF.of("12e.344.88");
        findClientUseCase.findOneByCPF(invalidCpf);
    }
    private static void criarFuncionarioVazio(){
        EmployeeInputDTO employee = new EmployeeInputDTO(null,null,null,null,null);
        createEmployeeUseCase.insert(employee);
        System.out.println(employee);
    }
    private static void criarFuncionarioComCpfExistente(){
        Email email = Email.of("test@gmail.com");
        CPF cpf = CPF.of("123.456.789-10");
        EmployeeInputDTO employee = new EmployeeInputDTO("João",cpf,"16998765443",email,"21/01/2000");
        createEmployeeUseCase.insert(employee);
        System.out.println(employee);
    }

    private static void criarFuncionarioComEmailECpfInvalidos(){
        Email invalidEmail = Email.of("invalidEmail.com");
        CPF invalidCPF = CPF.of("12e.344.77");
        EmployeeInputDTO invalidEmployee = new EmployeeInputDTO("João",invalidCPF,"16990001234",invalidEmail,"10/10/2000");
        createEmployeeUseCase.insert(invalidEmployee);
    }

    private static void criarClienteVazio(){
        Client client = new Client();
        createClientUseCase.insert(client);
        System.out.println(client);
    }

    private static void criarClienteComCpfExistente(){
        Email email = Email.of("test@gmail.com");
        CPF cpf = CPF.of("999.888.777-66");
        Client client = new Client("João",cpf,"16990001122",email);
        createClientUseCase.insert(client);
        System.out.println(client);
    }

    private static void criarClienteComEmailECpfInvalidos(){
        Email invalidEmail = Email.of("invalidEmail.com");
        CPF invalidCPF = CPF.of("12e.344.77");
        Client invalidClient = new Client("João",invalidCPF,"16990001122",invalidEmail);
        createClientUseCase.insert(invalidClient);
    }*/

    private static void mockData(){
        ServiceInputDTO service1 = new ServiceInputDTO("Corte básico", "Corte de cabelo masculino", 30.0,
                "Masculino","Corte");
        ServiceInputDTO service2 = new ServiceInputDTO("Barba", "Fazer a barba simples", 25.0,
        "Masculino","Barba");
        ServiceInputDTO service3 = new ServiceInputDTO("Corte e barba",
                "Corte de cabelo e barba feita masculino", 40.0,
                "Masculino","Cabelo e barba");
        ServiceInputDTO service4 = new ServiceInputDTO("Progressiva",
                "Progressiva cabelo feminino", 120.0,
                "Feminino","Cabelo");
        ServiceInputDTO service5 = new ServiceInputDTO("Chapinha",
                "Chapinha simples cabelo feminino", 50.0,
                "Feminino","Cabelo");
        ServiceInputDTO service6 = new ServiceInputDTO("Coloração",
                "Coloração cabelo feminino", 70.0,
                "Feminino", "Cabelo");
        ServiceInputDTO service7 = new ServiceInputDTO("Escova",
                "Escova simples cabelo feminino", 60.0,
                "Feminino", "Cabelo");

        createServiceUseCase.insert(service1);
        createServiceUseCase.insert(service2);
        createServiceUseCase.insert(service3);
        createServiceUseCase.insert(service4);
        createServiceUseCase.insert(service5);
        createServiceUseCase.insert(service6);
        createServiceUseCase.insert(service7);

        Email client1Email = Email.of("lucas@gmail.com");
        CPF client1Cpf = CPF.of("111.222.333-44");
        ClientInputDTO client1 = new ClientInputDTO("Lucas",client1Cpf,"16998765432",client1Email);

        Email client2Email = Email.of("jorge@gmail.com");
        CPF client2Cpf = CPF.of("000.111.222-33");
        ClientInputDTO client2 = new ClientInputDTO("Jorge",client2Cpf,"16998765431",client2Email);

        Email client3Email = Email.of("carloslinux@gmail.com");
        CPF client3Cpf = CPF.of("123.456.789-10");
        ClientInputDTO client3 = new ClientInputDTO("Carlos",client3Cpf,"16998765430",client3Email);

        Email client4Email = Email.of("pablo@gmail.com");
        CPF client4Cpf = CPF.of("123.456.789-11");
        ClientInputDTO client4 = new ClientInputDTO("Pablo",client4Cpf,"16998765432",client4Email);

        Email client5Email = Email.of("fabiobd@gmail.com");
        CPF client5Cpf = CPF.of("123.456.789-12");
        ClientInputDTO client5 = new ClientInputDTO("Fábio",client5Cpf,"16998765432",client5Email);

        Email client6Email = Email.of("luciano@gmail.com");
        CPF client6Cpf = CPF.of("123.456.789-13");
        ClientInputDTO client6 = new ClientInputDTO("Luciano",client6Cpf,"16988765432",client6Email);

        Email client7Email = Email.of("bruno@gmail.com");
        CPF client7Cpf = CPF.of("123.456.789-18");
        ClientInputDTO client7 = new ClientInputDTO("Bruno",client7Cpf,"16988765432",client7Email);

        createClientUseCase.insert(client1);
        createClientUseCase.insert(client2);
        createClientUseCase.insert(client3);
        createClientUseCase.insert(client4);
        createClientUseCase.insert(client5);
        createClientUseCase.insert(client6);
        createClientUseCase.insert(client7);

        Email employee1Email = Email.of("eduardo@gmail.com");
        CPF employee1Cpf = CPF.of("999.888.777-66");
        EmployeeInputDTO employee1 = new EmployeeInputDTO
                ("Eduardo",employee1Cpf,"16912345678",employee1Email,"11/11/2001");

        Email employee2Email = Email.of("victor@gmail.com");
        CPF employee2Cpf = CPF.of("000.888.777-66");
        EmployeeInputDTO employee2 = new EmployeeInputDTO
                ("Victor",employee2Cpf,"16912345679",employee2Email,"20/10/1999");

        Email employee3Email = Email.of("ariadne@gmail.com");
        CPF employee3Cpf = CPF.of("111.888.777-66");
        EmployeeInputDTO employee3 = new EmployeeInputDTO
                ("Ariadne",employee3Cpf,"16912345670",employee3Email,"30/01/1970");

        Email employee4Email = Email.of("emanuel@gmail.com");
        CPF employee4Cpf = CPF.of("888.888.777-77");
        EmployeeInputDTO employee4 = new EmployeeInputDTO
                ("Emanuel",employee4Cpf,"16912345671",employee4Email,"10/10/2004");

        Email employee5Email = Email.of("joazinho@gmail.com");
        CPF employee5Cpf = CPF.of("888.765.567-00");
        EmployeeInputDTO employee5 = new EmployeeInputDTO
                ("João",employee5Cpf,"16912345672",employee5Email,"30/11/1989");

        Email employee6Email = Email.of("antonio@gmail.com");
        CPF employee6Cpf = CPF.of("081.765.567-10");
        EmployeeInputDTO employee6 = new EmployeeInputDTO
                ("Antônio",employee6Cpf,"16912345672",employee6Email,"30/08/1982");


        Email employee7Email = Email.of("lucia@gmail.com");
        CPF employee7Cpf = CPF.of("088.965.567-11");
        EmployeeInputDTO employee7 = new EmployeeInputDTO
                ("Lúcia",employee7Cpf,"16912345672",employee7Email,"30/06/1980");

        createEmployeeUseCase.insert(employee1);
        createEmployeeUseCase.insert(employee2);
        createEmployeeUseCase.insert(employee3);
        createEmployeeUseCase.insert(employee4);
        createEmployeeUseCase.insert(employee5);
        createEmployeeUseCase.insert(employee6);
        createEmployeeUseCase.insert(employee7);

        EmployeeOutputDTO employeeModel1 = findEmployeeUseCase.findOne(1).get();
        EmployeeOutputDTO employeeModel2 = findEmployeeUseCase.findOne(2).get();
        EmployeeOutputDTO employeeModel3 = findEmployeeUseCase.findOne(3).get();
        EmployeeOutputDTO employeeModel4 = findEmployeeUseCase.findOne(4).get();
        EmployeeOutputDTO employeeModel5 = findEmployeeUseCase.findOne(5).get();
        EmployeeOutputDTO employeeModel6 = findEmployeeUseCase.findOne(6).get();
        EmployeeOutputDTO employeeModel7 = findEmployeeUseCase.findOne(7).get();
        inactivateEmployeeUseCase.inactivate(employeeModel6.id());

        ServiceOutputDTO serviceModel1 = findServiceUseCase.findOne(1).get();
        ServiceOutputDTO serviceModel2 = findServiceUseCase.findOne(2).get();
        ServiceOutputDTO serviceModel3 = findServiceUseCase.findOne(3).get();
        ServiceOutputDTO serviceModel4 = findServiceUseCase.findOne(4).get();
        ServiceOutputDTO serviceModel5 = findServiceUseCase.findOne(5).get();

        addEmployeeExpertiseUseCase.addExpertise(employeeModel1.id(), serviceModel1.id());
        addEmployeeExpertiseUseCase.addExpertise(employeeModel2.id(),serviceModel2.id());
        addEmployeeExpertiseUseCase.addExpertise(employeeModel3.id(),serviceModel3.id());
        addEmployeeExpertiseUseCase.addExpertise(employeeModel4.id(),serviceModel4.id());
        addEmployeeExpertiseUseCase.addExpertise(employeeModel5.id(),serviceModel5.id());

        /*Scheduling scheduling1 = new Scheduling(client1,employeeModel1,
                LocalDateTime.of(2025,6,5,19,0),service1);
        Scheduling scheduling2 = new Scheduling(client2,employeeModel2,
                LocalDateTime.of(2025,6,10,20,0),service2);
        Scheduling scheduling3 = new Scheduling(client3,employeeModel3,
                LocalDateTime.of(2025,6,15,21,0),service3);
        Scheduling scheduling4 = new Scheduling(client4,employeeModel4,
                LocalDateTime.of(2025,6,20,22,0),service4);
        Scheduling scheduling5 = new Scheduling(client5,employeeModel5,
                LocalDateTime.of(2025,6,25,18,0),service5);

        createSchedulingUseCase.insert(scheduling1);
        createSchedulingUseCase.insert(scheduling2);
        createSchedulingUseCase.insert(scheduling3);
        createSchedulingUseCase.insert(scheduling4);
        createSchedulingUseCase.insert(scheduling5);*/
    }
}


