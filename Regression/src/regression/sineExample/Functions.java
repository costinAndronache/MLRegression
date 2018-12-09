/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.sineExample;
import java.util.function.Function;
import regression.*;

/**
 *
 * @author Costin
 */
public class Functions {
    static final int a = 0;
    static final int b = 1;
    
    private final R2Point[] trainingData;
    public Functions(R2Point[] trainingData){
        this.trainingData = trainingData;
    }
    
    public Function<Double[], Double> aDerivative() {
        return  (values) -> {
            double result = 0;
            for (R2Point td : this.trainingData) {
                result += 2 * Math.sin(values[b] * td.x) *
                        (Math.sin(values[b] * td.x) * values[a] - td.y);
            }
            return result;
        };
    }
    
    public Function<Double[], Double> bDerivative() {
         return  (values) -> {
            double result = 0;
            for (R2Point td : this.trainingData) {
                result += 2 * values[a] * td.x * Math.cos(values[b] * td.x) *
                        (values[a] * Math.sin(values[b] * td.x) - td.y);
            }
            
            return result;
        };
    }
    
    public Function<Double[], Double> lossFunction() {
        return (values) -> {
            double result = 0;
            for (R2Point td : this.trainingData) {
                double item = td.y - values[a] * Math.sin(values[b] * td.x);
                result += item * item;
            }
            return result ;
        };
    }
}
