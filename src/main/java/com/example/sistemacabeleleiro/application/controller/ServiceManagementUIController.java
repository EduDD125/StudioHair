package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.ApplicationView;
import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.usecases.service.dto.ServiceOutputDTO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.List;

public class ServiceManagementUIController {

    @FXML
    private TableView<ServiceOutputDTO> tableView;
    @FXML
    private TableColumn<ServiceOutputDTO, Number> cId;
    @FXML
    private TableColumn<ServiceOutputDTO, String> cName;
    @FXML
    private TableColumn<ServiceOutputDTO, String> cDescription;
    @FXML
    private TableColumn<ServiceOutputDTO, Number> cPrice;
    @FXML
    private TableColumn<ServiceOutputDTO, String> cCategory;
    @FXML
    private TableColumn<ServiceOutputDTO, String> cSubCategory;
    @FXML
    private TableColumn<ServiceOutputDTO, String> cStatus;

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
        cId.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().id()));
        cName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().name()));
        cDescription.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().description()));
        cPrice.setCellValueFactory(p -> new SimpleDoubleProperty(p.getValue().price()));
        cCategory.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().category()));
        cSubCategory.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().subcategory()));
        cStatus.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().status().name()));
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

    public void editService(ActionEvent actionEvent) throws IOException{
        showServiceInMode(UIMode.UPDATE);
    }

    public void detailService(ActionEvent actionEvent) throws IOException{
        showServiceInMode(UIMode.VIEW);
    }

    public void registerService(ActionEvent actionEvent) throws IOException{
        ApplicationView.setRoot("ServiceUI");
    }

    private void showServiceInMode(UIMode mode) throws IOException {
        ServiceOutputDTO selectedService = tableView.getSelectionModel().getSelectedItem();
        if (selectedService != null){
            ApplicationView.setRoot("ServiceUI");
            ServiceController controller = (ServiceController) ApplicationView.getController();
            controller.setService(selectedService,mode);
        }
    }
    public void goBackToMainMenu(ActionEvent actionEvent) throws IOException{
        ApplicationView.setRoot("MainMenuUI");
    }

    public void findServices(ActionEvent event) {
    }
}
