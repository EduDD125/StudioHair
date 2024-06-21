package com.example.sistemacabeleleiro.Application.controller;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Employee.Employee;
import com.example.sistemacabeleleiro.Domain.UseCases.Employee.FindEmployeeUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class EmployeeManagementUIController {

    @FXML
    private TableView<Employee> tableView;
    @FXML
    private TableColumn<Employee, Integer> cName;
    @FXML
    private TableColumn<Employee, Integer> cCPF;
    @FXML
    private TableColumn<Employee, Integer> cExpertise;
    @FXML
    private TableColumn<Employee, Integer> cPhone;
    @FXML
    private TableColumn<Employee, Integer> cEmail;
    @FXML
    private TableColumn<Employee, Integer> cBirthDate;
    @FXML
    private TableColumn<Employee, Integer> cStatus;

    ObservableList<Employee> tableData;

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
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        cExpertise.setCellValueFactory(new PropertyValueFactory<>("expertise"));
        cPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cBirthDate.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataAndShow() {
        List<Employee> employees = FindEmployeeUseCase.findAll();
        tableData.clear();
        tableData.addAll(employees);
    }

    public void deleteEmployee(ActionEvent actionEvent) {
    }

    public void editEmployee(ActionEvent actionEvent) {
    }

    public void detailEmployee(ActionEvent actionEvent) {
    }

    public void registerEmployee(ActionEvent actionEvent) {
    }
}
