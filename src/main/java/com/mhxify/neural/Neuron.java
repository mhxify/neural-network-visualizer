package com.mhxify.neural;

import java.util.Random;

public class Neuron {

    private double[] weights;
    private double bias;
    private double output;

    public Neuron(int inputCount) {
        weights = new double[inputCount];
        Random random = new Random();

        for (int i = 0; i < inputCount; i++) {
            weights[i] = random.nextDouble() * 2 - 1; // between -1 and 1
        }

        bias = random.nextDouble() * 2 - 1;
    }

    public double forward(double[] inputs) {
        double sum = bias;

        for (int i = 0; i < inputs.length; i++) {
            double multiplication = inputs[i] * weights[i];

            System.out.println("Step " + i);
            System.out.println("Input: " + inputs[i]);
            System.out.println("Weight: " + weights[i]);
            System.out.println("Input * Weight: " + multiplication);
            System.out.println("Sum before: " + sum);

            sum += multiplication;

            System.out.println("Sum after: " + sum);
        }

        output = sigmoid(sum);
        return output;
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public double getOutput() {
        return output;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setWeight(
            int index,
            double value
    ) {
        weights[index] = value;
    }
}