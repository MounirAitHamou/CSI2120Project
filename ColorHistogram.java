/*
 * CSI 2120 - Programming Paradigms [A]
 * 
 * Mounir Aït Hamou  -  300296173
 * Aroha Upreti      -  300283790
 * 
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
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
					totalPixels += Double.parseDouble(value);
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
		int count = (int) Math.pow(2, 3 * d_bit);
		colors = new ArrayList<>(Collections.nCopies(count, 0.0));
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				int index = (pixels[i][j][0] << (2 * d_bit)) + (pixels[i][j][1] << d_bit) + pixels[i][j][2];
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

	public double[] getUnnormalizedHistogram() {
		double[] histogram = new double[colors.size()];

		for (int i = 0; i < colors.size(); i++) {
			histogram[i] = colors.get(i);
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
		try (FileWriter file = new FileWriter(filename, StandardCharsets.UTF_8)) {
			BufferedWriter bwrite = new BufferedWriter(file);
			bwrite.write(Math.pow(2, d_bit * 3) + "\n");
			double[] histogram = getUnnormalizedHistogram();
			for (double value : histogram) {
				bwrite.write(value + " ");
			}
			bwrite.close();
			System.out.println("Histogram saved to file: " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/*
		 * File directory = new
		 * File("C:\\GitHub\\Projects\\CSI2120Project\\queryImages");
		 * File queryImage = new
		 * File("C:\\GitHub\\Projects\\CSI2120Project\\queryImages\\q00.jpg");
		 * ColorImage image = new ColorImage(queryImage.getAbsolutePath());
		 * ColorHistogram colorHistogram = new ColorHistogram(3);
		 * colorHistogram.setImage(image);
		 * double[] histogram = colorHistogram.getUnnormalizedHistogram();
		 * for (double value : histogram) {
		 * System.out.print(value + " ");
		 * }
		 * colorHistogram.ColorHistogram(directory.getAbsolutePath() + File.separator +
		 * "q00.txt");
		 */
		ColorHistogram colorHistogram = new ColorHistogram(
				"C:\\GitHub\\Projects\\CSI2120Project\\queryImages\\q00.txt");
		double[] histogram = colorHistogram.getUnnormalizedHistogram();
		for (double value : histogram) {
			System.out.print(value + " ");
		}

	}
}
