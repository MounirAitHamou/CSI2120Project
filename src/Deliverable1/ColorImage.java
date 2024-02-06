package Deliverable1;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
public class ColorImage {
    private int width;
    private int height;
    private int depth;
    private WritableRaster pixels;
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
    public int[] getPixel(int i, int j){
        int[] originalPixel = pixels.getPixel(i, j, (int[]) null);
        return Arrays.copyOfRange(originalPixel, 0, 3);
    }
    public void reduceColor(int d){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int[] originalPixel = getPixel(i,j);
                int[] reducedPixel = new int[3];
                int newred = originalPixel[0]>>(8-d);
                int newgreen = originalPixel[1]>>(8-d);
                int newblue = originalPixel[2]>>(8-d);
                reducedPixel[0] = newred;
                reducedPixel[1] = newgreen;
                reducedPixel[2] = newblue;
                pixels.setPixel(i, j, reducedPixel);
            }
        }
    }

}
