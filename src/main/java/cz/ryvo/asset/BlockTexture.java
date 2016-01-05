package cz.ryvo.asset;

import cz.ryvo.measures.Vector2L;

public class BlockTexture extends AbstractTexture {

    private static final Vector2L expectedSize = new Vector2L(128, 256);

    public BlockTexture(String resourceName) {
        super(resourceName);
    }

    @Override
    protected Vector2L expectedTextureSize() {
        return new Vector2L(256, 256);
    }
}
