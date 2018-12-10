/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.digitExample;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        final double learningRate = 0.5;
        final double errorThreshold = 0.005;
        final int maxIterations = 5000;
        final double smoothness = 1000;
        
        
        GradientDescentOptimizer op = new GradientDescentOptimizer(learningRate, errorThreshold, maxIterations);
        
        for(int y: labels){
            Data.TrainingData[] forThisLabelOnes = Data.regressionTrainingSetForLabel(y, trainingData);
            Functions fns = new Functions(forThisLabelOnes, smoothness);
            Double[] initialData = new Double[forThisLabelOnes[0].grayscaleIntensities.length];
            for(int j=0; j<initialData.length; j++){ initialData[j] = 0.; }
            Double[] result = op.optimize(fns.lossFunction(), initialData, fns.partialDerivativePerArgument());
            weightsPerLabel.put(y, result);
        }
        
        String header = "Learning rate = " + learningRate +
                "; Error threshold = " + errorThreshold +
                "; Max iterations = " + maxIterations +
                "; Smoothness = " + smoothness;
        
        try (PrintWriter out = new PrintWriter("weights.txt")) {
            weightsPerLabel.forEach((label, weights) -> {
                out.println(label + "");
                for(double w: weights){
                    out.print(w + " ");
                }
                out.println();
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Map<Integer, Integer> correctGuesses = new HashMap<>();
        Map<Integer, Integer> wrongGuesses = new HashMap<>();
        
        Classifier cls = new Classifier(weightsPerLabel);
        for(Data.TrainingData td: trainingData){
            Classifier.BestPrediction pd = cls.bestPredictionFor(td.grayscaleIntensities);
            Map<Integer, Integer> map = pd.predictedLabel == td.label ? correctGuesses : wrongGuesses;
            map.put(td.label, map.getOrDefault(td.label, 0) + 1);
        }
        
        final double accuracy = (double)correctGuesses.values().stream().reduce((x, y) -> x + y).get() / 
                trainingData.length;
        
        final double error = 1 - accuracy;
        
         try (PrintWriter out = new PrintWriter("trainingError.txt")) {
           out.println(header);
           out.println("Error: " + error);
           out.println("Accuracy: " + accuracy);
           out.println("Correct guesses:\n" + correctGuesses);
           out.println("Wrong guesses: \n" + wrongGuesses);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
