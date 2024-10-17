package ru.olgabelozerova;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;


public class HelloFX extends Application {
    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello, JavaFX!");
        Scene scene = new Scene(label, 400, 300);
        stage.setScene(scene);
        stage.setTitle("JavaFX Application");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

