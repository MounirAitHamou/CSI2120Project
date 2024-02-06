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
