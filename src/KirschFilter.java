import java.awt.image.BufferedImage;

public class KirschFilter {

    private int width;
    private int height;

    private static int[][][] kirschMask = {
            {{-3, -3, 5}, {-3, 0, 5}, {-3, -3, 5}},
            {{-3, 5, 5}, {-3, 0, 5}, {-3, -3, -3}},
            {{5, 5, 5}, {-3, 0, -3}, {-3, -3, -3}},
            {{5, 5, -3}, {5, 0, -3}, {-3, -3, -3}},
            {{5, -3, -3}, {5, 0, -3}, {5, -3, -3}},
            {{-3, -3, -3}, {5, 0, -3}, {5, 5, -3}},
            {{-3, -3, -3}, {-3, 0, -3}, {5, 5, 5}},
            {{-3, -3, -3}, {-3, 0, 5}, {-3, 5, 5}},
    };

    private int[][] addMask(BufferedImage image, int[][] mask) {
         height = image.getHeight();
         width = image.getWidth();

        int[][] res = new int[height - 2][width - 2];

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {

                int red = 0;
                int blue = 0;
                int green = 0;

                for (int k = -1; k < 2; k++) {
                    for (int m = -1; m < 2; m++) {
                        red += (image.getRGB(j + m, i + k) >> 16 & 0xff) * mask[k + 1][m + 1];
                        green += (image.getRGB(j + m, i + k) >> 8 & 0xff) * mask[k + 1][m + 1];
                        blue += (image.getRGB(j + m, i + k) & 0xff) * mask[k + 1][m + 1];
                    }
                }
                res[i - 1][j - 1] = convertFromRGBtoInt(red, green, blue);
            }
        }

        return res;
    }

    private int convertFromRGBtoInt(int red, int green, int blue) {

        return  65536 * red + 256 * green + blue;
    }

    public BufferedImage kirschFilter(BufferedImage bufferedImage) {

        int[][][] temp = new int[8][width][height];
        for (int i = 0; i < 8; i++)
            temp[i] = addMask(bufferedImage, kirschMask[i]);

        BufferedImage res = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < 8; i++) {
            for (int x = 0; x < width - 2; x++)
                for (int y = 0; y < height - 2; y++) {
                    if (res.getRGB(x, y) > temp[i][y][x])
                        res.setRGB(x, y, temp[i][y][x]);
                }
        }
        return res;
    }
}