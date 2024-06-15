package com.example.sistemacabeleleiro.application.main;

import com.example.sistemacabeleleiro.application.repository.inmemory.InMemoryClientDAO;
import com.example.sistemacabeleleiro.application.repository.inmemory.InMemoryEmployeeDAO;
import com.example.sistemacabeleleiro.application.repository.inmemory.InMemorySchedulingDAO;
import com.example.sistemacabeleleiro.application.repository.inmemory.InMemoryServiceDAO;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.email.Email;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.employee.EmployeeStatus;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.usecases.client.*;
import com.example.sistemacabeleleiro.domain.usecases.employee.*;
import com.example.sistemacabeleleiro.domain.usecases.reports.ExportReportUseCase;
import com.example.sistemacabeleleiro.domain.usecases.reports.GenerateReportUseCase;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.*;
import com.example.sistemacabeleleiro.domain.usecases.service.*;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        mockData();

        /* TESTS OF SYSTEM USE CASES */

        /* MOCK DE DADOS, CRIAÇÃO DE AGENDAMENTO NO "SUNNY DAY" */
        List<Client> clients = findClientUseCase.findAll();
        for(Client c:clients){
            System.out.println(c);
        }
        System.out.println("/////");
        List<Employee> employees = findEmployeeUseCase.findAll();
        for(Employee e:employees){
            System.out.println(e);
        }
        List<Service> services = findServiceUseCase.findAll();
        for(Service s:services){
            System.out.println(s);
        }

        List<Scheduling> schedules = findSchedulingUseCase.findAll();
        for (Scheduling s:schedules){
            System.out.println(s);
        }

        System.out.println("-- CASOS DE TESTE --");

        // CASOS DE TESTE PARA RELATÓRIOS
        gerarPDF();
        gerarRelatoriosEmSunnyDay();

        // CASOS DE TESTE PARA FUNCIONÁRIO
        removerFuncionarioAtivo();
        removerFuncionarioComAgendamento();
        inativarFuncionarioJaInativo();
        ativarFuncionarioJaAtivo();
        findFuncionarioComCpfInexistente();
        findFuncionarioComIdNull();
        findFuncionarioComCpfInvalido();
        criarFuncionarioVazio();
        criarFuncionarioComCpfExistente();
        criarFuncionarioComEmailECpfInvalidos();
        //atualizarFuncionario();

        // CASOS DE TESTE PARA CLIENTE
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
        //atualizarCliente();

        // CASOS DE TESTE PARA SERVIÇOS
        criarServicoVazio();
        findServico();
        findTodosServicos();
        findServicosPorCategoria();
        findServicosComDesconto();
        findServicoPorFaixaDePreco();
        atualizarServico();
        /*removerServico();
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

    private static void atualizarServico() {
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
        Employee employee = findEmployeeUseCase.findOne(6).get();
        inactivateEmployeeUseCase.inactivate(employee);
    }

    private static void ativarClienteJaAtivo(){
        Client client = findClientUseCase.findOne(1).get();
        activateClientUseCase.activate(client);
    }

    private static void ativarFuncionarioJaAtivo(){
        Employee employee = findEmployeeUseCase.findOne(1).get();
        activateEmployeeUseCase.activate(employee);
    }

    private static void gerarPDF(){
        exportReportUseCase.exportSchedules("report_all");
        System.out.println("PDF gerado com sucesso!");
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
        Employee employee = new Employee();
        createEmployeeUseCase.insert(employee);
        System.out.println(employee);
    }
    private static void criarFuncionarioComCpfExistente(){
        Email email = Email.of("test@gmail.com");
        CPF cpf = CPF.of("123.456.789-10");
        Employee employee = new Employee("João",cpf,"16998765443",email,"21/01/2000");
        createEmployeeUseCase.insert(employee);
        System.out.println(employee);
    }

    private static void criarFuncionarioComEmailECpfInvalidos(){
        Email invalidEmail = Email.of("invalidEmail.com");
        CPF invalidCPF = CPF.of("12e.344.77");
        Employee invalidEmployee = new Employee("João",invalidCPF,"16990001234",invalidEmail,"10/10/2000");
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
    }

    private static void mockData(){
        Service service1 = new Service("Corte básico", "Corte de cabelo masculino", 30.0,
                "Masculino","Corte",0.0);
        Service service2 = new Service("Barba", "Fazer a barba simples", 25.0,
        "Masculino","Barba",0.0);
        Service service3 = new Service("Corte e barba",
                "Corte de cabelo e barba feita masculino", 40.0,
                "Masculino","Cabelo e barba",0.0);
        Service service4 = new Service("Progressiva",
                "Progressiva cabelo feminino", 120.0,
                "Feminino","Cabelo",0.0);
        Service service5 = new Service("Chapinha",
                "Chapinha simples cabelo feminino", 50.0,
                "Feminino","Cabelo",0.0);
        Service service6 = new Service("Coloração",
                "Coloração cabelo feminino", 70.0,
                "Feminino", "Cabelo", 0.2);
        Service service7 = new Service("Escova",
                "Escova simples cabelo feminino", 60.0,
                "Feminino", "Cabelo", 0.1);

        createServiceUseCase.insert(service1);
        createServiceUseCase.insert(service2);
        createServiceUseCase.insert(service3);
        createServiceUseCase.insert(service4);
        createServiceUseCase.insert(service5);
        createServiceUseCase.insert(service6);
        createServiceUseCase.insert(service7);

        Email client1Email = Email.of("lucas@gmail.com");
        CPF client1Cpf = CPF.of("111.222.333-44");
        Client client1 = new Client("Lucas",client1Cpf,"16998765432",client1Email);

        Email client2Email = Email.of("jorge@gmail.com");
        CPF client2Cpf = CPF.of("000.111.222-33");
        Client client2 = new Client("Jorge",client2Cpf,"16998765431",client2Email);

        Email client3Email = Email.of("carloslinux@gmail.com");
        CPF client3Cpf = CPF.of("123.456.789-10");
        Client client3 = new Client("Carlos",client3Cpf,"16998765430",client3Email);

        Email client4Email = Email.of("pablo@gmail.com");
        CPF client4Cpf = CPF.of("123.456.789-11");
        Client client4 = new Client("Pablo",client4Cpf,"16998765432",client4Email);

        Email client5Email = Email.of("fabiobd@gmail.com");
        CPF client5Cpf = CPF.of("123.456.789-12");
        Client client5 = new Client("Fábio",client5Cpf,"16998765432",client5Email);

        Email client6Email = Email.of("luciano@gmail.com");
        CPF client6Cpf = CPF.of("123.456.789-13");
        Client client6 = new Client("Luciano",client6Cpf,"16988765432",client6Email,
                ClientStatus.INACTIVE);

        Email client7Email = Email.of("bruno@gmail.com");
        CPF client7Cpf = CPF.of("123.456.789-18");
        Client client7 = new Client("Bruno",client7Cpf,"16988765432",client7Email);

        createClientUseCase.insert(client1);
        createClientUseCase.insert(client2);
        createClientUseCase.insert(client3);
        createClientUseCase.insert(client4);
        createClientUseCase.insert(client5);
        createClientUseCase.insert(client6);
        createClientUseCase.insert(client7);

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

        Email employee4Email = Email.of("emanuel@gmail.com");
        CPF employee4Cpf = CPF.of("888.888.777-77");
        Employee employee4 = new Employee
                ("Emanuel",employee4Cpf,"16912345671",employee4Email,"10/10/2004");

        Email employee5Email = Email.of("joazinho@gmail.com");
        CPF employee5Cpf = CPF.of("888.765.567-00");
        Employee employee5 = new Employee
                ("João",employee5Cpf,"16912345672",employee5Email,"30/11/1989");

        Email employee6Email = Email.of("antonio@gmail.com");
        CPF employee6Cpf = CPF.of("081.765.567-10");
        Employee employee6 = new Employee
                ("Antônio",employee6Cpf,"16912345672",employee6Email,"30/08/1982",
                        EmployeeStatus.INACTIVE);

        Email employee7Email = Email.of("lucia@gmail.com");
        CPF employee7Cpf = CPF.of("088.965.567-11");
        Employee employee7 = new Employee
                ("Lúcia",employee7Cpf,"16912345672",employee7Email,"30/06/1980");

        createEmployeeUseCase.insert(employee1);
        createEmployeeUseCase.insert(employee2);
        createEmployeeUseCase.insert(employee3);
        createEmployeeUseCase.insert(employee4);
        createEmployeeUseCase.insert(employee5);
        createEmployeeUseCase.insert(employee6);
        createEmployeeUseCase.insert(employee7);

        addEmployeeExpertiseUseCase.addExpertise(employee1, service1);
        addEmployeeExpertiseUseCase.addExpertise(employee2,service2);
        addEmployeeExpertiseUseCase.addExpertise(employee3,service3);
        addEmployeeExpertiseUseCase.addExpertise(employee4,service4);
        addEmployeeExpertiseUseCase.addExpertise(employee5,service5);

        Scheduling scheduling1 = new Scheduling(client1,employee1,
                LocalDateTime.of(2025,6,5,19,0),service1);
        Scheduling scheduling2 = new Scheduling(client2,employee2,
                LocalDateTime.of(2025,6,10,20,0),service2);
        Scheduling scheduling3 = new Scheduling(client3,employee3,
                LocalDateTime.of(2025,6,15,21,0),service3);
        Scheduling scheduling4 = new Scheduling(client4,employee4,
                LocalDateTime.of(2025,6,20,22,0),service4);
        Scheduling scheduling5 = new Scheduling(client5,employee5,
                LocalDateTime.of(2025,6,25,18,0),service5);

        createSchedulingUseCase.insert(scheduling1);
        createSchedulingUseCase.insert(scheduling2);
        createSchedulingUseCase.insert(scheduling3);
        createSchedulingUseCase.insert(scheduling4);
        createSchedulingUseCase.insert(scheduling5);
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
        inactivateClientUseCase = new InactivateClientUseCase(clientDAO);
        activateClientUseCase = new ActivateClientUseCase(clientDAO);

        activateEmployeeUseCase = new ActivateEmployeeUseCase(employeeDAO);
        updateEmployeeUseCase = new UpdateEmployeeUseCase(employeeDAO);
        addEmployeeExpertiseUseCase = new AddEmployeeExpertiseUseCase(employeeDAO,serviceDAO);
        createEmployeeUseCase = new CreateEmployeeUseCase(employeeDAO,clientDAO);
        findEmployeeUseCase = new FindEmployeeUseCase(employeeDAO);
        inactivateEmployeeUseCase = new InactivateEmployeeUseCase(employeeDAO);
        removeEmployeeUseCase = new RemoveEmployeeUseCase(employeeDAO,schedulingDAO);
        removeExpertiseFromEmployeeUseCase = new RemoveExpertiseFromEmployeeUseCase(employeeDAO,serviceDAO,updateEmployeeUseCase);

        cancelSchedulingUseCase = new CancelSchedulingUseCase(schedulingDAO);
        createSchedulingUseCase = new CreateSchedulingUseCase(schedulingDAO,clientDAO,employeeDAO,serviceDAO);
        findSchedulingUseCase = new FindSchedulingUseCase(schedulingDAO);
        //updateScheduleUseCase = new UpdateScheduleUseCase(schedulingDAO);
        inactivateServiceUseCase = new InactivateServiceUseCase(serviceDAO);
        activateServiceUseCase = new ActivateServiceUseCase(serviceDAO);

        createServiceUseCase = new CreateServiceUseCase(serviceDAO);
        findServiceUseCase = new FindServiceUseCase(serviceDAO);
        removeServiceUseCase = new RemoveServiceUseCase(serviceDAO,employeeDAO,schedulingDAO);
        updateServiceUseCase = new UpdateServiceUseCase(serviceDAO);

        generateReportUseCase = new GenerateReportUseCase(schedulingDAO,employeeDAO);
        exportReportUseCase = new ExportReportUseCase(generateReportUseCase);

    }
}
