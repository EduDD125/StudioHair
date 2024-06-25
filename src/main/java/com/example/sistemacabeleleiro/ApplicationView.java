package com.example.sistemacabeleleiro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationView extends Application {

    private static Scene scene;
    private static Object controller;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = loadFXML("MainMenuUI");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException{
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationView.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        controller = fxmlLoader.getController();
        return parent;
    }

    public static void main(String[] args) {
        launch();
    }

    public static Object getController() {
        return controller;
    }
}