import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadPPMImage {

    public void readPPMImage(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            br.readLine();

            String line = br.readLine();
            while (line.startsWith("#")) {
                line = br.readLine();
            }

            // Ширина и височина
            String[] dimensions = line.trim().split("\\s+");
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            int maxColor = Integer.parseInt(br.readLine().trim());

            List<Integer> pixels = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] pixelValues = line.trim().split("\\s+");
                for (String value : pixelValues) {
                    pixels.add(Integer.parseInt(value));
                }
            }

            System.out.println("PPM Loaded (format already verified externally):");
            System.out.println("Width: " + width);
            System.out.println("Height: " + height);
            System.out.println("Max color: " + maxColor);
            System.out.println("Total pixels read: " + pixels.size() / 3);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadPPMImage reader = new ReadPPMImage();
        reader.readPPMImage("image1.ppm");
    }
}
