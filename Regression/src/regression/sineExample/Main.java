/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.sineExample;
import java.util.Arrays;
import java.util.function.Function;
import regression.*;
import regression.sineExample.*;
/**
 *
 * @author Costin
 */
public class Main {
    public static void run() {
        GradientDescentOptimizer op = new GradientDescentOptimizer(0.01, 0.1, 1000);
        R2Point[] trainingData = Data.parseData();
        Functions fns = new Functions(trainingData);
        GradientDescentOptimizer.Argument[] arguments = new GradientDescentOptimizer.Argument[2];
        
        arguments[Functions.a] = new GradientDescentOptimizer.Argument(0.5, fns.aDerivative());
        arguments[Functions.b] = new GradientDescentOptimizer.Argument(0.5, fns.bDerivative());
                
        Double[] result = op.optimize(fns.lossFunction(), arguments);
        System.out.println(Arrays.asList(result));
        
        //result[Functions.a] = 1.5;
        //result[Functions.b] = 1.0;
        
        Function<Double, Double> prediction = (x) -> {
            return result[Functions.a] * Math.sin(x * result[Functions.b]); 
        };
        
        for(R2Point td: trainingData){
            System.out.println("(" + td.x + ") = " + prediction.apply(td.x) + " | " + td.y );
        }
        
        
        System.out.println("\"x\", \"y\"");
        for(R2Point td: trainingData){
            System.out.println(td.x + ", " + prediction.apply(td.x));
        }
    }
}
