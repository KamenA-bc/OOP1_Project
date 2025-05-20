package netpbm.io;

import netpbm.image.NetpbmImage;

import java.io.*;

/**
 * Utility class for loading Netpbm images based on their format.
 * <p>
 * Determines the image type from the file's magic number and delegates to the appropriate reader.
 * Supports PBM (P1), PGM (P2), and PPM (P3) formats.
 */
public class NetpbmLoader {

    /**
     * Loads a Netpbm image from the given file.
     * <p>
     * The method reads the magic number from the file header and selects
     * the corresponding reader to parse the file contents.
     *
     * @param file The Netpbm image file to load.
     * @return A {@link NetpbmImage} object representing the parsed image.
     * @throws IOException If the format is unsupported or the file cannot be read.
     */
    public static NetpbmImage load(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String magic = reader.readLine().trim();
        reader.close();

        NetpbmImage image;

        switch (magic) {
            case "P1":
                image = PBMReader.read(file);
                break;
            case "P2":
                image = PGMReader.read(file);
                break;
            case "P3":
                image = PPMReader.read(file);
                break;
            default:
                throw new IOException("Unsupported format: " + magic);
        }

        image.setFileName(file.getName());
        return image;
    }
}



