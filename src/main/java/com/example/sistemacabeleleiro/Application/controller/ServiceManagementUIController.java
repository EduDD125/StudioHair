package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.entities.employee.Employee;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.entities.service.Service;
import com.example.sistemacabeleleiro.domain.entities.service.ServiceStatus;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingOutputDTO;
import com.example.sistemacabeleleiro.domain.usecases.service.dto.ServiceOutputDTO;
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
    private TableView<ServiceOutputDTO> tableView;
    @FXML
    private TableColumn<ServiceOutputDTO, Integer> cName;
    @FXML
    private TableColumn<ServiceOutputDTO, Integer> cDiscription;
    @FXML
    private TableColumn<ServiceOutputDTO, Integer> cPrice;
    @FXML
    private TableColumn<ServiceOutputDTO, Integer> cCategory;
    @FXML
    private TableColumn<ServiceOutputDTO, Integer> cSubCategory;
    @FXML
    private TableColumn<ServiceOutputDTO, Integer> cDiscount;
    @FXML
    private TableColumn<ServiceOutputDTO, Integer> cStatus;

    ObservableList<ServiceOutputDTO> tableData;

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
        List<ServiceOutputDTO> services = Main.findServiceUseCase.findAll();
        tableData.clear();
        tableData.addAll(services);
    }

    public void deleteService(ActionEvent actionEvent) {
        ServiceOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Main.removeServiceUseCase.remove(selectedItem.id());
            loadDataAndShow();
        }
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
