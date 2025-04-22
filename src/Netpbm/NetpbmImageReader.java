package Netpbm;

import java.io.IOException;

public interface NetpbmImageReader {
    LoadedImage read(String filePath) throws IOException;
}
