package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.ApplicationView;
import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.usecases.employee.dto.EmployeeOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.utils.EntityNotFoundException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class EmployeeManagementUIController {

    @FXML
    private TextField nameInput;
    @FXML
    private TextField cpfInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TableView<EmployeeOutputDTO> tableView;

    @FXML
    private TableColumn<EmployeeOutputDTO, String> cName;
    @FXML
    private TableColumn<EmployeeOutputDTO, String> cCPF;
    @FXML
    private TableColumn<EmployeeOutputDTO, String> cExpertise;
    @FXML
    private TableColumn<EmployeeOutputDTO, String> cPhone;
    @FXML
    private TableColumn<EmployeeOutputDTO, String> cEmail;
    @FXML
    private TableColumn<EmployeeOutputDTO, String> cBirthDate;
    @FXML
    private TableColumn<EmployeeOutputDTO, String> cStatus;

    private ObservableList<EmployeeOutputDTO> tableData;

    @FXML
    private void initialize() {
        bindTableToItemsList();
        bindColumnsToValueSource();
        loadDataAndShow();
    }

    private void bindTableToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnsToValueSource() {
        cName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().name()));
        cCPF.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().cpf().getValue()));
        cExpertise.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().expertise()));
        cPhone.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().phone()));
        cEmail.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().email().getValue()));
        cBirthDate.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().dateOfBirth()));
        cStatus.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().status().name()));
    }

    private void loadDataAndShow() {
        List<EmployeeOutputDTO> employees = Main.findEmployeeUseCase.findAll();
        tableData.clear();
        if (!employees.isEmpty()) System.out.println("tem algo"); else System.out.println("nada");
        tableData.addAll(employees);
    }

    public void deleteEmployee(ActionEvent actionEvent) {
        EmployeeOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Main.removeEmployeeUseCase.remove(selectedItem.id());
            loadDataAndShow();
        }
    }

    public void editEmployee(ActionEvent actionEvent) throws IOException {
        showEmployeeInMode(UIMode.UPDATE);
    }

    public void detailEmployee(ActionEvent actionEvent) throws IOException {
        showEmployeeInMode(UIMode.VIEW);
    }

    public void registerEmployee(ActionEvent actionEvent) throws IOException {
        ApplicationView.setRoot("EmployeeUI");
    }

    public void inactivateEmployee(ActionEvent event) {
        EmployeeOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Main.inactivateEmployeeUseCase.inactivate(selectedItem.id());
            loadDataAndShow();
        }
    }

    public void activateEmployee(ActionEvent event) {
        EmployeeOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Main.activateEmployeeUseCase.activate(selectedItem.id());
            loadDataAndShow();
        }
    }

    public void findEmployees(ActionEvent event) throws IOException {
        EmployeeOutputDTO employee;
        if (!nameInput.getText().isEmpty()) {
            employee = Main.findEmployeeUseCase.findOneByName(nameInput.getText())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        } else {
            if (!cpfInput.getText().isEmpty()) {
                employee = Main.findEmployeeUseCase.findOneByCpf(CPF.of(cpfInput.getText()))
                        .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
            } else {
                throw new RuntimeException("Choose at least one filter");
            }
        }
        ApplicationView.setRoot("EmployeeUI");
        EmployeeController controller = (EmployeeController) ApplicationView.getController();
        controller.setEmployee(employee, UIMode.VIEW);
    }

    private void showEmployeeInMode(UIMode mode) throws IOException {
        EmployeeOutputDTO selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            ApplicationView.setRoot("EmployeeUI");
            EmployeeController controller = (EmployeeController) ApplicationView.getController();
            controller.setEmployee(selectedEmployee, mode);
        }
    }

    public void goBackToMainMenu(ActionEvent actionEvent) throws IOException {
        ApplicationView.setRoot("MainMenuUI");
    }
}
