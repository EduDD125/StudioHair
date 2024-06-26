package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.ApplicationView;
import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.entities.schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.employee.dto.EmployeeOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingInputDTO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingUpdateDTO;
import com.example.sistemacabeleleiro.domain.usecases.service.dto.ServiceOutputDTO;
import javafx.collections.FXCollections;
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

public class SchedulesController {


    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtId;
    @FXML
    private ComboBox comboClient;
    @FXML
    private ComboBox comboEmployee;
    @FXML
    private ComboBox comboService;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtHour;
    @FXML
    private ChoiceBox choiceStatus;

    private SchedulingOutputDTO schedule;

    @FXML
    private void initialize(){
        txtId.setDisable(true);
        txtPrice.setDisable(true);

        choiceStatus.setItems(FXCollections.observableArrayList(SchedulingStatus.values()));
        choiceStatus.setDisable(true);
        List<ClientOutputDTO> clients = Main.findClientUseCase.findAll();
        comboClient.setItems(FXCollections.observableArrayList(clients));

        List<EmployeeOutputDTO> employees = Main.findEmployeeUseCase.findAll();
        comboEmployee.setItems(FXCollections.observableArrayList(employees));

        List<ServiceOutputDTO> services = Main.findServiceUseCase.findAll();
        comboService.setItems(FXCollections.observableArrayList(services));
    }

    public void saveOrUpdate(ActionEvent event) {
        getEntityFromView();
        boolean newSchedule = Main.findSchedulingUseCase.findOne(schedule.id()).isEmpty();
        ClientOutputDTO client = (ClientOutputDTO) comboClient.getSelectionModel().getSelectedItem();
        EmployeeOutputDTO employee = (EmployeeOutputDTO) comboEmployee.getSelectionModel().getSelectedItem();
        ServiceOutputDTO service = (ServiceOutputDTO) comboService.getSelectionModel().getSelectedItem();
        if (newSchedule){
            SchedulingInputDTO schedulingInput = new SchedulingInputDTO(
                   client.id(),
                    employee.id(),
                    service.id(),
                    getExactDateTime().get()
            );

            Main.createSchedulingUseCase.insert(schedulingInput);
            showSuccessMessage("Schedule created successfully!");
        }else{
            SchedulingUpdateDTO schedulingUpdate = new SchedulingUpdateDTO(
                    Integer.parseInt(txtId.getText()),
                    client.id(),
                    employee.id(),
                    service.id(),
                    getExactDateTime().get()
            );

            Main.updateScheduleUseCase.update(schedulingUpdate);
            showSuccessMessage("Schedule updated successfully!");
        }
    }

    private void getEntityFromView(){
        if (schedule == null) {
            int id = -1;
            double totalValue = -1;
            if (!txtId.getText().isEmpty()) {
                id = Integer.valueOf(txtId.getText());
                totalValue = Double.valueOf(txtPrice.getText());
            }
            ClientOutputDTO client = (ClientOutputDTO) comboClient.getSelectionModel().getSelectedItem();
            EmployeeOutputDTO employee = (EmployeeOutputDTO) comboEmployee.getSelectionModel().getSelectedItem();
            ServiceOutputDTO service = (ServiceOutputDTO) comboService.getSelectionModel().getSelectedItem();
            schedule = new SchedulingOutputDTO(
                    id,
                    client.id(),
                    client.name(),
                    employee.id(),
                    employee.name(),
                    service.id(),
                    service.name(),
                    totalValue,
                    getExactDateTime().get(),
                    (SchedulingStatus) choiceStatus.getValue()
            );
        }
    }

    private Optional<LocalDateTime> getExactDateTime() {
        LocalDate date = txtDate.getValue();
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

    public void setSchedule(SchedulingOutputDTO schedule, UIMode mode){
        if (schedule == null)
            throw new IllegalArgumentException("Schedule can not be null");

        this.schedule = schedule;
        setEntityIntoView();

        configureViewMode();
    }
    private void setEntityIntoView(){
        txtId.setText(String.valueOf(schedule.id()));
        comboClient.setValue(schedule.clientName());
        comboEmployee.setValue(schedule.employeeName());
        comboService.setValue(schedule.serviceName());
        txtPrice.setText(String.valueOf(schedule.totalValue()));
        txtDate.setValue(schedule.realizationDate().toLocalDate());
        txtHour.setText(String.valueOf(schedule.realizationDate().toLocalTime()));
        choiceStatus.setValue(schedule.status());
    }

    private void configureViewMode(){
        btnSave.setVisible(false);
        txtDate.setDisable(true);
        txtHour.setDisable(true);
        txtPrice.setDisable(true);
        comboClient.setDisable(true);
        comboEmployee.setDisable(true);
        comboService.setDisable(true);
    }

    public void cancelSchedule(ActionEvent event) {
    }

    public void confirmSchedule(ActionEvent event) {
    }

    private void showSuccessMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        returnToPreviousScene();
    }
    private void returnToPreviousScene(){
        try {
            ApplicationView.setRoot("ScheduleManagementUI");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void goBackToMenu(ActionEvent event) {
        returnToPreviousScene();
    }
}
