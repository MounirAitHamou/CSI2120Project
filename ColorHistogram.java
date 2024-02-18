/*
 * CSI 2120 - Programming Paradigms [A]
 * 
 * Mounir Aït Hamou  -  300296173
 * Aroha Upreti      -  300283790
 * 
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

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
		try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
			String line = br.readLine();
			double temp = Math.pow(Double.parseDouble(line), 1.0 / 3.0);
			d_bit = (int) (Math.log(temp) / Math.log(2));
			colors = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(" ");
				for (String value : values) {
					totalPixels += Integer.parseInt(value);
					colors.add(Double.parseDouble(value));
				}
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
		int[][][] pixels = image.getAllPixels();
		int count = (int) Math.pow(Math.pow(2, d_bit), 3);
		colors = new ArrayList<>(Collections.nCopies(count, 0.0));
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				int index = (pixels[i][j][0] << (2 * d_bit)) + (pixels[i][j][1] << d_bit) + pixels[i][j][2];
				if (index >= count) {
					System.out.println(index);
				}
				colors.set(index, colors.get(index) + 1);
			}
		}
		totalPixels = pixels.length * pixels[0].length;
	}

	/**
	 * Computes normalized histogram of the image
	 * 
	 * @return normalized image histogram
	 */
	public double[] getHistogram() {
		double[] histogram = new double[colors.size()];

		for (int i = 0; i < colors.size(); i++) {
			histogram[i] = colors.get(i) / totalPixels;
		}

		return histogram;
	}

	/**
	 * Compare method that determines the intersection between two histograms
	 * 
	 * @param hist the given histogram
	 * @return the intersection of hist and this instance's histogram
	 */
	public double compare(ColorHistogram hist) {
		double sum = 0;
		double[] hist1 = getHistogram();
		double[] hist2 = hist.getHistogram();
		for (int i = 0; i < hist1.length; i++) {
			sum += Math.min(hist1[i], hist2[i]);
		}
		return sum;
	}

	/**
	 * Saves the histogram into a text file
	 * 
	 * @param filename file that is to be saved into
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
		ColorImage image = new ColorImage("imageDataset2_15_20/25.jpg");
		ColorHistogram imageHist = new ColorHistogram(3);
		imageHist.setImage(image);
		System.out.println(imageHist.compare(new ColorHistogram("imageDataset2_15_20/25.jpg.txt")));
		double[] hist = imageHist.getHistogram();
		double[] hist2 = new ColorHistogram("imageDataset2_15_20/25.jpg.txt").getHistogram();
		for (int i = 0; i < hist.length; i++) {
			if (hist[i] != hist2[i])
				System.out.println(hist[i] + " " + hist2[i]);
		}
	}

}
