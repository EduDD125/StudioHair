package com.example.sistemacabeleleiro.Application.controller;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.SchedulingStatus;
import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.RemoveEmployeeUseCase;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.CancelSchedulingUseCase;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.FindSchedulingUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class ScheculeManagementUIController {

    @FXML
    private TableView<Scheduling> tableView;
    @FXML
    private TableColumn<Scheduling, Integer> cClient;
    @FXML
    private TableColumn<Scheduling, Integer> cEmployee;
    @FXML
    private TableColumn<Scheduling, Integer> cRealizationDate;
    @FXML
    private TableColumn<Scheduling, Integer> cService;
    @FXML
    private TableColumn<Scheduling, Integer> cStatus;

    ObservableList<Scheduling> tableData;

    @FXML
    private void initialize() {
        bindTableToItensList();
        bindColumnsToValueSource();
        loadDataAndShow();
    }

    private void bindTableToItensList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnsToValueSource() {
        cClient.setCellValueFactory(new PropertyValueFactory<>("name"));
        cEmployee.setCellValueFactory(new PropertyValueFactory<>("name"));
        cRealizationDate.setCellValueFactory(new PropertyValueFactory<>("dataRealizacao"));
        cService.setCellValueFactory(new PropertyValueFactory<>("service"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataAndShow() {
        List<Scheduling> schedules = FindSchedulingUseCase.findAll();
        tableData.clear();
        tableData.addAll(schedules);
    }

    public void cancelSchedule(ActionEvent actionEvent) {
        Scheduling selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            CancelSchedulingUseCase.cancel(selectedItem);
            loadDataAndShow();
        }
    }

    public void editSchedule(ActionEvent actionEvent) {
    }

    public void detailSchedule(ActionEvent actionEvent) {
    }

    public void registerSchedule(ActionEvent actionEvent) {
    }

    public void goBackToMainMenu(ActionEvent actionEvent) {
    }
}
