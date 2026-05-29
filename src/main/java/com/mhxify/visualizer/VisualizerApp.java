package com.mhxify.visualizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class VisualizerApp extends Application {

    private final List<NeuronView> inputViews = new ArrayList<>();
    private final List<NeuronView> hiddenViews = new ArrayList<>();
    private final List<NeuronView> outputViews = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Pane root = new Pane();

        double inputX = 150;
        double hiddenX = 450;
        double outputX = 750;

        double[] inputY = {220, 380};
        double[] hiddenY = {150, 250, 350, 450};
        double[] outputY = {300};

        for (double iy : inputY) {
            for (double hy : hiddenY) {
                root.getChildren().add(createLine(inputX, iy, hiddenX, hy));
            }
        }

        for (double hy : hiddenY) {
            for (double oy : outputY) {
                root.getChildren().add(createLine(hiddenX, hy, outputX, oy));
            }
        }

        for (double y : inputY) {
            NeuronView view = new NeuronView(inputX, y);
            inputViews.add(view);
            root.getChildren().add(view);
        }

        for (double y : hiddenY) {
            NeuronView view = new NeuronView(hiddenX, y);
            hiddenViews.add(view);
            root.getChildren().add(view);
        }

        for (double y : outputY) {
            NeuronView view = new NeuronView(outputX, y);
            outputViews.add(view);
            root.getChildren().add(view);
        }

        // Test values for now
        inputViews.get(0).setValue(0.0);
        inputViews.get(1).setValue(1.0);

        hiddenViews.get(0).setValue(0.72);
        hiddenViews.get(1).setValue(0.15);
        hiddenViews.get(2).setValue(0.88);
        hiddenViews.get(3).setValue(0.32);

        outputViews.get(0).setValue(0.99);

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
}