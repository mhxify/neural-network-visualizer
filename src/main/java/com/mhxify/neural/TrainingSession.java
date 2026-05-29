package com.mhxify.neural;

public class TrainingSession {

    private final NeuralNetwork network;
    private final double learningRate;

    private int currentEpoch = 0;
    private double lastLoss = 0.0;

    public TrainingSession(
            NeuralNetwork network,
            double learningRate
    ) {
        this.network = network;
        this.learningRate = learningRate;
    }

    public void trainOneEpoch() {
        Layer hiddenLayer = network.getLayers().get(0);
        Layer outputLayer = network.getLayers().get(1);

        double totalLoss = 0.0;

        for (int sample = 0; sample < XORData.INPUTS.length; sample++) {

            double[] inputs = XORData.INPUTS[sample];
            double expected = XORData.OUTPUTS[sample][0];

            double[] outputs = network.forward(inputs);
            double predicted = outputs[0];

            totalLoss += LossFunction.mse(expected, predicted);

            Neuron outputNeuron = outputLayer.getNeurons().get(0);

            double outputError = predicted - expected;
            double outputDelta = outputError * predicted * (1 - predicted);

            for (int i = 0; i < outputNeuron.getWeights().length; i++) {
                double oldWeight = outputNeuron.getWeights()[i];
                double inputToOutputNeuron = outputNeuron.getLastInputs()[i];

                double newWeight =
                        oldWeight - learningRate * outputDelta * inputToOutputNeuron;

                outputNeuron.setWeight(i, newWeight);
            }

            outputNeuron.setBias(
                    outputNeuron.getBias() - learningRate * outputDelta
            );

            for (int h = 0; h < hiddenLayer.getNeurons().size(); h++) {
                Neuron hiddenNeuron = hiddenLayer.getNeurons().get(h);

                double hiddenOutput = hiddenNeuron.getOutput();
                double outputWeight = outputNeuron.getWeights()[h];

                double hiddenDelta =
                        outputDelta * outputWeight * hiddenOutput * (1 - hiddenOutput);

                for (int i = 0; i < hiddenNeuron.getWeights().length; i++) {
                    double oldWeight = hiddenNeuron.getWeights()[i];
                    double inputToHiddenNeuron = hiddenNeuron.getLastInputs()[i];

                    double newWeight =
                            oldWeight - learningRate * hiddenDelta * inputToHiddenNeuron;

                    hiddenNeuron.setWeight(i, newWeight);
                }

                hiddenNeuron.setBias(
                        hiddenNeuron.getBias() - learningRate * hiddenDelta
                );
            }
        }

        currentEpoch++;
        lastLoss = totalLoss;
    }

    public int getCurrentEpoch() {
        return currentEpoch;
    }

    public double getLastLoss() {
        return lastLoss;
    }
}