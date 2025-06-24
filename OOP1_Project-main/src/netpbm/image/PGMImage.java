package netpbm.image;

/**
 * Represents a PGM (Portable Graymap) image in the P2 ASCII format.
 * <p>
 * This class models grayscale images using a two-dimensional array of {@code Pixel} objects,
 * where each pixel holds identical RGB values representing a single intensity level.
 * It conforms to the {@code NetPBMImages} interface, providing access to image metadata,
 * pixel manipulation, and cloning functionality.
 * </p>
 */
public class PGMImage implements NetPBMImages {

    private int width;
    private int height;
    private int maxVal;
    private Pixel[][] pixels;
    private String fileName;
    private String format = "P2";

    public PGMImage(int width, int height, int maxVal) {
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixels = new Pixel[height][width];
    }

    public PGMImage(int width, int height, int maxVal, Pixel[][] pixels) {
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixels = pixels;
        this.format = "P2";
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getMaxVal() {
        return maxVal;
    }

    @Override
    public Pixel[][] getPixels() {
        return pixels;
    }

    @Override
    public Pixel getPixel(int y, int x) {
        return pixels[y][x];
    }

    @Override
    public void setPixel(int y, int x, Pixel pixel) {
        pixels[y][x] = pixel;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setFileName(String name) {
        this.fileName = name;
    }

    /**
     * Creates a deep copy of the current grayscale image.
     * <p>
     * The clone includes all image dimensions and pixel values,
     * with each pixel being duplicated to ensure full separation from the original instance.
     * </p>
     *
     * @return a new {@code NetPBMImages} object that is an exact copy of this image
     */
    @Override
    public NetPBMImages clone() {
        PGMImage copy = new PGMImage(width, height, maxVal);
        copy.setFileName(fileName);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                copy.setPixel(y, x, pixels[y][x].clone());
            }
        }
        return copy;
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void setPixels(Pixel[][] pixels) {
        this.pixels = pixels;
    }
}
