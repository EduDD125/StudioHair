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
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDateOfBirth;
    @FXML
    private ChoiceBox choiceStatus;
    @FXML
    private ListView expertiseListView;

    private EmployeeOutputDTO employee;

    @FXML
    private void initialize(){
        txtId.setDisable(true);
        expertiseListView.setDisable(true);
        choiceStatus.setItems(FXCollections.observableArrayList(EmployeeStatus.values()));
        choiceStatus.setDisable(true);
    }

    public void saveOrUpdate(ActionEvent event) {
        getEntityFromView();
        boolean newEmployee= Main.findEmployeeUseCase.findOne(employee.id()).isEmpty();
        if (newEmployee){
            EmployeeInputDTO employeeInput = new EmployeeInputDTO(
                    txtName.getText(),
                    CPF.of(txtCpf.getText()),
                    txtPhone.getText(),
                    Email.of(txtEmail.getText()),
                    txtDateOfBirth.getText()
            );

            Main.createEmployeeUseCase.insert(employeeInput);
            showSuccessMessage("Employee created successfully!");
        }else{
            EmployeeUpdateDTO employeeUpdate = new EmployeeUpdateDTO(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    CPF.of(txtCpf.getText()),
                    txtPhone.getText(),
                    Email.of(txtEmail.getText()),
                    txtDateOfBirth.getText()
            );

            Main.updateEmployeeUseCase.update(employeeUpdate);
            showSuccessMessage("Employee updated successfully!");
        }
    }

    private void getEntityFromView(){
        if (employee == null) {
            List<String> expertises = new ArrayList<>();
            int id = -1;
            if (!txtId.getText().isEmpty()) {
                id = Integer.valueOf(txtId.getText());
                expertises = expertiseListView.getItems().stream().toList();
            }
            employee = new EmployeeOutputDTO(
                    id,
                    txtName.getText(),
                    CPF.of(txtCpf.getText()),
                    txtPhone.getText(),
                    Email.of(txtEmail.getText()),
                    txtDateOfBirth.getText(),
                    (EmployeeStatus) choiceStatus.getValue(),
                    expertises
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
    private void setEntityIntoView(){
        txtId.setText(String.valueOf(employee.id()));
        txtName.setText(employee.name());
        txtCpf.setText(employee.cpf().getValue());
        txtEmail.setText(employee.email().getValue());
        txtPhone.setText(employee.phone());
        txtDateOfBirth.setText(employee.dateOfBirth());
        expertiseListView.setItems(FXCollections.observableArrayList(employee.expertises()));
        choiceStatus.setValue(employee.status());
    }

    private void configureViewMode(){
        btnCancel.setText("Close");
        btnSave.setVisible(false);

        txtName.setDisable(true);
        txtCpf.setDisable(true);
        txtEmail.setDisable(true);
        txtPhone.setDisable(true);
        txtDateOfBirth.setDisable(true);
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
            ApplicationView.setRoot("EmployeeManagementUI");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void backToPreviousScene(ActionEvent event) {
        returnToPreviousScene();
    }
}
