package netpbm.image;

import java.util.List;

public interface PixelData {
    List<Pixel> getPixels();
    Pixel getPixel(int y, int x);
    void setPixel(int y, int x, Pixel pixel);
    void setPixels(List<Pixel> pixels);
}
