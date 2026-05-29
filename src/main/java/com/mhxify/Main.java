package com.mhxify;

import com.mhxify.neural.Layer;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Layer layer = new Layer(2, 3);

        double[] inputs = {1.0, 0.0};

        double[] outputs = layer.forward(inputs);

        System.out.println("Layer outputs = " + Arrays.toString(outputs));
    }
}