package com.mhxify.visualizer;

import com.mhxify.neural.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class VisualizerApp extends Application {

    // ── Layout constants ──────────────────────────────────────────────────────
    private static final double CANVAS_W = 960;
    private static final double CANVAS_H = 620;

    private static final double INPUT_X  = 200;
    private static final double HIDDEN_X = 480;
    private static final double OUTPUT_X = 760;

    private static final double[] INPUT_Y  = {220, 400};
    private static final double[] HIDDEN_Y = {120, 240, 370, 490};
    private static final double[] OUTPUT_Y = {310};

    // ── Neural-network state ──────────────────────────────────────────────────
    private NeuralNetwork   network;
    private TrainingHistory history;
    private TrainingSession trainingSession;
    private Timeline        trainingTimeline;
    private int             currentSampleIndex = 0;

    // ── Visual nodes ──────────────────────────────────────────────────────────
    private final List<NeuronView>     inputViews                = new ArrayList<>();
    private final List<NeuronView>     hiddenViews               = new ArrayList<>();
    private final List<NeuronView>     outputViews               = new ArrayList<>();
    private final List<ConnectionView> inputToHiddenConnections  = new ArrayList<>();
    private final List<ConnectionView> hiddenToOutputConnections = new ArrayList<>();

    // ── Info labels ───────────────────────────────────────────────────────────
    private Label inputLabel;
    private Label expectedLabel;
    private Label predictedLabel;
    private Label resultLabel;
    private Label accuracyLabel;
    private Label sampleCounterLabel;
    private Label epochLabel;
    private Label liveLossLabel;

    // ── Controls ──────────────────────────────────────────────────────────────
    private CheckBox showWeightsCheck;

    // ── Live loss chart series (kept as field so we can append to it) ─────────
    private XYChart.Series<Number, Number> lossSeries;
    private int lossSeriesCount = 0;

    // =========================================================================
    public static void main(String[] args) { launch(args); }

    // =========================================================================
    @Override
    public void start(Stage stage) {

        // ── 1. Build network & training session ───────────────────────────────
        network         = new NeuralNetwork();
        network.addLayer(new Layer(2, 4));
        network.addLayer(new Layer(4, 1));

        history         = new TrainingHistory();
        trainingSession = new TrainingSession(network, 0.5);

        // ── 2. Root pane ──────────────────────────────────────────────────────
        Pane canvas = new Pane();
        canvas.setPrefSize(CANVAS_W, CANVAS_H);
        canvas.setStyle("-fx-background-color: #1a1d27;");

        // ── 3. Background grid ────────────────────────────────────────────────
        addBackgroundGrid(canvas);

        // ── 4. Layer title labels ─────────────────────────────────────────────
        addLayerLabels(canvas);

        // ── 5. Connections ────────────────────────────────────────────────────
        for (double iy : INPUT_Y) {
            for (double hy : HIDDEN_Y) {
                ConnectionView c = new ConnectionView(INPUT_X, iy, HIDDEN_X, hy);
                inputToHiddenConnections.add(c);
                canvas.getChildren().add(c);
            }
        }
        for (double hy : HIDDEN_Y) {
            for (double oy : OUTPUT_Y) {
                ConnectionView c = new ConnectionView(HIDDEN_X, hy, OUTPUT_X, oy);
                hiddenToOutputConnections.add(c);
                canvas.getChildren().add(c);
            }
        }

        // ── 6. Neuron circles ─────────────────────────────────────────────────
        for (double y : INPUT_Y)  { NeuronView v = new NeuronView(INPUT_X,  y); inputViews .add(v); canvas.getChildren().add(v); }
        for (double y : HIDDEN_Y) { NeuronView v = new NeuronView(HIDDEN_X, y); hiddenViews.add(v); canvas.getChildren().add(v); }
        for (double y : OUTPUT_Y) { NeuronView v = new NeuronView(OUTPUT_X, y); outputViews.add(v); canvas.getChildren().add(v); }

        // ── 7. Loss chart ─────────────────────────────────────────────────────
        canvas.getChildren().add(buildLossChart());

        // ── 8. Info panel ─────────────────────────────────────────────────────
        canvas.getChildren().add(buildInfoPanel());

        // ── 9. Bottom controls ────────────────────────────────────────────────
        canvas.getChildren().add(buildControlBar());

        // ── 10. Initial render ────────────────────────────────────────────────
        updateNetworkValues();

        // ── 11. Scene ─────────────────────────────────────────────────────────
        Scene scene = new Scene(canvas, CANVAS_W, CANVAS_H);
        stage.setTitle("Neural Network Visualizer — XOR");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // =========================================================================
    // Background decorative grid
    // =========================================================================
    private void addBackgroundGrid(Pane canvas) {
        for (int x = 0; x <= CANVAS_W; x += 40) {
            Line vl = new Line(x, 0, x, CANVAS_H);
            vl.setStroke(Color.web("#ffffff08"));
            canvas.getChildren().add(vl);
        }
        for (int y = 0; y <= CANVAS_H; y += 40) {
            Line hl = new Line(0, y, CANVAS_W, y);
            hl.setStroke(Color.web("#ffffff08"));
            canvas.getChildren().add(hl);
        }
    }

    // =========================================================================
    // Layer title labels
    // =========================================================================
    private void addLayerLabels(Pane canvas) {
        addCenteredLabel(canvas, "INPUT",  INPUT_X,  60, "#64b5f6");
        addCenteredLabel(canvas, "HIDDEN", HIDDEN_X, 60, "#81c784");
        addCenteredLabel(canvas, "OUTPUT", OUTPUT_X, 60, "#ffb74d");
    }

    private void addCenteredLabel(Pane pane, String text, double cx, double y, String hex) {
        Label l = new Label(text);
        l.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 11px;"
                + " -fx-font-weight: bold; -fx-text-fill: " + hex + ";");
        l.setLayoutX(cx - 30);
        l.setLayoutY(y);
        pane.getChildren().add(l);
    }

    // =========================================================================
    // Loss chart — series stored as field so live training can append to it
    // =========================================================================
    private LineChart<Number, Number> buildLossChart() {

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("×10 epochs");
        yAxis.setLabel("MSE");
        xAxis.setTickLabelFill(Color.web("#aaaaaa"));
        yAxis.setTickLabelFill(Color.web("#aaaaaa"));

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Training Loss");
        chart.setLayoutX(14);
        chart.setLayoutY(14);
        chart.setPrefSize(290, 200);
        chart.setCreateSymbols(false);
        chart.setLegendVisible(false);
        chart.setAnimated(false);   // must be false for live updates
        chart.setStyle(
                "-fx-background-color: rgba(255,255,255,0.04);"
                        + "-fx-background-radius: 10;"
                        + "-fx-border-color: rgba(255,255,255,0.10);"
                        + "-fx-border-radius: 10;"
                        + "-fx-text-fill: #cccccc;"
        );

        lossSeries = new XYChart.Series<>();
        chart.getData().add(lossSeries);
        return chart;
    }

    // =========================================================================
    // Info panel (right side)
    // =========================================================================
    private VBox buildInfoPanel() {

        VBox panel = new VBox(10);
        panel.setPrefWidth(210);
        panel.setLayoutX(CANVAS_W - 228);
        panel.setLayoutY(14);
        panel.setPadding(new Insets(14));
        panel.setStyle(
                "-fx-background-color: rgba(255,255,255,0.05);"
                        + "-fx-background-radius: 10;"
                        + "-fx-border-color: rgba(255,255,255,0.12);"
                        + "-fx-border-radius: 10;"
        );

        Label title        = styledLabel("SAMPLE INFO",   "#ffffff", 11, true);
        inputLabel         = styledLabel("Input: —",      "#aaaaaa", 13, false);
        expectedLabel      = styledLabel("Expected: —",   "#aaaaaa", 13, false);
        predictedLabel     = styledLabel("Predicted: —",  "#aaaaaa", 13, false);
        resultLabel        = styledLabel("",              "#ffffff", 14, true);
        accuracyLabel      = styledLabel("",              "#8888aa", 12, false);
        sampleCounterLabel = styledLabel("Sample 1 / 4", "#666688", 11, false);
        epochLabel         = styledLabel("Epoch: 0",      "#8888aa", 12, false);
        liveLossLabel      = styledLabel("Loss: —",       "#8888aa", 12, false);

        panel.getChildren().addAll(
                title, new Separator(),
                inputLabel, expectedLabel, predictedLabel,
                resultLabel, accuracyLabel, sampleCounterLabel,
                new Separator(),
                epochLabel, liveLossLabel
        );
        return panel;
    }

    private Label styledLabel(String text, String color, int size, boolean bold) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: " + color + "; -fx-font-size: " + size + "px;"
                + (bold ? " -fx-font-weight: bold;" : "")
                + " -fx-font-family: 'Courier New';");
        return l;
    }

    // =========================================================================
    // Bottom control bar
    // =========================================================================
    private HBox buildControlBar() {

        Button prevBtn  = makeButton("◀  Prev",             "#2d3a55", "#aaaacc");
        Button nextBtn  = makeButton("▶  Next XOR Sample",  "#3d5a99", "#ffffff");
        Button trainBtn = makeButton("▶  Train",            "#2e6b3e", "#aaffbb");
        Button pauseBtn = makeButton("⏸  Pause",            "#6b4a2e", "#ffddaa");
        Button stepBtn  = makeButton("⏭  One Epoch",        "#2d3a55", "#aaaacc");

        prevBtn .setOnAction(e -> {
            currentSampleIndex = (currentSampleIndex - 1 + XORData.INPUTS.length) % XORData.INPUTS.length;
            updateNetworkValues();
        });
        nextBtn .setOnAction(e -> {
            currentSampleIndex = (currentSampleIndex + 1) % XORData.INPUTS.length;
            updateNetworkValues();
        });
        trainBtn.setOnAction(e -> startLiveTraining());
        pauseBtn.setOnAction(e -> pauseLiveTraining());
        stepBtn .setOnAction(e -> {
            trainingSession.trainOneEpoch();
            appendLossPoint(trainingSession.getLastLoss());
            updateNetworkValues();
        });

        showWeightsCheck = new CheckBox("Show weights");
        showWeightsCheck.setSelected(true);
        showWeightsCheck.setStyle(
                "-fx-text-fill: #aaaaaa; -fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        showWeightsCheck.selectedProperty().addListener((obs, old, val) -> {
            inputToHiddenConnections .forEach(c -> c.setLabelVisible(val));
            hiddenToOutputConnections.forEach(c -> c.setLabelVisible(val));
        });

        HBox bar = new HBox(12, prevBtn, nextBtn, trainBtn, pauseBtn, stepBtn, showWeightsCheck);
        bar.setAlignment(Pos.CENTER);
        bar.setPrefWidth(CANVAS_W);
        bar.setLayoutX(0);
        bar.setLayoutY(CANVAS_H - 56);
        bar.setPadding(new Insets(10));
        bar.setStyle("-fx-background-color: rgba(0,0,0,0.35);");
        return bar;
    }

    private Button makeButton(String text, String bg, String fg) {
        Button b = new Button(text);
        String base = "-fx-background-color: " + bg + "; -fx-text-fill: " + fg + ";"
                + " -fx-font-family: 'Courier New'; -fx-font-size: 12px;"
                + " -fx-background-radius: 6; -fx-padding: 7 14 7 14; -fx-cursor: hand;";
        b.setStyle(base);
        b.setOnMouseEntered(e -> b.setOpacity(0.8));
        b.setOnMouseExited (e -> b.setOpacity(1.0));
        return b;
    }

    // =========================================================================
    // Live training
    // =========================================================================
    private void startLiveTraining() {
        if (trainingTimeline != null && trainingTimeline.getStatus() == Animation.Status.RUNNING) return;

        trainingTimeline = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            for (int i = 0; i < 10; i++) trainingSession.trainOneEpoch();
            appendLossPoint(trainingSession.getLastLoss());
            updateNetworkValues();
        }));
        trainingTimeline.setCycleCount(Animation.INDEFINITE);
        trainingTimeline.play();
    }

    private void pauseLiveTraining() {
        if (trainingTimeline != null) trainingTimeline.pause();
    }

    // Append a point to the live loss chart; cap at 500 points to avoid slowdown
    private void appendLossPoint(double loss) {
        lossSeriesCount++;
        lossSeries.getData().add(new XYChart.Data<>(lossSeriesCount, loss));
        if (lossSeries.getData().size() > 500) {
            lossSeries.getData().remove(0);
        }
    }

    // =========================================================================
    // Core render update
    // =========================================================================
    private void updateNetworkValues() {

        double[] input   = XORData.INPUTS[currentSampleIndex];
        double[] output  = network.forward(input);
        double predicted = output[0];
        double expected  = XORData.OUTPUTS[currentSampleIndex][0];

        // Neuron activations
        inputViews.get(0).setValue(input[0]);
        inputViews.get(1).setValue(input[1]);

        Layer hiddenLayer = network.getLayers().get(0);
        for (int i = 0; i < hiddenLayer.getNeurons().size(); i++) {
            hiddenViews.get(i).setValue(hiddenLayer.getNeurons().get(i).getOutput());
        }
        outputViews.get(0).setValue(predicted);

        pulseAll();
        updateConnectionWeights();

        // Info panel text
        inputLabel    .setText("Input:     [" + (int) input[0] + ", " + (int) input[1] + "]");
        expectedLabel .setText("Expected:  " + (int) expected);
        predictedLabel.setText(String.format("Predicted: %.4f", predicted));

        boolean correct = (predicted >= 0.5 ? 1 : 0) == (int) expected;
        resultLabel.setText(correct ? "✅  CORRECT" : "❌  WRONG");
        resultLabel.setStyle(resultLabel.getStyle()
                .replaceAll("-fx-text-fill: [^;]+;",
                        "-fx-text-fill: " + (correct ? "#81c784" : "#e57373") + ";"));

        int correctCount = 0;
        for (int s = 0; s < XORData.INPUTS.length; s++) {
            double p = network.forward(XORData.INPUTS[s])[0];
            if ((p >= 0.5 ? 1 : 0) == (int) XORData.OUTPUTS[s][0]) correctCount++;
        }
        accuracyLabel     .setText("Accuracy: " + correctCount + " / " + XORData.INPUTS.length);
        sampleCounterLabel.setText("Sample " + (currentSampleIndex + 1) + " / " + XORData.INPUTS.length);
        epochLabel        .setText("Epoch: " + trainingSession.getCurrentEpoch());
        liveLossLabel     .setText(String.format("Loss: %.6f", trainingSession.getLastLoss()));
    }

    // =========================================================================
    // Connection weight update (single clean loop — no duplicate pass)
    // =========================================================================
    private void updateConnectionWeights() {

        Layer hiddenLayer = network.getLayers().get(0);
        Layer outputLayer = network.getLayers().get(1);

        // Connections were added: for each INPUT_Y[i] → for each HIDDEN_Y[h]
        // So connection index = i * HIDDEN_Y.length + h
        int idx = 0;
        for (int i = 0; i < INPUT_Y.length; i++) {
            for (int h = 0; h < HIDDEN_Y.length; h++) {
                double w = hiddenLayer.getNeurons().get(h).getWeights()[i];
                inputToHiddenConnections.get(idx++).setWeight(w);
            }
        }

        // Hidden → Output
        Neuron outputNeuron = outputLayer.getNeurons().get(0);
        for (int h = 0; h < hiddenToOutputConnections.size(); h++) {
            hiddenToOutputConnections.get(h).setWeight(outputNeuron.getWeights()[h]);
        }
    }

    // =========================================================================
    // Pulse animation
    // =========================================================================
    private void pulseAll() {
        List<NeuronView> all = new ArrayList<>();
        all.addAll(inputViews);
        all.addAll(hiddenViews);
        all.addAll(outputViews);

        for (int i = 0; i < all.size(); i++) {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), all.get(i));
            st.setFromX(1.0); st.setFromY(1.0);
            st.setToX(1.15);  st.setToY(1.15);
            st.setAutoReverse(true);
            st.setCycleCount(2);
            st.setDelay(Duration.millis(i * 25L));
            st.play();
        }
    }

    // =========================================================================
    // Separator helper
    // =========================================================================
    private static class Separator extends Region {
        Separator() {
            setPrefHeight(1);
            setMaxWidth(Double.MAX_VALUE);
            setStyle("-fx-background-color: rgba(255,255,255,0.12);");
        }
    }
}