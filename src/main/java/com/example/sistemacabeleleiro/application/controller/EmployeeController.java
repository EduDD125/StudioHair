package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.ApplicationView;
import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;
import com.example.sistemacabeleleiro.domain.entities.employee.EmployeeStatus;
import com.example.sistemacabeleleiro.domain.usecases.employee.dto.EmployeeInputDTO;
import com.example.sistemacabeleleiro.domain.usecases.employee.dto.EmployeeOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.employee.dto.EmployeeUpdateDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EmployeeController {

    @FXML
    private TextField nameTextInput;
    @FXML
    private TextField cpfTextInput;
    @FXML
    private TextField emailTextInput;
    @FXML
    private TextField phoneTextInput;
    @FXML
    private TextField dateOfBirthTextInput;
    @FXML
    private ChoiceBox<EmployeeStatus> statusChoiceBox;
    @FXML
    private ListView<String> expertiseListView;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    private EmployeeOutputDTO employee;

    @FXML
    private void initialize() {
        statusChoiceBox.setItems(FXCollections.observableArrayList(EmployeeStatus.values()));
    }

    public void saveOrUpdate(ActionEvent event) {
        getEntityFromView();
        boolean newEmployee = Main.findEmployeeUseCase.findOne(employee.id()).isEmpty();

        if (newEmployee) {
            EmployeeInputDTO employeeInput = new EmployeeInputDTO(
                    nameTextInput.getText(),
                    CPF.of(cpfTextInput.getText()),
                    phoneTextInput.getText(),
                    Email.of(emailTextInput.getText()),
                    dateOfBirthTextInput.getText()
            );

            Main.createEmployeeUseCase.insert(employeeInput);
            showSuccessMessage("Employee created successfully!");
        } else {
            EmployeeUpdateDTO employeeUpdate = new EmployeeUpdateDTO(
                    employee.id(),
                    nameTextInput.getText(),
                    CPF.of(cpfTextInput.getText()),
                    phoneTextInput.getText(),
                    Email.of(emailTextInput.getText()),
                    dateOfBirthTextInput.getText()
            );

            Main.updateEmployeeUseCase.update(employeeUpdate);
            showSuccessMessage("Employee updated successfully!");
        }
    }

    private void getEntityFromView() {
        if (employee == null) {
            int id = -1;
            if (employee.id() != 0) {
                id = employee.id();
            }
            employee = new EmployeeOutputDTO(
                    id,
                    nameTextInput.getText(),
                    CPF.of(cpfTextInput.getText()),
                    phoneTextInput.getText(),
                    Email.of(emailTextInput.getText()),
                    dateOfBirthTextInput.getText(),
                    statusChoiceBox.getValue()
            );
        }
    }

    public void setEmployee(EmployeeOutputDTO employee, UIMode mode) {
        if (employee == null)
            throw new IllegalArgumentException("Employee can not be null.");

        this.employee = employee;
        setEntityIntoView();

        if (mode == UIMode.VIEW)
            configureViewMode();
    }

    private void setEntityIntoView() {
        employee.id();
        nameTextInput.setText(employee.name());
        cpfTextInput.setText(employee.cpf().getValue());
        emailTextInput.setText(employee.email().getValue());
        phoneTextInput.setText(employee.phone());
        statusChoiceBox.setValue(employee.status());
    }

    private void configureViewMode() {
        cancelButton.setText("Close");
        saveButton.setVisible(false);

        nameTextInput.setDisable(true);
        cpfTextInput.setDisable(true);
        emailTextInput.setDisable(true);
        phoneTextInput.setDisable(true);
        statusChoiceBox.setDisable(true);
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        returnToPreviousScene();
    }

    private void returnToPreviousScene() {
        try {
            ApplicationView.setRoot("EmployeeManagementUI");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToPreviousScene(ActionEvent event) throws IOException {
        returnToPreviousScene();
    }
}
