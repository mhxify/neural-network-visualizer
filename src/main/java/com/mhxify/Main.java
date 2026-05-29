package com.mhxify;

import com.mhxify.neural.Layer;
import com.mhxify.neural.LossFunction;
import com.mhxify.neural.NeuralNetwork;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // Create neural network
        NeuralNetwork network = new NeuralNetwork();

        // Hidden layer
        network.addLayer(new Layer(2, 3));

        // Output layer
        network.addLayer(new Layer(3, 1));

        // Inputs
        double[] inputs = {1.0, 0.0};

        // Expected output
        double expected = 1.0;

        // Forward propagation
        double[] outputs = network.forward(inputs);

        // Network prediction
        double predicted = outputs[0];

        // Calculate loss
        double loss = LossFunction.mse(
                expected,
                predicted
        );

        // Display results
        System.out.println("Inputs      = " + Arrays.toString(inputs));
        System.out.println("Expected    = " + expected);
        System.out.println("Predicted   = " + predicted);
        System.out.println("Loss        = " + loss);
    }
}