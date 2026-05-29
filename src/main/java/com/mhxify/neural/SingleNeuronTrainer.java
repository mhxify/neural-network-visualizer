package com.mhxify.neural;

public class SingleNeuronTrainer {

    public static void train(
            Neuron neuron,
            double[] inputs,
            double expected,
            double learningRate,
            int epochs
    ) {
        for (int epoch = 1; epoch <= epochs; epoch++) {

            double predicted = neuron.forward(inputs);
            double loss = LossFunction.mse(expected, predicted);

            double error = predicted - expected;

            double sigmoidDerivative = predicted * (1 - predicted);

            double gradient = error * sigmoidDerivative;

            for (int i = 0; i < neuron.getWeights().length; i++) {
                double oldWeight = neuron.getWeights()[i];

                double newWeight = oldWeight - learningRate * gradient * inputs[i];

                neuron.setWeight(i, newWeight);
            }

            double newBias = neuron.getBias() - learningRate * gradient;
            neuron.setBias(newBias);

            if (epoch % 100 == 0) {
                System.out.println(
                        "Epoch " + epoch +
                                " | Predicted = " + predicted +
                                " | Loss = " + loss
                );
            }
        }
    }
}