package com.mhxify.visualizer;

import com.mhxify.neural.Layer;
import com.mhxify.neural.NetworkTrainer;
import com.mhxify.neural.NeuralNetwork;
import com.mhxify.neural.XORData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import com.mhxify.neural.TrainingHistory;
import java.util.List;

public class VisualizerApp extends Application {

    private final List<NeuronView> inputViews = new ArrayList<>();
    private final List<NeuronView> hiddenViews = new ArrayList<>();
    private final List<NeuronView> outputViews = new ArrayList<>();

    private NeuralNetwork network;
    private int currentSampleIndex = 0;

    private Label inputLabel;
    private Label expectedLabel;
    private Label predictedLabel;
    private Label resultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        network = new NeuralNetwork();

        network.addLayer(new Layer(2, 4));
        network.addLayer(new Layer(4, 1));

        TrainingHistory history =
                NetworkTrainer.trainXORWithHistory(
                        network,
                        0.5,
                        50000
                );

        System.out.println("Loss points: " + history.getLosses().size());

        Pane root = new Pane();

        root.getChildren().add(
                createLossChart(history)
        );

        double inputX = 150;
        double hiddenX = 450;
        double outputX = 750;

        double[] inputY = {220, 380};
        double[] hiddenY = {120, 240, 360, 480};
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

        createInfoLabels(root);
        createNextButton(root);

        updateNetworkValues();

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Neural Network Visualizer");
        stage.setScene(scene);
        stage.show();
    }

    private void createInfoLabels(Pane root) {
        inputLabel = new Label();
        inputLabel.setLayoutX(330);
        inputLabel.setLayoutY(20);

        expectedLabel = new Label();
        expectedLabel.setLayoutX(330);
        expectedLabel.setLayoutY(45);

        predictedLabel = new Label();
        predictedLabel.setLayoutX(330);
        predictedLabel.setLayoutY(70);

        resultLabel = new Label();
        resultLabel.setLayoutX(330);
        resultLabel.setLayoutY(95);

        root.getChildren().addAll(
                inputLabel,
                expectedLabel,
                predictedLabel,
                resultLabel
        );
    }

    private void createNextButton(Pane root) {
        Button nextButton = new Button("Next XOR Sample");
        nextButton.setLayoutX(360);
        nextButton.setLayoutY(540);

        nextButton.setOnAction(event -> {
            currentSampleIndex++;

            if (currentSampleIndex >= XORData.INPUTS.length) {
                currentSampleIndex = 0;
            }

            updateNetworkValues();
        });

        root.getChildren().add(nextButton);
    }

    private void updateNetworkValues() {

        double[] input = XORData.INPUTS[currentSampleIndex];
        double[] output = network.forward(input);

        inputViews.get(0).setValue(input[0]);
        inputViews.get(1).setValue(input[1]);

        Layer hiddenLayer = network.getLayers().get(0);

        for (int i = 0; i < hiddenLayer.getNeurons().size(); i++) {
            hiddenViews.get(i).setValue(
                    hiddenLayer.getNeurons().get(i).getOutput()
            );
        }

        double expected = XORData.OUTPUTS[currentSampleIndex][0];
        double predicted = output[0];

        outputViews.get(0).setValue(predicted);

        inputLabel.setText("Input: [" + input[0] + ", " + input[1] + "]");
        expectedLabel.setText("Expected: " + expected);
        predictedLabel.setText(String.format("Predicted: %.4f", predicted));

        int predictedClass = predicted >= 0.5 ? 1 : 0;
        int expectedClass = expected >= 0.5 ? 1 : 0;

        if (predictedClass == expectedClass) {
            resultLabel.setText("Result: Correct ✅");
        } else {
            resultLabel.setText("Result: Wrong ❌");
        }
    }

    private Line createLine(
            double startX,
            double startY,
            double endX,
            double endY
    ) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(2);
        return line;
    }

    private LineChart<Number, Number> createLossChart(TrainingHistory history) {

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Training Step");
        yAxis.setLabel("Loss");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Loss During Training");

        chart.setLayoutX(20);
        chart.setLayoutY(20);
        chart.setPrefSize(260, 180);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("MSE Loss");

        for (int i = 0; i < history.getLosses().size(); i++) {
            series.getData().add(
                    new XYChart.Data<>(
                            i,
                            history.getLosses().get(i)
                    )
            );
        }

        chart.getData().add(series);

        return chart;
    }
}