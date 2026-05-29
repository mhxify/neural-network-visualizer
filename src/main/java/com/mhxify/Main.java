package com.mhxify;

import com.mhxify.neural.Neuron;

public class Main {
    public static void main(String[] args) {
        Neuron neuron = new Neuron(2);

        double[] inputs = {1.0, 0.0};

        double result = neuron.forward(inputs);

        System.out.println("Neuron output = " + result);
    }
}