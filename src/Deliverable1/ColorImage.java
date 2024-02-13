/*
 * CSI 2120 - Programming Paradigms [A]
 * 
 * Mounir Aït Hamou  -  
 * Aroha Upreti      -  300283790
 * 
 */

package Deliverable1;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
public class ColorImage {
	// Instance Variables
    private int width;
    private int height;
    private int depth;
    private int[][][] pixels;
    
    // Constructor
    public ColorImage(String fileName) {
        try{
            BufferedImage image = ImageIO.read(new File(fileName));
            width = image.getWidth();
            height = image.getHeight();
            depth = image.getColorModel().getPixelSize();
            pixels = new int[width][height][3];
            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    int rgb = image.getRGB(i, j);
                    pixels[i][j][0] = (rgb >> 16) & 0xFF;
                    pixels[i][j][1] = (rgb >> 8) & 0xFF;
                    pixels[i][j][2] = rgb & 0xFF;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Methods
    /**
	 * Returns the 3-channel value of pixel at column i row j in the 
	 * form of a 3-element array
	 * 
	 * @param   i  pixel at column 'i'
	 * @param   j  pixel at row 'j'   
	 * @returns    3-element array
	 */
    public int[] getPixel(int i, int j){
       return pixels[i][j];
    }
 
    /**
   	 * Reduces the color space of each pixel to a d-bit representation
   	 * 
   	 * @param   d  the 'd'-bit representation to be changed to
   	 */
    public void reduceColor(int d) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j][0] = (pixels[i][j][0] >> (8 - d));
                pixels[i][j][1] = (pixels[i][j][1] >> (8 - d));
                pixels[i][j][2] = (pixels[i][j][2] >> (8 - d));
            }
        }
    }

    public int[][][] getPixels() {
        return pixels;
    }
}
