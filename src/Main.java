import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //SCIEZKA DO OBRAZKA JAKO ARGUMENT PROGRAMU!!!!!
        String path = args[0];
        BufferedImage image = null;

        try {
            File file = new File(path);
            image = ImageIO.read(file);

        } catch (Exception e) {
            System.out.println(e);
        }

        Scanner sc = new Scanner(System.in);
        System.out.println(" Choose one of the options:");
        System.out.println("1-->closingDiskElement");
        System.out.println("2-->geoDistance");
        System.out.println("3-->KirschFilter");
        System.out.println("4-->MaxEntropy");
        int number = sc.nextInt();

        switch (number) {

            case 1:
                Scanner scanner = new Scanner(System.in);
                System.out.print(" Give radius: ");
                ClosingDiskElement close = new ClosingDiskElement(scanner.nextInt());
                save(close.closing(image));
                break;
            case 2:
                int x, y;
                Scanner scanner1 = new Scanner(System.in);

                System.out.println("Give Point position (x,y) ");
                System.out.println("x = ");

                x = scanner1.nextInt();
                System.out.println("y = ");
                y = scanner1.nextInt();

                GeoDistance geoDistance = new GeoDistance(x, y);
                save(geoDistance.distance(image));
                break;
            case 3:
                KirschFilter kirsch = new KirschFilter();
                save(kirsch.kirschFilter(image));
                break;
            case 4:
                MaxEntropy maxEntropy = new MaxEntropy();
                save(maxEntropy.AutomaticThresholding(image));
                break;
            default:
                break;
        }
    }
//TUTAJ WPISUJE SCIEZKE DO ZAPISU !!!!!!!!!!!!!!!!!!!!!!!!!
     private static void save(BufferedImage image) {
        try {
            File file = new File("C:\\Users\\Tomek\\IdeaProjects\\ImageProcessing1\\result\\res_kirsch_manalisa.jpg");
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}