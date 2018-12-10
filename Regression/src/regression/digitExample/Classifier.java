/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.digitExample;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Costin
 */
public class Classifier {
    public static class BestPrediction {
        public int predictedLabel;
        public double probability;
        public BestPrediction(int label, double prob){
            this.predictedLabel = label;
            this.probability = prob;
        }
    }
    
    private final Map<Integer, Double[]> weights;
    public Classifier(Map<Integer, Double[]> weights){
        this.weights = weights;
    }
    
    public BestPrediction bestPredictionFor(Double[] features){
        Map<Integer, Double> predictions = this.weights.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, 
                e -> Functions.predictionFor(features, e.getValue())));
        Optional<Map.Entry<Integer, Double>> bestEntry = predictions.entrySet().stream().max((e1, e2) -> {
            return e1.getValue() > e2.getValue() ? -1 : 0;
        });
        
        return new BestPrediction(bestEntry.get().getKey(), bestEntry.get().getValue());
    }
}
