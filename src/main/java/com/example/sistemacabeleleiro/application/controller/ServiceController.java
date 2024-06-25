package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.ApplicationView;
import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.entities.service.ServiceStatus;
import com.example.sistemacabeleleiro.domain.usecases.service.dto.ServiceInputDTO;
import com.example.sistemacabeleleiro.domain.usecases.service.dto.ServiceOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.service.dto.ServiceUpdateDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ServiceController {
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtSubCategory;
    @FXML
    private TextField txtPrice;
    @FXML
    private ChoiceBox choiceStatus;

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    private ServiceOutputDTO service;

    @FXML
    private void initialize(){
        txtId.setDisable(true);
        choiceStatus.setItems(FXCollections.observableArrayList(ServiceStatus.values()));
    }

    public void saveOrUpdate(ActionEvent event) {
        getEntityFromView();
        boolean newService = Main.findServiceUseCase.findOne(service.id()).isEmpty();

        if (newService){
            ServiceInputDTO serviceInput = new ServiceInputDTO(
                    txtName.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    txtCategory.getText(),
                    txtSubCategory.getText()
            );
            Main.createServiceUseCase.insert(serviceInput);
            showSuccessMessage("Service created successfully!");

        }else{
            ServiceUpdateDTO serviceUpdate = new ServiceUpdateDTO(
              Integer.parseInt(txtId.getText()),
              txtName.getText(),
              txtDescription.getText(),
              Double.parseDouble(txtPrice.getText()),
              txtCategory.getText(),
              txtSubCategory.getText()
            );
            Main.updateServiceUseCase.update(serviceUpdate);
            showSuccessMessage("Service updated successfully!");
        }

    }

    private void getEntityFromView(){
        if (service == null){
            int id = -1;
            if (!txtId.getText().isEmpty()){
                id = Integer.valueOf(txtId.getText());
            }
            service = new ServiceOutputDTO(
                    id,
                    txtName.getText(),
                    txtDescription.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    txtCategory.getText(),
                    txtSubCategory.getText(),
                    (ServiceStatus) choiceStatus.getValue()
            );
        }
    }

    public void setService(ServiceOutputDTO service, UIMode mode) {
        if (service == null)
            throw new IllegalArgumentException("Service can not be null.");

        this.service = service;
        setEntityIntoView();

        if (mode == UIMode.VIEW)
            configureViewMode();
    }

    private void setEntityIntoView(){
        txtId.setText(String.valueOf(service.id()));
        txtName.setText(service.name());
        txtDescription.setText(service.description());
        txtPrice.setText(String.valueOf(service.price()));
        txtCategory.setText(service.category());
        txtSubCategory.setText(service.subcategory());
        choiceStatus.setValue(service.status());
    }

    private void configureViewMode(){
        btnCancel.setText("Close");
        btnSave.setVisible(false);

        txtName.setDisable(true);
        txtDescription.setDisable(true);
        txtPrice.setDisable(true);
        txtCategory.setDisable(true);
        txtSubCategory.setDisable(true);
        choiceStatus.setDisable(true);

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
            ApplicationView.setRoot("ServiceManagementUI");
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void backToPreviousScene(ActionEvent event) {
        returnToPreviousScene();
    }

}
