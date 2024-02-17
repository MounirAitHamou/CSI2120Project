/*
 * CSI 2120 - Programming Paradigms [A]
 * 
 * Mounir Aït Hamou  -  
 * Aroha Upreti      -  300283790
 * 
 */
package Deliverable1;
import java.io.File;

public class SimilaritySearch {
    public static void main(String[] args){
    	ColorImage queryImage = new ColorImage("queryImages" + args[0]);
    	ColorHistogram queryHist = new ColorHistogram(3);
    	queryHist.setImage(queryImage);
    	File[] imageDataset = new File(args[1]).listFiles();
    	
    	for (int i = 0; i < imageDataset.length; i+= 2) { // Increments 2 to read only .txt files
    		ColorHistogram imageHist = new ColorHistogram(imageDataset[i].getName());
    		
    		
    		
    	}

    }
}
