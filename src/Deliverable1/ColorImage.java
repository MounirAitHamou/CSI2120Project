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
    private WritableRaster pixels;
    
    // Constructor
    public ColorImage(String fileName) {
        try{
            BufferedImage image = ImageIO.read(new File(fileName));
            width = image.getWidth();
            height = image.getHeight();
            depth = image.getColorModel().getPixelSize();
            pixels = image.getRaster();
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
        int[] originalPixel = pixels.getPixel(i, j, (int[]) null);
        return Arrays.copyOfRange(originalPixel, 0, 3);
    }
    
    /**
   	 * Reduces the color space of each pixel to a d-bit representation
   	 * 
   	 * @param   d  the 'd'-bit representation to be changed to
   	 */
    public void reduceColor(int d){
        int mask = (1 << d) - 1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] originalPixel = getPixel(i, j);
                int newRed = originalPixel[0] >> (depth - d);
                int newGreen = originalPixel[1] >> (depth - d);
                int newBlue = originalPixel[2] >> (depth - d);
                int[] reducedPixel = {newRed & mask, newGreen & mask, newBlue & mask};
                pixels.setPixel(i, j, reducedPixel);
            }
        }
        depth = d;

    }

}
