package com.mhxify.neural;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    private final List<Neuron> neurons ;

    public Layer(int inputCount , int neuronCount) {
        neurons = new ArrayList<>() ;

        for (int i = 0 ; i < neuronCount ; i ++) {
            neurons.add(new Neuron(inputCount));
        }
    }

    public double[] forward(double[] inputs) {

        double[] outputs = new double[neurons.size()];

        for (int i = 0; i < neurons.size(); i++) {
            outputs[i] = neurons.get(i).forward(inputs);
        }

        return outputs;
    }


    public List<Neuron> getNeurons() {
        return neurons;
    }
}
