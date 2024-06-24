package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.usecases.employee.dto.EmployeeOutputDTO;
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
    private TableView<EmployeeOutputDTO> tableView;
    @FXML
    private TableColumn<EmployeeOutputDTO, Integer> cName;
    @FXML
    private TableColumn<EmployeeOutputDTO, Integer> cCPF;
    @FXML
    private TableColumn<EmployeeOutputDTO, Integer> cExpertise;
    @FXML
    private TableColumn<EmployeeOutputDTO, Integer> cPhone;
    @FXML
    private TableColumn<EmployeeOutputDTO, Integer> cEmail;
    @FXML
    private TableColumn<EmployeeOutputDTO, Integer> cBirthDate;
    @FXML
    private TableColumn<EmployeeOutputDTO, Integer> cStatus;

    ObservableList<EmployeeOutputDTO> tableData;

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
        List<EmployeeOutputDTO> employees = Main.findEmployeeUseCase.findAll();
        tableData.clear();
        tableData.addAll(employees);
    }

    public void deleteEmployee(ActionEvent actionEvent) {
        EmployeeOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Main.removeEmployeeUseCase.remove(selectedItem.id());
            loadDataAndShow();
        }
    }

    public void editEmployee(ActionEvent actionEvent) {
    }

    public void detailEmployee(ActionEvent actionEvent) {
    }

    public void registerEmployee(ActionEvent actionEvent) {
    }
}
