package com.example.sistemacabeleleiro.Application.controller;

import com.example.sistemacabeleleiro.Domain.Entities.Client.Client;
import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.UseCases.Client.FindClientUseCase;
import com.example.sistemacabeleleiro.Domain.UseCases.Client.RemoveClientUseCase;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.FindSchedulingUseCase;
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
    private TableView<Client> tableView;
    @FXML
    private TableColumn<Client, Integer> cName;
    @FXML
    private TableColumn<Client, Integer> cCPF;
    @FXML
    private TableColumn<Client, Integer> cPhone;
    @FXML
    private TableColumn<Client, Integer> cEmail;
    @FXML
    private TableColumn<Client, Integer> cStatus;

    ObservableList<Client> tableData;

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
        List<Client> clients = FindClientUseCase.findAll();
        tableData.clear();
        tableData.addAll(clients);
    }

    public void deleteClient(ActionEvent actionEvent) {
        Client selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            RemoveClientUseCase.remove(selectedItem);
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
