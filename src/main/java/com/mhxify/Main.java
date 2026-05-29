package com.mhxify;

import com.mhxify.neural.Layer;
import com.mhxify.neural.NeuralNetwork;
import com.mhxify.neural.XORData;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        NeuralNetwork network = new NeuralNetwork();

        network.addLayer(new Layer(2, 2)); // hidden layer
        network.addLayer(new Layer(2, 1)); // output layer

        for (int i = 0; i < XORData.INPUTS.length; i++) {

            double[] prediction = network.forward(XORData.INPUTS[i]);

            System.out.println(
                    Arrays.toString(XORData.INPUTS[i])
                            + " -> predicted: "
                            + Arrays.toString(prediction)
                            + " expected: "
                            + Arrays.toString(XORData.OUTPUTS[i])
            );
        }
    }
}