package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.WindowLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainMenuUIController {

    @FXML
    private Button schedulesButton;
    @FXML
    private Button clientsButton;
    @FXML
    private Button servicesButton;
    @FXML
    private Button employeesButton;
    public void goToSchedulesUIScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ScheduleManagementUI");
        ScheduleManagementUIController controller = (ScheduleManagementUIController)  WindowLoader.getController();
    }

    public void goToClientsUIScene(ActionEvent actionEvent) {
    }

    public void goToServicesUIScene(ActionEvent actionEvent) {
    }

    public void goToEmployeesUIScene(ActionEvent actionEvent) {
    }
}
