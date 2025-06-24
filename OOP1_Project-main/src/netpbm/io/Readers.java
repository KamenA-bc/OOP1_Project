package netpbm.io;

import netpbm.image.NetPBMImages;
import java.io.IOException;

public interface Readers {
    NetPBMImages read(String path) throws IOException;
}

