package netpbm.image;

/**
 * Represents a bitmap image in Netpbm format (PBM, PGM, or PPM).
 * <p>
 * Stores pixel data, format metadata, and file-related information.
 * Supports deep cloning and provides access to individual pixels and channels.
 */
public class NetpbmImage implements Cloneable {

    private String format;
    private int width;
    private int height;
    private int maxVal;
    private int[][][] pixels;
    private String fileName;

    /**
     * Constructs a Netpbm image with specified properties and initializes pixel data.
     *
     * @param format   Netpbm format identifier ("P1", "P2", or "P3")
     * @param width    Image width in pixels
     * @param height   Image height in pixels
     * @param maxVal   Maximum pixel intensity value (only used for P2 and P3)
     * @param channels Number of channels (1 for PBM/PGM, 3 for PPM)
     */
    public NetpbmImage(String format, int width, int height, int maxVal, int channels) {
        this.format = format;
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixels = new int[height][width][channels];
    }

    public String getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public int[][][] getPixels() {
        return pixels;
    }
    

    public void setFormat(String format) {
        this.format = format;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    public void setPixels(int[][][] pixels) {
        this.pixels = pixels;
    }

    public void setPixel(int y, int x, int channel, int value) {
        this.pixels[y][x][channel] = value;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    /**
     * Creates a deep copy of this image.
     * <p>
     * The cloned image includes all metadata and a fully independent copy of the pixel data,
     * so modifications to the clone will not affect the original.
     *
     * @return A deep clone of this {@code NetpbmImage}.
     */
    @Override
    public NetpbmImage clone() {
        NetpbmImage copy = new NetpbmImage(format, width, height, maxVal, pixels[0][0].length);
        copy.fileName = fileName;

        int[][][] clonedPixels = new int[height][width][];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                clonedPixels[y][x] = pixels[y][x].clone();
            }
        }
        copy.pixels = clonedPixels;

        return copy;
    }
}
