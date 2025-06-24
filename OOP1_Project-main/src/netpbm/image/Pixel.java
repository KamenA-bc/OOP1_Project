package netpbm.image;

public class Pixel {
    private int red;
    private int green;
    private int blue;

    // For grayscale or PBM
    public Pixel(int value) {
        this.red = value;
        this.green = value;
        this.blue = value;
    }

    // For RGB
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

    public Pixel clone() {
        return new Pixel(red, green, blue);
    }
}