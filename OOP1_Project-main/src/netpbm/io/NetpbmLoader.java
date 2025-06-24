package netpbm.io;

import netpbm.image.NetPBMImages;
import java.io.*;
import java.util.*;

/**
 * Utility class for loading Netpbm images based on their format.
 * <p>
 * Determines the image type from the file's magic number and delegates to the appropriate reader.
 * Supports PBM (P1), PGM (P2), and PPM (P3) formats.
 */
public class NetpbmLoader {

    private static final Map<String, Readers> readerMap = new HashMap<>();

    // Static block to register known readers
    static {
        readerMap.put("P1", new PBMReader());
        readerMap.put("P2", new PGMReader());
        readerMap.put("P3", new PPMReader());
    }

    /**
     * Loads a NetPBM image using the appropriate reader implementation
     * based on the magic number (P1, P2, P3) in the file header.
     *
     * @param file The NetPBM file to load
     * @return A parsed NetPBMImages object
     * @throws IOException If the file cannot be read or the format is unsupported
     */
    public static NetPBMImages load(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // Skip comments and blank lines
            do {
                line = reader.readLine();
            } while (line != null && (line.trim().isEmpty() || line.startsWith("#")));

            if (line == null) {
                throw new IOException("File is empty or contains only comments");
            }

            String format = line.trim();

            Readers readerImpl = readerMap.get(format);
            if (readerImpl == null) {
                throw new IOException("Unsupported NetPBM format: " + format);
            }

            // Use the correct reader implementation to load the file
            return readerImpl.read(file.getPath());
        }
    }
}



