package com.example.sistemacabeleleiro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowLoader extends Application {
    private static Scene scene;
    private static Object controller;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("MainMenuUI"));
        stage.setTitle("STUDIO HAIR SYSTEM");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WindowLoader.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        return root;
    }

    public static Object getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch();
    }
}
