package com.example.sistemacabeleleiro.application.controller;

import com.example.sistemacabeleleiro.ApplicationView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class MainMenuUIController {

    @FXML
    private ImageView imgLogo;

    @FXML
    private void initialize(){
        Image image = new Image(getClass().getResource("/com/example/sistemacabeleleiro/logo/Logo.png").toExternalForm());
        imgLogo.setImage(image);
    }
    public void goToSchedulesUIScene(ActionEvent actionEvent) throws IOException{
        ApplicationView.setRoot("ScheduleManagementUI");
    }

    public void goToClientsUIScene(ActionEvent actionEvent) throws IOException {
        ApplicationView.setRoot("ClientManagementUI");

    }

    public void goToServicesUIScene(ActionEvent actionEvent) throws IOException{
        ApplicationView.setRoot("ServiceManagementUI");
    }

    public void goToEmployeesUIScene(ActionEvent actionEvent) throws IOException{
        ApplicationView.setRoot("EmployeeManagementUI");
    }
}
