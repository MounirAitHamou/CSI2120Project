/*
 * CSI 2120 - Programming Paradigms [A]
 * 
 * Mounir Aït Hamou  -  
 * Aroha Upreti      -  300283790
 * 
 */

package Deliverable1;

import java.io.*;
import java.util.ArrayList;
import java.awt.image.WritableRaster;


public class ColorHistogram {
	// Instance Variables
	private ColorImage image;
	private int d_bit;
	private ArrayList<Integer> colors;
	private int totalPixels;
	
	// Constructors
	public ColorHistogram(int d) {
		this.image = null;
		this.d_bit = d;
	}
	
	public ColorHistogram(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();
			double temp = Math.pow(Double.parseDouble(line),1.0/3.0);
			d_bit = (int)(Math.log(temp)/Math.log(2));
			colors = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(" ");
				for (String value : values) {
					totalPixels += Integer.parseInt(value);
					colors.add(Integer.parseInt(value));
				}
			}
			for (int i = 0; i < colors.size(); i++){
				colors.set(i,(colors.get(i)/totalPixels));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Methods
	/**
	 * Associates image with a histogram instance
	 * 
	 * @param image the desired image to be associated
	 */
	public void setImage(ColorImage image) {
		this.image = image;
		int[][][] pixels = image.getPixels();


	}
	
	/**
	 * Computes normalized histogram of the image
	 * 
	 * @return      normalized image histogram
	 */
	public double [] getHistogram() {
		
		return null;
	}
	
	/**
	 * Compare method that determines the intersection between two histograms
	 * 
	 * @param hist  the given histogram 
	 * @return		the intersection of hist and this instance's histogram
	 */
	public double compare(ColorHistogram hist) {
		
		return 0;
	}
	
	/**
	 * Saves the histogram into a text file
	 * 
	 * @param filename	file that is to be saved into
	 */
	public void ColorHistogram(String filename) {
		FileWriter file;
		try {
			file = new FileWriter(filename);
			BufferedWriter bwrite = new BufferedWriter(file);
			
			System.out.println("Starting to write on file '" + filename + "'.");
			
			/*
			 * Code goes here
			 * bwrite.write("-----------------");
			 */
			
			bwrite.close();
			System.out.println("File Writing... Completed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
