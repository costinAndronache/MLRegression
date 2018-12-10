/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.digitExample;
import java.util.function.*;
/**
 *
 * @author Costin
 */
public class Functions {
    private final Data.TrainingData[] trainingData;
    private final double smoothness;
    public Functions(Data.TrainingData[] trainingData, double smoothness){
        this.trainingData = trainingData;
        this.smoothness = smoothness;
    }
    
    public static Double predictionFor(Double[] features, Double[] weights){
        double dotProd = 0;
        for(int i=0; i<features.length; i++){
            dotProd += features[i] * weights[i];
        }
        //take bias into account
        dotProd += 1;
        // apply the sigmoid on dotProd, a.k.a g(dotProd)
        double sigmValue = 1.0 / (1.0 + Math.exp(-dotProd));
        return sigmValue;
    }
    
    
    public BiFunction<Double[], Integer, Double> partialDerivativePerArgument() {
        return (weights, argIndex) -> {
            double result = 0.0; 
            for(Data.TrainingData td: this.trainingData) {
                result += (predictionFor(td.grayscaleIntensities, weights) - td.label) 
                        * td.grayscaleIntensities[argIndex];
            }
            result *= 1.0/this.trainingData.length;
            if(argIndex > 0) {
                result += (this.smoothness / this.trainingData.length) * weights[argIndex];
            }
            return result;
        };
    }
    
    //TO DO: implement one vs all classificator
    
    public Function<Double[], Double> lossFunction() {
        return (weights) -> {
            double result = 0.;
            for(Data.TrainingData td: this.trainingData){
                double prediction = predictionFor(td.grayscaleIntensities, weights);
                double firstTerm = -td.label * Math.log(prediction);
                double secondTerm = (1 - td.label) * Math.log(1 - prediction);
                result += firstTerm - secondTerm;
            }
            result *= 1.0 / this.trainingData.length;
            double sqWeightsSum = 0;
            for(Double w: weights){
                sqWeightsSum += w*w;
            }
            sqWeightsSum *= this.smoothness / (2.0 * this.trainingData.length);
            return result + sqWeightsSum;
        };
    }
}
