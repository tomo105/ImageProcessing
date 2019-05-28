import java.awt.image.BufferedImage;
import static java.lang.Math.*;

public class ClosingDiskElement {

    private int radius;
    private int width;
    private int height;

    public ClosingDiskElement(int radius) {
        this.radius = radius;
    }

    public BufferedImage dilatation(BufferedImage image) {

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int c = maxElementInDisk(image, j, i);
                newImage.setRGB(j, i, c);
            }
        }

        return newImage;
    }

    private int maxElementInDisk(BufferedImage image, int x, int y) {
        int max = Integer.MIN_VALUE;
        int temp;
        for (int i = y - radius; i < y + radius; i++) {
            for (int j = x - radius; j < x + radius; j++) {
                if ((pow(j - x, 2) + pow(i - y, 2) <= pow(radius, 2))) {
                    if (i >= 0 && j >= 0 && i < height && j < width) {

                        temp = image.getRGB(j, i);

                        if (temp > max)
                            max = temp;
                    }
                }
            }
        }
        return max;
    }

    public BufferedImage closing(BufferedImage image) {

        width = image.getWidth();
        height = image.getHeight();
        BufferedImage convertedImage = dilatation(image);

        return erosion(convertedImage);
    }

    //----------------------------------------------------------------------------------
    private BufferedImage erosion(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int c = minElementInDisk(image, j, i);
                newImage.setRGB(j, i, c);
            }
        }

        return newImage;
    }

    private int minElementInDisk(BufferedImage image, int x, int y) {
        int min = Integer.MAX_VALUE;
        int temp;
        for (int i = y - image.getWidth(); i < y + radius; i++) {
            for (int j = x - radius; j < x + radius; j++) {
                if ((pow(j - x, 2) + pow(i - y, 2) <= pow(radius, 2))) {
                    if (i >= 0 && j >= 0 && i < image.getHeight() && j < width) {

                        temp = image.getRGB(j, i);

                        if (temp < min)
                            min = temp;
                    }
                }
            }
        }
        return min;
    }



}
