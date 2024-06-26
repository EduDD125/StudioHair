package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.ApplicationView;
import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.entities.client.Client;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.client.usecases.FindClientUseCase;
import com.example.sistemacabeleleiro.domain.usecases.employee.dto.EmployeeOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.employee.usecases.FindEmployeeUseCase;
import com.example.sistemacabeleleiro.domain.usecases.reports.ExportReportUseCase;
import com.example.sistemacabeleleiro.domain.usecases.reports.GenerateReportUseCase;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingOutputDTO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScheduleManagementUIController {

    @FXML
    private ComboBox<String> comboClient;
    @FXML
    private ComboBox<String> comboEmployee;
    @FXML
    private ComboBox<String> comboService;
    @FXML
    private DatePicker txtBeginDate;
    @FXML
    private DatePicker txtEndDate;
    @FXML
    private DatePicker txtExactlyDate;
    @FXML
    private TextField txtHour;
    @FXML
    private TableView<SchedulingOutputDTO> tableView;
    @FXML
    private TableColumn<SchedulingOutputDTO, Number> cId;

    @FXML
    private TableColumn<SchedulingOutputDTO, String> cClient;
    @FXML
    private TableColumn<SchedulingOutputDTO, String> cEmployee;
    @FXML
    private TableColumn<SchedulingOutputDTO, LocalDateTime> cRealizationDate;
    @FXML
    private TableColumn<SchedulingOutputDTO, String> cService;
    @FXML
    private TableColumn<SchedulingOutputDTO, Number> cValue;

    @FXML
    private TableColumn<SchedulingOutputDTO, String> cStatus;

    ObservableList<SchedulingOutputDTO> tableData;

    @FXML
    private void initialize() {
        bindTableToItensList();
        bindColumnsToValueSource();
        loadDataAndShow();
    }

    private void bindTableToItensList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
        List<String> clients = Main.findClientUseCase.findAll().stream()
                .map(c -> c.name()).toList();
        List<String> employees = Main.findEmployeeUseCase.findAll().stream()
                .map(e -> e.name()).toList();
        List<String> services = Main.findServiceUseCase.findAll().stream()
                        .map(s -> s.name()).toList();
        comboClient.setItems(FXCollections.observableArrayList(clients));
        comboEmployee.setItems(FXCollections.observableArrayList(employees));
        comboService.setItems(FXCollections.observableArrayList(services));

    }

    private void bindColumnsToValueSource() {
        cId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().id()));
        cClient.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().clientName()));
        cEmployee.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().employeeName()));
        cRealizationDate.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().realizationDate()));
        cService.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().serviceName()));
        cValue.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().totalValue()));
        cStatus.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().status().name()));
    }

    private void loadDataAndShow() {
        List<SchedulingOutputDTO> schedules = Main.findSchedulingUseCase.findAll();
        tableData.clear();
        tableData.addAll(schedules);
    }

    public void editSchedule(ActionEvent actionEvent) throws IOException{
        showScheduleInMode(UIMode.UPDATE);
    }

    public void detailSchedule(ActionEvent actionEvent) throws IOException{
        showScheduleInMode(UIMode.VIEW);
    }

    public void registerSchedule(ActionEvent actionEvent) throws IOException{
        ApplicationView.setRoot("ScheduleUI");
    }

    public void filterSchedules(ActionEvent event) {
        List<SchedulingOutputDTO> schedulingsList = Main.findSchedulingUseCase.findAll();

        Optional<String> selectedClientName = Optional.ofNullable(comboClient.getValue());
        Optional<String> selectedEmployeeName = Optional.ofNullable(comboEmployee.getValue());
        Optional<String> selectedServiceName = Optional.ofNullable(comboService.getValue());
        Optional<LocalDateTime> exactDateTime = getExactDateTime();
        Optional<LocalDate> startDate = Optional.ofNullable(txtBeginDate.getValue());
        Optional<LocalDate> endDate = Optional.ofNullable(txtEndDate.getValue());

        if (selectedClientName.isPresent()) {
            int clientId = Main.findClientUseCase.findOneByName(selectedClientName.get()).get().id();
            schedulingsList = Main.findSchedulingUseCase.findByClient(clientId);
        }

        if (selectedEmployeeName.isPresent()) {
            String employeeName = selectedEmployeeName.get();
            schedulingsList = schedulingsList.stream()
                    .filter(s -> s.employeeName().equals(employeeName))
                    .collect(Collectors.toList());
        }
        if (selectedServiceName.isPresent()){
            String serviceName = selectedServiceName.get();
            schedulingsList = schedulingsList.stream()
                    .filter(s -> s.serviceName().equals(serviceName))
                    .collect(Collectors.toList());
        }

        if (exactDateTime.isPresent()) {
            schedulingsList = Main.findSchedulingUseCase.findByScheduledDate(exactDateTime.get())
                    .map(List::of).orElse(List.of());
        }

        if (startDate.isPresent() && endDate.isPresent()) {
            schedulingsList = Main.findSchedulingUseCase.findByTimePeriod(startDate.get(), endDate.get());
        }

        if (!schedulingsList.isEmpty()) {
            tableData.clear();
            tableData.addAll(schedulingsList);
        }
    }

    private Optional<LocalDateTime> getExactDateTime() {
        LocalDate date = txtExactlyDate.getValue();
        String timeText = txtHour.getText();

        if (date == null || timeText == null || timeText.isEmpty()) {
            return Optional.empty();
        }

        try {
            LocalTime time = LocalTime.parse(timeText, DateTimeFormatter.ofPattern("HH:mm"));
            return Optional.of(LocalDateTime.of(date, time));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    private void showScheduleInMode(UIMode mode) throws IOException {
        SchedulingOutputDTO selectedSchedule = tableView.getSelectionModel().getSelectedItem();
        if (selectedSchedule != null) {
            ApplicationView.setRoot("ScheduleUI");
            SchedulesController controller = (SchedulesController) ApplicationView.getController();
            controller.setSchedule(selectedSchedule, mode);
        }
    }

    public void refresh(ActionEvent event) {
        loadDataAndShow();
    }

    public void goBackToMainMenu(ActionEvent actionEvent) throws IOException{
        ApplicationView.setRoot("MainMenuUI");
    }

    public void createRerport(ActionEvent actionEvent) {

        GenerateReportUseCase generateReportUseCase = new GenerateReportUseCase();


        // Exibir ou exportar o relatório conforme necessário
        ExportReportUseCase exportReportUseCase = new ExportReportUseCase(generateReportUseCase);
        exportReportUseCase.generatePDF2("report", tableData);
    }
}
