package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.ApplicationView;
import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.entities.cpf.CPF;
import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientOutputDTO;
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

public class ClientManagementUIController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCpf;
    @FXML
    private TableView<ClientOutputDTO> tableView;

    @FXML
    private TableColumn<ClientOutputDTO, Number> cId;
    @FXML
    private TableColumn<ClientOutputDTO, String> cName;
    @FXML
    private TableColumn<ClientOutputDTO, String> cCPF;
    @FXML
    private TableColumn<ClientOutputDTO, String> cPhone;
    @FXML
    private TableColumn<ClientOutputDTO, String> cEmail;
    @FXML
    private TableColumn<ClientOutputDTO, String> cStatus;

    private ObservableList<ClientOutputDTO> tableData;

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
        cId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().id()));
        cName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().name()));
        cCPF.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().cpf().getValue()));
        cPhone.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().phone()));
        cEmail.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().email().getValue()));
        cStatus.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().status().name()));
    }

    private void loadDataAndShow() {
        List<ClientOutputDTO> clients = Main.findClientUseCase.findAll();
        tableData.clear();
        tableData.addAll(clients);
    }

    public void deleteClient(ActionEvent actionEvent) {
        ClientOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Main.removeClientUseCase.remove(selectedItem.id());
            loadDataAndShow();
        }
    }

    public void editClient(ActionEvent actionEvent) throws IOException{
        showUserInMode(UIMode.UPDATE);
    }

    public void detailClient(ActionEvent actionEvent) throws IOException{
        showUserInMode(UIMode.VIEW);
    }

    public void registerClient(ActionEvent actionEvent) throws IOException {
        ApplicationView.setRoot("ClientUI");

    }
    public void inactivateClient(ActionEvent event) {
        ClientOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Main.inactivateClientUseCase.inactivate(selectedItem.id());
            loadDataAndShow();
        }
    }

    public void activateClient(ActionEvent event) {
        ClientOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Main.activateClientUseCase.activate(selectedItem.id());
            loadDataAndShow();
        }
    }

    public void findClients(ActionEvent event) throws IOException{
        ClientOutputDTO client;
        if (!txtName.getText().isEmpty()){
            client = Main.findClientUseCase.findOneByName(txtName.getText())
                    .orElseThrow(()-> new EntityNotFoundException("Client not found"));
        }else{
            if (!txtCpf.getText().isEmpty()){
                client = Main.findClientUseCase.findOneByCPF(CPF.of(txtCpf.getText()))
                        .orElseThrow(()-> new EntityNotFoundException("Client not found"));
            }else{
                throw new RuntimeException("Choose at least one filter");
            }
        }
        ApplicationView.setRoot("ClientUI");
        ClientController controller = (ClientController) ApplicationView.getController();
        controller.setClient(client,UIMode.VIEW);
    }

    private void showUserInMode(UIMode mode) throws IOException {
        ClientOutputDTO selectedClient = tableView.getSelectionModel().getSelectedItem();
        if (selectedClient != null){
            ApplicationView.setRoot("ClientUI");
            ClientController controller = (ClientController) ApplicationView.getController();
            controller.setClient(selectedClient,mode);
        }
    }

    public void goBackToMainMenu(ActionEvent actionEvent) throws IOException{
        ApplicationView.setRoot("MainMenuUI");
    }
}
