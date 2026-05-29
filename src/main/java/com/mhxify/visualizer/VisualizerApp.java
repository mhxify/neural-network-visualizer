package com.mhxify.visualizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class VisualizerApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Pane root = new Pane();

        // Positions
        double inputX = 150;
        double hiddenX = 450;
        double outputX = 750;

        double[] inputY = {220, 380};
        double[] hiddenY = {150, 250, 350, 450};
        double[] outputY = {300};

        // Draw connections: input -> hidden
        for (double iy : inputY) {
            for (double hy : hiddenY) {
                root.getChildren().add(createLine(inputX, iy, hiddenX, hy));
            }
        }

        // Draw connections: hidden -> output
        for (double hy : hiddenY) {
            for (double oy : outputY) {
                root.getChildren().add(createLine(hiddenX, hy, outputX, oy));
            }
        }

        // Draw input neurons
        for (double y : inputY) {
            root.getChildren().add(createNeuron(inputX, y));
        }

        // Draw hidden neurons
        for (double y : hiddenY) {
            root.getChildren().add(createNeuron(hiddenX, y));
        }

        // Draw output neuron
        for (double y : outputY) {
            root.getChildren().add(createNeuron(outputX, y));
        }

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Neural Network Visualizer");
        stage.setScene(scene);
        stage.show();
    }

    private Line createLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(2);
        return line;
    }

    private Circle createNeuron(double x, double y) {
        Circle circle = new Circle(x, y, 25);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.DODGERBLUE);
        circle.setStrokeWidth(3);
        return circle;
    }
}