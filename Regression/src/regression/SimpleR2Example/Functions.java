/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.SimpleR2Example;
import java.util.function.*;
import regression.*;
/**
 *
 * @author Costin
 */
public class Functions {
    static final int m = 0;
    static final int b = 1;
    
    private final R2Point[] trainingData;
    public Functions(R2Point[] trainingData){
        this.trainingData = trainingData;
    }
    
    public Function<Double[], Double> slopeDerivative() {
        return  (values) -> {
            double result = 0;
            for (R2Point td : this.trainingData) {
                result += -td.x * (td.y - (values[m] * td.x + values[b]));
            }
            return result * (2.0 / this.trainingData.length) ;
        };
       
    }
    
    public Function<Double[], Double> biasDerivative() {
         return  (values) -> {
            double result = 0;
            for (R2Point td : this.trainingData) {
                result += -(td.y - (values[m] * td.x + values[b]));
            }
       
            result =  result * (2.0 / this.trainingData.length) ;
            return result;
        };
    }
    
    public Function<Double[], Double> lossFunction() {
        return (values) -> {
            double result = 0;
            for (R2Point td : this.trainingData) {
                double item = td.y - (values[m] * td.x + values[b]);
                result += item * item;
            }
            result = result * (1.0 / this.trainingData.length); 
            return result ;
        };
    }
}
