package netpbm.io;

import netpbm.image.NetPBMImages;
import java.io.*;
import java.util.*;


/**
 * Utility class for loading NetPBM image files (PBM, PGM, PPM).
 * <p>
 * Automatically detects the image format based on the file's header and delegates
 * the reading process to the appropriate format-specific reader.
 * </p>
 */
public class NetpbmLoader {

    /** Maps format identifiers (P1, P2, P3) to their corresponding reader implementations. */
    private static final Map<String, Readers> readerMap = new HashMap<>();


    static {
        readerMap.put("P1", new PBMReader());
        readerMap.put("P2", new PGMReader());
        readerMap.put("P3", new PPMReader());
    }

    /**
     * Loads a NetPBM image from the specified file.
     * <p>
     * Determines the format by reading the header and delegates to the correct
     * {@code Readers} implementation.
     * </p>
     *
     * @param file the file containing the NetPBM image
     * @return a {@code NetPBMImages} instance representing the loaded image
     * @throws IOException if the file is invalid or unsupported
     */
    public static NetPBMImages load(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            // Skip blank lines and comments
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

            return readerImpl.read(file.getPath());
        }
    }
}




