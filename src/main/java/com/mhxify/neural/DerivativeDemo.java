package com.mhxify.neural;

public class DerivativeDemo {

    public static double loss(double weight) {
        return weight * weight;
    }

    public static double derivative(double weight) {
        return 2 * weight;
    }
}