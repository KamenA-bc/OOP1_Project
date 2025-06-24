package netpbm.image;

/**
 * Represents a PBM (Portable BitMap) image using the ASCII-based P1 format.
 * <p>
 * This class models black-and-white (binary) images using a two-dimensional
 * array of {@code Pixel} objects, where each pixel is expected to be either black or white.
 * It implements the {@code NetPBMImages} interface, supporting image data access,
 * manipulation, and duplication.
 * </p>
 */
public class PBMImage implements NetPBMImages {

    private int width;
    private int height;
    private Pixel[][] pixels;
    private String fileName;
    private String format = "P1";

    public PBMImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Pixel[height][width];
    }

    public PBMImage(int width, int height, Pixel[][] pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.format = "P1";
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Returns the maximum pixel value supported by PBM format.
     * <p>
     * For binary images, this value is always 1.
     * </p>
     *
     * @return 1, representing the binary nature of PBM images
     */
    @Override
    public int getMaxVal() {
        return 1;
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
     * Creates a deep copy of the current PBM image.
     * <p>
     * All pixel values and metadata are cloned to ensure
     * complete independence from the original object.
     * </p>
     *
     * @return a new {@code NetPBMImages} object identical to this PBM image
     */
    @Override
    public NetPBMImages clone() {
        PBMImage copy = new PBMImage(width, height);
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