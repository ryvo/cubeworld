package cz.ryvo.asset;

import cz.ryvo.exception.ResourceException;
import cz.ryvo.measures.Vector2L;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

public abstract class AbstractTexture {

    protected Texture texture;

    protected AbstractTexture(final String resourceName) {
        loadFromFile(resourceName);
        checkTexture(texture);
    }

    protected void loadFromFile(final String resourceName) {
        String format = "PNG";
        int i = resourceName.lastIndexOf('.');
        if (i > 0) {
            format = resourceName.substring(i + 1).toUpperCase();
        }

        try {
            texture = TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream(resourceName), GL11.GL_LINEAR);
        } catch (IOException e) {
            throw new ResourceException("Missing block texture '" + resourceName + "'");
        }
    }

    protected void checkTexture(Texture texture) {
        Vector2L expectedSize = expectedTextureSize();
        if ( (expectedSize.getX() > 0 && texture.getTextureWidth() != expectedSize.getX())  ||
             (expectedSize.getY() > 0 && texture.getTextureHeight() != expectedSize.getY())) {
            throw new ResourceException("Bad block texture size. Texture must be " + expectedSize.getX() +
                    "px wide and " + expectedSize.getY() + "px high.");
        }
    }

    protected abstract Vector2L expectedTextureSize();
}
