package cz.ryvo.asset;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.newdawn.slick.opengl.PNGDecoder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.newdawn.slick.opengl.PNGDecoder.RGBA;

public class Image {

    private int height;
    private int width;

    ByteBuffer buffer;

    public Image(final String ref) throws IOException, ImageReadException {
        // Open the PNG resource as an InputStream
        try(InputStream in = ResourceLoader.getResourceAsStream(ref)) {
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Get the width and height of the texture
            width = decoder.getWidth();
            height = decoder.getHeight();

            // Decode the PNG file in a ByteBuffer
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, decoder.getWidth() * 4, RGBA);
            buffer.flip();
        }
    }
}
