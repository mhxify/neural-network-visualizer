package com.mhxify;

import com.mhxify.neural.Layer;
import com.mhxify.neural.NeuralNetwork;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        NeuralNetwork network = new NeuralNetwork();

        network.addLayer(new Layer(2, 3)); // 2 inputs → 3 outputs
        network.addLayer(new Layer(3, 1)); // 3 inputs → 1 output

        double[] inputs = {1.0, 0.0};

        double[] result = network.forward(inputs);

        System.out.println("Network output = " + Arrays.toString(result));
    }
}