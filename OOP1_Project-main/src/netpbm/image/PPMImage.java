package netpbm.image;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an ASCII PPM (Portable Pixmap) image in the Netpbm format.
 * <p>
 * A PPM image stores color pixel data where each {@link Pixel} contains
 * red, green, and blue intensity values. This class maintains the image's
 * dimensions, maximum color value, and a list of pixels.
 */
public class PPMImage implements NetPBMImages {

    private int width;
    private int height;
    private int maxVal;
    private String format = "P3";
    private String fileName;
    private List<Pixel> pixels;

    public PPMImage(int width, int height, int maxVal) {
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixels = new ArrayList<>();
    }

    public PPMImage(int width, int height, int maxVal, List<Pixel> pixels) {
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixels = pixels;
        this.format = "P3";
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getMaxVal() {
        return maxVal;
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
    public List<Pixel> getPixels() {
        return pixels;
    }

    @Override
    public void setPixels(List<Pixel> pixels) {
        this.pixels = pixels;
    }

    @Override
    public Pixel getPixel(int y, int x) {
        for (Pixel p : pixels) {
            if (p.getY() == y && p.getX() == x) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void setPixel(int y, int x, Pixel pixel) {

        pixels.removeIf(p -> p.getY() == y && p.getX() == x);
        pixel.setX(x);
        pixel.setY(y);
        pixels.add(pixel);
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
     * Creates a deep copy of this PPM image, duplicating all pixel data.
     *
     * @return a cloned {@link NetPBMImages} instance identical to this one
     */
    @Override
    public NetPBMImages clone() {
        List<Pixel> clonedPixels = new ArrayList<>();
        for (Pixel p : pixels) {
            clonedPixels.add(p.clone());
        }
        PPMImage copy = new PPMImage(width, height, maxVal, clonedPixels);
        copy.setFileName(fileName);
        return copy;
    }
}
