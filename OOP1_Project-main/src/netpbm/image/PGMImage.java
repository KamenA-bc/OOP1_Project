package netpbm.image;

public class PGMImage implements NetPBMImages {

    private  int width;
    private  int height;
    private  int maxVal;
    private  Pixel[][] pixels;
    private String fileName;

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

    private String format = "P2";

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
