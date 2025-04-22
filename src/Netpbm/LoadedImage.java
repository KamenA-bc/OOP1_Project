package Netpbm;

public class LoadedImage {
    public enum Format { PBM, PGM, PPM }

    private final String filename;
    private final Format format;
    private final int width;
    private final int height;
    private final int maxVal; // 1 for PBM, 255 or other for PGM/PPM
    private final int[][][] pixelData; // [height][width][RGB or grayscale]

    public LoadedImage(String filename, Format format, int width, int height, int maxVal, int[][][] pixelData) {
        this.filename = filename;
        this.format = format;
        this.width = width;
        this.height = height;
        this.maxVal = maxVal;
        this.pixelData = pixelData;
    }

    public String getFilename() {
        return filename;
    }

    public Format getFormat() {
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

    public int[][][] getPixelData() {
        return pixelData;
    }
}