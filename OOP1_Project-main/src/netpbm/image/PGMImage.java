package netpbm.image;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ASCII PGM (Portable Graymap) image in the Netpbm format.
 * <p>
 * A PGM image stores grayscale pixel data, where each {@link Pixel}
 * contains a single intensity value. This class maintains the image's
 * width, height, maximum pixel value, and a list of all pixel values.
 */
public class PGMImage implements NetPBMImages {

    private int width;
    private int height;
    private int maxVal;
    private List<Pixel> pixels = new ArrayList<>();
    private String fileName;
    private String format = "P2";

    public PGMImage(int width, int height, int maxVal) {
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
    }

    public PGMImage(int width, int height, int maxVal, List<Pixel> pixels) {
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixels = pixels;
        this.format = "P2";
    }

    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }
    @Override public int getMaxVal() { return maxVal; }

    @Override
    public Pixel getPixel(int y, int x) {
        for (Pixel p : pixels) {
            if (p.getX() == x && p.getY() == y) return p;
        }
        return null;
    }

    @Override
    public void setPixel(int y, int x, Pixel pixel) {
        pixel.setX(x);
        pixel.setY(y);
        pixels.removeIf(p -> p.getX() == x && p.getY() == y);
        pixels.add(pixel);
    }

    @Override
    public List<Pixel> getPixels() {
        return pixels;
    }

    @Override
    public void setPixels(List<Pixel> newPixels) {
        this.pixels = newPixels;
    }

    @Override public String getFileName() { return fileName; }
    @Override public void setFileName(String name) { this.fileName = name; }

    @Override public String getFormat() { return format; }
    @Override public void setFormat(String format) { this.format = format; }

    @Override public void setWidth(int width) { this.width = width; }
    @Override public void setHeight(int height) { this.height = height; }
    /**
     * Creates and returns a deep copy of this PGM image.
     * The pixel list is cloned to ensure full independence.
     *
     * @return a cloned {@link NetPBMImages} instance
     */
    @Override
    public NetPBMImages clone() {
        List<Pixel> copyPixels = new ArrayList<>();
        for (Pixel p : pixels) {
            copyPixels.add(p.clone());
        }
        PGMImage copy = new PGMImage(width, height, maxVal, copyPixels);
        copy.setFileName(fileName);
        return copy;
    }
}
