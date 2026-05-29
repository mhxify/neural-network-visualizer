package com.mhxify;

import com.mhxify.neural.Neuron;
import com.mhxify.neural.SingleNeuronTrainer;

public class Main {

    public static void main(String[] args) {

        // Create one neuron with one input
        Neuron neuron = new Neuron(1);

        // Training data
        double[] inputs = {1.0};

        // We want the neuron to output 1.0
        double expected = 1.0;

        // Train the neuron
        SingleNeuronTrainer.train(
                neuron,
                inputs,
                expected,
                0.1,   // learning rate
                1000   // epochs
        );

        // Final test
        double finalPrediction = neuron.forward(inputs);

        System.out.println("--------------------");
        System.out.println("Final Prediction = " + finalPrediction);
        System.out.println("Expected         = " + expected);
    }
}