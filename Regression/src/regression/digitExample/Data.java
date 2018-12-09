/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.digitExample;
import com.jmatio.io.*;
import com.jmatio.types.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
/**
 *
 * @author Costin
 */
public class Data {
    public static class TrainingData {
        public Double[] grayscaleIntensities;
        public Integer label;
        public TrainingData(Double[] grayscaleIntensities, Integer label){
            this.grayscaleIntensities = grayscaleIntensities;
            this.label = label;
        }
        
        @Override
        public String toString() {
            return "Label: " + this.label + "\nFeatures: " + Arrays.asList(grayscaleIntensities);
        }
    }
    
    public static TrainingData[] parseData() {
        // must open .mat file and read
        File file = new File("ex3data1.mat");
        MatFileReader reader;
        try {
            reader = new MatFileReader(file);
        } catch (IOException ex) {
            return null;
        }
        MLDouble matInstances = (MLDouble) reader.getMLArray("X");
        MLDouble matLabels = (MLDouble) reader.getMLArray("y");
        
        double[][] instances = matInstances.getArray();
        double[][] labels = matLabels.getArray();
        
        TrainingData[] result = new TrainingData[labels.length];
        for(int i=0; i<result.length; i++){
            Double[] featureVec = new Double[instances[i].length];
            for(int j=0; j<featureVec.length; j++){
                featureVec[j] = instances[i][j];
            }
            result[i] = new TrainingData(featureVec, (int)labels[i][0]);
        }
        
        return result;
    }
    
    public static int[] labels() {
        return new int[] {10, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    }
    
    public static TrainingData[] regressionTrainingSetForLabel(int label, TrainingData[] source){
      TrainingData[] result = new TrainingData[source.length];
      for(int i=0; i<result.length; i++){
          result[i] = new TrainingData(source[i].grayscaleIntensities, 
          source[i].label == label ? 1 : 0);
      }
      return result;
    }
}
