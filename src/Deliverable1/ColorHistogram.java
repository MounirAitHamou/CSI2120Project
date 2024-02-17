/*
 * CSI 2120 - Programming Paradigms [A]
 * 
 * Mounir Aït Hamou  -  300296173
 * Aroha Upreti      -  300283790
 * 
 */

package Deliverable1;

import java.io.*;
import java.util.ArrayList;
import Deliverable1.ColorImage;


public class ColorHistogram {
	// Instance Variables
	private ColorImage image;
	private int d_bit;
	private ArrayList<Double> colors;
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
					colors.add(Double.parseDouble(value));
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
		image.reduceColor(d_bit);
		System.out.println(d_bit);
		int[][][] pixels = image.getPixels();
		int count = (int) Math.pow(Math.pow(2,d_bit),3);
		colors = new ArrayList<>();
		for (int i = 0; i < count; i++){
			colors.add(0.0);
		}
		for(int i = 0; i < pixels.length; i++){
			for(int j = 0; j < pixels[0].length; j++){
				int index = 0;
				for (int k = 0; k < 3; k++){
					index += (pixels[i][j][k] << (d_bit*k));
				}
				colors.set(index,colors.get(index)+1);
			}
		}
		totalPixels = pixels.length*pixels[0].length;
		for (int i = 0; i < colors.size(); i++){
			colors.set(i,(colors.get(i)/totalPixels));
		}
	}
	
	/**
	 * Computes normalized histogram of the image
	 * 
	 * @return      normalized image histogram
	 */
	public Double [] getHistogram() {
		
		return colors.toArray(new Double[colors.size()]);
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
	public static void main(String[] args) {
		ColorImage image = new ColorImage("q00.jpg");
		ColorHistogram hist = new ColorHistogram(3);
		hist.setImage(image);
		Double[] histogram = hist.getHistogram();
		double sum = 0;
		for (int i = 0; i < histogram.length; i++){
			System.out.println(histogram[i]);
			sum += histogram[i];
		}
		System.out.println(sum);
	}

}
