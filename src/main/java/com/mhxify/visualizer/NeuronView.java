package com.mhxify.visualizer;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NeuronView extends StackPane {

    private final Label valueLabel;

    public NeuronView(double x, double y) {

        Circle circle = new Circle(25);
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
    }
}