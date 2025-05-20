package netpbm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CreateRandomPPMImage {
    private int width=320;
    private int height=256;
    private int maxColourValue = 255;
    Random rand = new Random();

    public void createPPMImage(String fileName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write("P3\n");
            bufferedWriter.write(width + " " + height + "\n");
            bufferedWriter.write(maxColourValue + "\n");
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    bufferedWriter.write(rand.nextInt(256) + " "); //R
                    bufferedWriter.write(rand.nextInt(256) + " "); //G
                    bufferedWriter.write(rand.nextInt(256) + "     "); //B

                }
            }
            bufferedWriter.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        CreateRandomPPMImage image = new CreateRandomPPMImage();
        image.createPPMImage("undo2.ppm");
    }
}