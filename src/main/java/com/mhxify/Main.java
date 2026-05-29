package com.mhxify;

import com.mhxify.neural.Layer;
import com.mhxify.neural.NetworkTrainer;
import com.mhxify.neural.NeuralNetwork;
import com.mhxify.neural.XORData;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        NeuralNetwork network = new NeuralNetwork();

        network.addLayer(new Layer(2, 4));
        network.addLayer(new Layer(4, 1));

        NetworkTrainer.trainXOR(
                network,
                0.5,
                50000
        );

        System.out.println("--------------------");

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