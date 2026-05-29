package com.mhxify.neural;

import java.util.ArrayList;
import java.util.List;

public class TrainingHistory {

    private final List<Double> losses = new ArrayList<>();

    public void addLoss(double loss) {
        losses.add(loss);
    }

    public List<Double> getLosses() {
        return losses;
    }
}