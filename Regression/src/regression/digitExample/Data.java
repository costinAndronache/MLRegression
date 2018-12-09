/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression.digitExample;

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
    }
    
    public static TrainingData[] parseData() {
        // must open .mat file and read
        
        return null;
    }
}
