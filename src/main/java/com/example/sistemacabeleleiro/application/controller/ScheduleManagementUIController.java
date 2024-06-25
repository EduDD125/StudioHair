package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.WindowLoader;
import com.example.sistemacabeleleiro.application.main.Main;
import com.example.sistemacabeleleiro.domain.entities.schedulling.Scheduling;
import com.example.sistemacabeleleiro.domain.usecases.scheduling.dto.SchedulingOutputDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

import static com.example.sistemacabeleleiro.application.main.Main.findSchedulingUseCase;

public class ScheduleManagementUIController {

    @FXML
    private TableView<SchedulingOutputDTO> tableView;
    @FXML
    private TableColumn<SchedulingOutputDTO, Integer> cClient;
    @FXML
    private TableColumn<SchedulingOutputDTO, Integer> cEmployee;
    @FXML
    private TableColumn<SchedulingOutputDTO, Integer> cRealizationDate;
    @FXML
    private TableColumn<SchedulingOutputDTO, Integer> cService;
    @FXML
    private TableColumn<SchedulingOutputDTO, Integer> cStatus;

    ObservableList<SchedulingOutputDTO> tableData;

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
        cClient.setCellValueFactory(new PropertyValueFactory<>("name"));
        cEmployee.setCellValueFactory(new PropertyValueFactory<>("name"));
        cRealizationDate.setCellValueFactory(new PropertyValueFactory<>("dataRealizacao"));
        cService.setCellValueFactory(new PropertyValueFactory<>("service"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadDataAndShow() {
        List<SchedulingOutputDTO> schedules = findSchedulingUseCase.findAll();

        System.out.println("Here");
        tableData.clear();
        if (schedules != null) tableData.addAll(schedules);
    }

    public void cancelSchedule(ActionEvent actionEvent) {
        SchedulingOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Main.cancelSchedulingUseCase.cancel(selectedItem.id());
            loadDataAndShow();
        }
    }

    public void editSchedule(ActionEvent actionEvent) {
    }

    public void detailSchedule(ActionEvent actionEvent) {
    }

    public void registerSchedule(ActionEvent actionEvent) {
    }

    private void showScheduleInMode (UIMode mode) throws IOException {
        SchedulingOutputDTO selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            WindowLoader.setRoot("ScheduleUI");
            SchedulesController controller = (SchedulesController) WindowLoader.getController();
            controller.setSchedule(selectedItem, mode);
        }
    }

    public void goBackToMainMenu(ActionEvent actionEvent) {
    }

    public void filterSchedules(ActionEvent actionEvent) {
    }
}
