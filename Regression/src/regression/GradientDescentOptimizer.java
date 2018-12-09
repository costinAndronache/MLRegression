/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression;
import java.util.Arrays;
import java.util.function.*;
/**
 *
 * @author Costin
 */
public class GradientDescentOptimizer {
    public static class Argument {
        private final double value;
        private final Function<Double[], Double> derivativeWRT;
        public Argument(double initialValue, Function<Double[], Double> derivativeWRT){
            this.value = initialValue;
            this.derivativeWRT = derivativeWRT;
        }
    }
    
    private final double learningRate;
    private final double errorThreshold;
    private final int iterationsThreshold;
    
    public GradientDescentOptimizer(double learningRate, double errorThreshold,
            int iterationsThreshold){
        this.learningRate = learningRate;
        this.errorThreshold = errorThreshold;
        this.iterationsThreshold = iterationsThreshold;
    }
    
     public Double[] optimize(Function<Double[], Double> lossFn, Argument[] arguments) {
        Double[] result = new Double[arguments.length];
        for(int i=0; i<arguments.length; i++){
            result[i] = arguments[i].value;
        }
        
        BiFunction<Double[], Integer, Double> derivativePerArgumentt = 
                (values, argIndex) -> {
                return arguments[argIndex].derivativeWRT.apply(values);
        };
        
        return this.optimize(lossFn, result, derivativePerArgumentt);
     }
    
    public Double[] optimize(Function<Double[], Double> lossFn, Double[] initialValues, 
            BiFunction<Double[], Integer, Double> derivativePerComponent) {
        Double[] result = Arrays.copyOf(initialValues, initialValues.length);
        
        int currentIter = 0;
        double currentFnValue = lossFn.apply(result);
        double prevFnValue = currentFnValue + 2 * this.errorThreshold;
        while (currentIter < this.iterationsThreshold) {
            
            currentIter++; 
            Double[] prevValues = Arrays.copyOf(result, result.length);
            
            for(int i=0; i<result.length; i++){
                result[i] = result[i] - this.learningRate * derivativePerComponent.apply(prevValues, i);
            }
            
            prevFnValue = currentFnValue;
            currentFnValue = lossFn.apply(result);
            if((Math.abs(currentFnValue - prevFnValue) < errorThreshold)) {
                break;
            }
            
            //experimental: check if we accidentally climb back up and abort
            if(currentFnValue > prevFnValue){
                result = prevValues;
                break;
            }
        }
                
        return result;
    }
}
