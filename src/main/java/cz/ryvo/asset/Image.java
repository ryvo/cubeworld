package cz.ryvo.asset;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Image {

    ByteBuffer imgeBuffer;

    public Image(final String ref) throws IOException {
        InputStream is = ResourceLoader.getResourceAsStream(ref);
        //FileChannel fileChannel = fis.getChannel();
    }
}
