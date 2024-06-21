package com.example.sistemacabeleleiro.Application.controller;

import com.example.sistemacabeleleiro.Domain.Entities.Schedulling.Scheduling;
import com.example.sistemacabeleleiro.Domain.Entities.Service.Service;
import com.example.sistemacabeleleiro.Domain.UseCases.Scheduling.FindSchedulingUseCase;
import com.example.sistemacabeleleiro.Domain.UseCases.Service.FindServiceUseCase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ServiceManagementUIController {

    @FXML
    private TableView<Service> tableView;
    @FXML
    private TableColumn<Scheduling, Integer> cName;
    @FXML
    private TableColumn<Scheduling, Integer> cDiscription;
    @FXML
    private TableColumn<Scheduling, Integer> cPrice;
    @FXML
    private TableColumn<Scheduling, Integer> cCategory;
    @FXML
    private TableColumn<Scheduling, Integer> cSubCategory;
    @FXML
    private TableColumn<Scheduling, Integer> cDiscount;
    @FXML
    private TableColumn<Scheduling, Integer> cStatus;

    ObservableList<Service> tableData;

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
        cDiscription.setCellValueFactory(new PropertyValueFactory<>("description"));
        cPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        cCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        cSubCategory.setCellValueFactory(new PropertyValueFactory<>("subCategory"));
        cDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataAndShow() {
        List<Service> services = FindServiceUseCase.findAll();
        tableData.clear();
        tableData.addAll(services);
    }

    public void deleteService(ActionEvent actionEvent) {
    }

    public void editService(ActionEvent actionEvent) {
    }

    public void detailService(ActionEvent actionEvent) {
    }

    public void registerService(ActionEvent actionEvent) {
    }

    public void goBackToMainMenu(ActionEvent actionEvent) {
    }
}
