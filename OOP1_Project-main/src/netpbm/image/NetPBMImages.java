package netpbm.image;

public interface NetPBMImages  {
    String getFormat();
    int getWidth();
    int getHeight();
    int getMaxVal();
    Pixel[][] getPixels();
    Pixel getPixel(int y, int x);
    void setPixel(int y, int x, Pixel pixel);
    String getFileName();
    void setFileName(String name);
    NetPBMImages clone();
    void setFormat(String format);
    void setWidth(int width);
    void setHeight(int height);
    void setPixels(Pixel[][] pixels);
}
