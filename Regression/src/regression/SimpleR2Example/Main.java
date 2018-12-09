/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.SimpleR2Example;
import java.util.Arrays;
import regression.*;

/**
 *
 * @author Costin
 */
public class Main {
    public static void run() {
        GradientDescentOptimizer op = new GradientDescentOptimizer(0.1, 0.001, 1000);
        R2Point[] trainingData = Data.parse2();
        Functions fns = new Functions(trainingData);
        GradientDescentOptimizer.Argument[] arguments = new GradientDescentOptimizer.Argument[2];
        
        arguments[Functions.m] = new GradientDescentOptimizer.Argument(0, fns.slopeDerivative());
        arguments[Functions.b] = new GradientDescentOptimizer.Argument(0, fns.biasDerivative());
                
        Double[] result = op.optimize(fns.lossFunction(), arguments);
        System.out.println(Arrays.asList(result));
        
        for(R2Point td: trainingData){
            double predictedY = result[Functions.m] * td.x + result[Functions.b];
            System.out.println("(" + td.x + ") = " + predictedY + " | " + td.y );
        }
    }
}
