/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.digitExample;
import java.util.*;
import regression.GradientDescentOptimizer;

/**
 *
 * @author Costin
 */
public class Main {
    public static void run() {
        Data.TrainingData[] trainingData = Data.parseData();
        int[] labels = Data.labels();
        Map<Integer, Double[]> weightsPerLabel = new HashMap<>();
        GradientDescentOptimizer op = new GradientDescentOptimizer(1.0, 0.005, 1000);
        
        for(int y: labels){
            Data.TrainingData[] forThisLabelOnes = Data.regressionTrainingSetForLabel(y, trainingData);
            Functions fns = new Functions(forThisLabelOnes, 1.0);
            Double[] initialData = new Double[forThisLabelOnes[0].grayscaleIntensities.length];
            for(int j=0; j<initialData.length; j++){ initialData[j] = 0.; }
            Double[] result = op.optimize(fns.lossFunction(), initialData, fns.partialDerivativePerArgument());
            weightsPerLabel.put(y, result);
        }
        
        System.out.println(weightsPerLabel);
        
    }
}
