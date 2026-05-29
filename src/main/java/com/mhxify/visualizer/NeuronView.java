package com.mhxify.visualizer;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NeuronView extends StackPane {

    private final Label valueLabel;
    private final Circle circle;

    public NeuronView(double x, double y) {

        circle = new Circle(25);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.DODGERBLUE);
        circle.setStrokeWidth(3);

        valueLabel = new Label("0.00");

        getChildren().addAll(circle, valueLabel);

        setLayoutX(x - 25);
        setLayoutY(y - 25);
    }

    public void setValue(double value) {
        valueLabel.setText(String.format("%.2f", value));
        updateColor(value);
    }

    private void updateColor(double value) {
        if (value < 0.3) {
            circle.setFill(Color.LIGHTGRAY);
        } else if (value < 0.7) {
            circle.setFill(Color.LIGHTBLUE);
        } else {
            circle.setFill(Color.LIGHTGREEN);
        }
    }
}