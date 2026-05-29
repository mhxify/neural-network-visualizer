package com.mhxify.visualizer;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ConnectionView extends Group {

    private final Line line;
    private final Label weightLabel;

    public ConnectionView(
            double startX,
            double startY,
            double endX,
            double endY
    ) {
        line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(2);

        weightLabel = new Label("0.00");

        double labelX = (startX + endX) / 2;
        double labelY = (startY + endY) / 2;

        weightLabel.setLayoutX(labelX);
        weightLabel.setLayoutY(labelY);

        getChildren().addAll(line, weightLabel);
    }

    public void setWeight(double weight) {
        weightLabel.setText(String.format("%.2f", weight));

        if (weight < 0) {
            line.setStroke(Color.RED);
        } else {
            line.setStroke(Color.GREEN);
        }

        line.setStrokeWidth(Math.min(5, Math.abs(weight) * 2));
    }

    public void setLabelVisible(boolean visible) {
        weightLabel.setVisible(visible);
    }
}