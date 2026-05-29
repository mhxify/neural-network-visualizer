package com.mhxify.visualizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class VisualizerApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Pane root = new Pane();

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Neural Network Visualizer");

        stage.setScene(scene);
        stage.show();
    }
}