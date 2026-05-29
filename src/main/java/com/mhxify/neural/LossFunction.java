package com.mhxify.neural;

public class LossFunction {

    public static double mse(
            double expected,
            double predicted
    ) {
        return Math.pow(
                expected - predicted,
                2
        );
    }
}