package com.mhxify.neural;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private final List<Layer> layers;

    public NeuralNetwork() {
        layers = new ArrayList<>();
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public double[] forward(double[] inputs) {
        double[] currentOutputs = inputs;

        for (Layer layer : layers) {
            currentOutputs = layer.forward(currentOutputs);
        }

        return currentOutputs;
    }

    public List<Layer> getLayers() {
        return layers;
    }
}