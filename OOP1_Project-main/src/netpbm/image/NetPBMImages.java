package netpbm.image;

public interface NetPBMImages extends ImageDimension, ImageFormat, PixelData, FilePath, ClonableImage {
    int getMaxVal();
}
