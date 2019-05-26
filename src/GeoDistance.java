import java.awt.*;
import java.awt.image.BufferedImage;


public class GeoDistance {

    private int width;
    private int height;
    private Point A;
    private int[][] marker;

    public GeoDistance(int x, int y) {
        this.A = new Point();
        A.x = x;
        A.y = y;
    }

     BufferedImage distance(BufferedImage image) {

        width = image.getWidth();
        height = image.getHeight();
        BufferedImage map = null;

        try {
            map = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);

        } catch (Exception e) {
            System.out.println(e);
        }

        int[][] inputImage = new int[width][height];
        int[][] dilatationMap = new int[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                inputImage[j][i] = image.getRGB(j, i);
                if (inputImage[j][i] == Color.WHITE.getRGB())
                    inputImage[j][i] = 1;
                else
                    inputImage[j][i] = 0;
            }
        }

        marker = new int[width][height];

        marker[A.x][A.y] = 1; //nasz punkt aznaczamy na 1

        int iter = 0;
        int condition = (height + width) * 2;

        while (iter < condition) {
            marker = dilatation(marker, dilatationMap, iter %256);

            marker = logicalAnd(marker, inputImage);

            iter++;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map.setRGB(j, i, dilatationMap[j][i]);
            }
        }
        return map;
    }

    private int[][] logicalAnd(int[][] marker, int[][] inputImage) {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (marker[j][i] == 1  && inputImage[j][i] == 1)
                    marker[j][i] = 1;
                else if ((marker[j][i] == 1) && inputImage[j][i] == 0)
                    marker[j][i] = 3;
            }
        }
        return marker;
    }

    private int[][] dilatation(int[][] image, int[][] map, int iter) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (image[j][i] == 1) {
                    if (i > 0 && image[j][i - 1] == 0)
                        image[j][i - 1] = 2;
                    if (i + 1 < height && image[j][i + 1] == 0)
                        image[j][i + 1] = 2;
                    if (j > 0 && image[j - 1][i] == 0)
                        image[j - 1][i] = 2;
                    if (j + 1 < width && image[j + 1][i] == 0)
                        image[j + 1][i] = 2;
                }
            }
        }

        change2(image, map, iter);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (image[j][i] == 2)
                    image[j][i] = 1;
            }
        }
        return image;
    }

    private void change2(int[][] image, int[][] dilMap, int iter) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (image[j][i] == 2)
                    dilMap[j][i] = iter;
            }
        }
    }


}