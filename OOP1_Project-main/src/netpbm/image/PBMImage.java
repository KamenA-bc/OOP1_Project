package netpbm.image;


import java.util.ArrayList;
import java.util.List;
/**
 * Represents an ASCII PBM (Portable Bitmap) image in the Netpbm format.
 * <p>
 * A PBM image stores binary pixel data where each {@link Pixel} is either
 * black (1) or white (0). This class maintains the image's width, height,
 * format, and a list of all pixel values.
 */
public class PBMImage implements NetPBMImages {

    private int width;
    private int height;
    private List<Pixel> pixels = new ArrayList<>();
    private String fileName;
    private String format = "P1";

    public PBMImage(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public PBMImage(int width, int height, List<Pixel> pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.format = "P1";
    }

    @Override public int getWidth() { return width; }
    @Override public int getHeight() { return height; }


    @Override public int getMaxVal() { return 1; }

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
     * Creates a deep copy of this PBM image, including cloned pixel data.
     *
     * @return a new {@link NetPBMImages} object identical to this one
     */
    @Override
    public NetPBMImages clone() {
        List<Pixel> copyPixels = new ArrayList<>();
        for (Pixel p : pixels) {
            copyPixels.add(p.clone());
        }
        PBMImage copy = new PBMImage(width, height, copyPixels);
        copy.setFileName(fileName);
        return copy;
    }
}