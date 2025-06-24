package netpbm.image;

/**
 * Represents a single pixel in an image.
 * Stores RGB color components as integer values.
 *
 * <p>Supports both grayscale and full-color (RGB) representations.</p>
 */
public class Pixel {
    private int red;
    private int green;
    private int blue;

    public Pixel(int value) {
        this.red = value;
        this.green = value;
        this.blue = value;
    }

    public Pixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() { return red; }

    public int getGreen() { return green; }

    public int getBlue() { return blue; }

    public void setRed(int red) { this.red = red; }

    public void setGreen(int green) { this.green = green; }

    public void setBlue(int blue) { this.blue = blue; }

    /**
     * Creates an exact copy of the current pixel.
     *
     * @return a new {@code Pixel} instance with identical RGB values
     */
    public Pixel clone() {
        return new Pixel(red, green, blue);
    }
}