package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.ApplicationView;
import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.entities.client.ClientStatus;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.entities.email.Email;
import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientInputDTO;
import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientUpdateDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClientController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private ChoiceBox choiceStatus;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    private ClientOutputDTO client;


    @FXML
    private void initialize(){
        txtId.setDisable(true);
        choiceStatus.setItems(FXCollections.observableArrayList(ClientStatus.values()));
    }
    public void saveOrUpdate(ActionEvent event) {
        getEntityFromView();
        boolean newClient = Main.findClientUseCase.findOne(client.id()).isEmpty();

        if (newClient){
            ClientInputDTO clientInput = new ClientInputDTO(
                    txtName.getText(),
                    CPF.of(txtCpf.getText()),
                    txtPhone.getText(),
                    Email.of(txtEmail.getText())
            );

            Main.createClientUseCase.insert(clientInput);
            showSuccessMessage("Client created successfully!");
        }else {
            ClientUpdateDTO clientUpdate = new ClientUpdateDTO(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    CPF.of(txtCpf.getText()),
                    txtPhone.getText(),
                    Email.of(txtEmail.getText())
            );

            Main.updateClientUseCase.update(clientUpdate);
            showSuccessMessage("Client updated successfully!");
        }
    }
    private void getEntityFromView(){
        if (client == null){
            int id = -1;
            if (!txtId.getText().isEmpty()){
                id = Integer.valueOf(txtId.getText());
            }
            client = new ClientOutputDTO(
                    id,
                    txtName.getText(),
                    CPF.of(txtCpf.getText()),
                    txtPhone.getText(),
                    Email.of(txtEmail.getText()),
                    (ClientStatus) choiceStatus.getValue()
            );
        }
    }

    public void setClient(ClientOutputDTO client, UIMode mode) {
        if (client == null)
            throw new IllegalArgumentException("Client can not be null.");

        this.client = client;
        setEntityIntoView();

        if (mode == UIMode.VIEW)
            configureViewMode();

    }

    private void setEntityIntoView(){
        txtId.setText(String.valueOf(client.id()));
        txtName.setText(client.name());
        txtCpf.setText(client.cpf().getValue());
        txtEmail.setText(client.email().getValue());
        txtPhone.setText(client.phone());
        choiceStatus.setValue(client.status());
    }

    private void configureViewMode(){
        btnCancel.setText("Close");
        btnSave.setVisible(false);

        txtName.setDisable(true);
        txtCpf.setDisable(true);
        txtEmail.setDisable(true);
        txtPhone.setDisable(true);
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
            ApplicationView.setRoot("ClientManagementUI");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void backToPreviousScene(ActionEvent event) throws IOException {
        returnToPreviousScene();
    }
}
