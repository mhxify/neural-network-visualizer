package com.mhxify.neural;

public class GradientDescent {

    public static double updateWeight(
            double weight,
            double gradient,
            double learningRate
    ) {
        return weight - learningRate * gradient;
    }
}