package netpbm.image;

/**
 * Represents a single pixel in an image with a defined position and color.
 * Stores its own (x, y) coordinates and RGB values.
 */
public class Pixel {
    private int x;
    private int y;
    private int red;
    private int green;
    private int blue;

    public Pixel(int x, int y, int value) {
        this(x, y, value, value, value);
    }

    public Pixel(int x, int y, int red, int green, int blue) {
        this.x = x;
        this.y = y;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public int getRed() { return red; }
    public int getGreen() { return green; }
    public int getBlue() { return blue; }

    public void setRed(int red) { this.red = red; }
    public void setGreen(int green) { this.green = green; }
    public void setBlue(int blue) { this.blue = blue; }

    public Pixel clone() {
        return new Pixel(x, y, red, green, blue);
    }
}