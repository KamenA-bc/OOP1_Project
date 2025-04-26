package Netpbm;

import Netpbm.Enums.Format;

public class LoadedImage {

    private final String filename;
    private final Format format;
    private  int width;
    private  int height;
    private final int maxVal;
    private  int[][][] pixelData; // [height][width][RGB]

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

    public void setPixelData(int[][][] pixelData) {
        this.pixelData = pixelData;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}