import java.awt.*;
import java.awt.image.BufferedImage;

public class MaxEntropy {

    private int width;
    private int height;


    public BufferedImage AutomaticThresholding(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        int tMax = maxEntropyValue(image);
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //progowanie wartoscia tMax
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                double grayScale = convertToGray(image, j, i);
                if (grayScale <= tMax)
                    newImage.setRGB(j, i, Color.BLACK.getRGB());
                else
                    newImage.setRGB(j, i, Color.WHITE.getRGB());
            }
        }

        return newImage;
    }

    private int maxEntropyValue(BufferedImage image) {
        int[] hist = makeHist(image);
        double[] normalizedHist = normHist(hist);

        double[] pT = new double[hist.length];
        pT[0] = normalizedHist[0];
        for (int i = 1; i < hist.length; ++i)
            pT[i] = pT[i - 1] + normalizedHist[i];

        // Entropy for black and white parts of the histogram
        final double epsilon = Double.MIN_VALUE;
        double[] histBlack = new double[hist.length];
        double[] histWhite = new double[hist.length];
        for (int t = 0; t < hist.length; ++t) {
            // Black entropy
            if (pT[t] > epsilon) {
                double max = 0;
                for (int i = 0; i <= t; ++i) {
                    if (normalizedHist[i] > epsilon) {
                        max -= normalizedHist[i] / pT[t] * Math.log(normalizedHist[i] / pT[t]);
                    }
                    histBlack[t] = max;
                }
            } else
                histBlack[t] = 0;

            // White  entropy
            double pTWhite = 1 - pT[t];
            if (pTWhite > epsilon) {
                double max = 0;
                for (int i = t + 1; i < hist.length; ++i)
                    if (normalizedHist[i] > epsilon)
                        max -= normalizedHist[i] / pTWhite * Math.log(normalizedHist[i] / pTWhite);

                histWhite[t] = max;
            } else
                histWhite[t] = 0;
        }

        // Find histogram index with maximum entropy
        double jMax = histBlack[0] + histWhite[0];
        int tMax = 0;
        for (int t = 1; t < hist.length; ++t) {
            double j = histBlack[t] + histWhite[t];
            if (j > jMax) {
                jMax = j;
                tMax = t;
            }
        }

        return tMax;
    }

    private int[] makeHist(BufferedImage image) {
        int[] hist = new int[256];
        for (int i = 0; i < hist.length; i++)
            hist[i] = 0;

        for (int i = 0; i < height; ++i)
            for (int j = 0; j < width; ++j)
                hist[(int) convertToGray(image, j, i)]++;

        return hist;
    }

    private double convertToGray(BufferedImage image, int ii, int jj) {
        Color color = new Color(image.getRGB(ii, jj));
        double red = color.getRed() * 0.299;
        double green = color.getGreen() * 0.587;
        double blue = color.getBlue() * 0.114;
        return red + green + blue;
    }

    private double[] normHist(int[] hist) {

        double sum = 0;
        for (int i = 0; i < hist.length; i++)
            sum += i;

        double[] normalizedHist = new double[hist.length];
        for (int i = 0; i < hist.length; i++)
            normalizedHist[i] = hist[i] / sum;

        return normalizedHist;
    }

}
