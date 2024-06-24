package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.usecases.client.dto.ClientOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.client.usecases.FindClientUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ClientManagementUIController {

    @FXML
    private TableView<ClientOutputDTO> tableView;
    @FXML
    private TableColumn<ClientOutputDTO, Integer> cName;
    @FXML
    private TableColumn<ClientOutputDTO, Integer> cCPF;
    @FXML
    private TableColumn<ClientOutputDTO, Integer> cPhone;
    @FXML
    private TableColumn<ClientOutputDTO, Integer> cEmail;
    @FXML
    private TableColumn<ClientOutputDTO, Integer> cStatus;

    ObservableList<ClientOutputDTO> tableData;

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
        cPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
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

    public void editClient(ActionEvent actionEvent) {
    }

    public void detailClient(ActionEvent actionEvent) {
    }

    public void registerClient(ActionEvent actionEvent) {
    }

    public void goBackToMainMenu(ActionEvent actionEvent) {
    }
}
