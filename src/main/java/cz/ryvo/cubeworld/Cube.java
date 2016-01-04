package cz.ryvo.cubeworld;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Cube {

    private static final int FLOAT = Float.SIZE / Byte.SIZE;
    // @formatter:off
    private static final float vertexA[] = {-1.0f, -1.0f, -1.0f};
    private static final float vertexB[] = { 1.0f, -1.0f, -1.0f};
    private static final float vertexC[] = { 1.0f,  1.0f, -1.0f};
    private static final float vertexD[] = {-1.0f,  1.0f, -1.0f};
    private static final float vertexE[] = {-1.0f, -1.0f,  1.0f};
    private static final float vertexF[] = { 1.0f, -1.0f,  1.0f};
    private static final float vertexG[] = { 1.0f,  1.0f,  1.0f};
    private static final float vertexH[] = {-1.0f,  1.0f,  1.0f};

    private static final float vertexData[] = {
         1.0f,  1.0f, -1.0f,
        -1.0f,  1.0f, -1.0f,
        -1.0f,  1.0f,  1.0f,
         1.0f,  1.0f,  1.0f,

         1.0f, -1.0f,  1.0f,
        -1.0f, -1.0f,  1.0f,
        -1.0f, -1.0f, -1.0f,
         1.0f, -1.0f, -1.0f,

         1.0f,  1.0f,  1.0f,
        -1.0f,  1.0f,  1.0f,
        -1.0f, -1.0f,  1.0f,
         1.0f, -1.0f,  1.0f,

         1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f, -1.0f,
        -1.0f,  1.0f, -1.0f,
         1.0f,  1.0f, -1.0f,

        -1.0f,  1.0f,  1.0f,
        -1.0f,  1.0f, -1.0f,
        -1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f,  1.0f,

         1.0f,  1.0f, -1.0f,
         1.0f,  1.0f,  1.0f,
         1.0f, -1.0f,  1.0f,
         1.0f, -1.0f, -1.0f
    };

    private static final float colorData[] = {
            1, 1, 0,
            1, 0, 1,
            0, 0, 1,
            0, 1, 1,

            1, 1, 0,
            1, 0, 1,
            0, 0, 1,
            0, 1, 1,

            1, 1, 0,
            1, 0, 1,
            0, 0, 1,
            0, 1, 1,

            1, 1, 0,
            1, 0, 1,
            0, 0, 1,
            0, 1, 1,

            1, 1, 0,
            1, 0, 1,
            0, 0, 1,
            0, 1, 1,

            1, 1, 0,
            1, 0, 1,
            0, 0, 1,
            0, 1, 1
    };
// @formatter:off

    private static final int vertices = 24;
    private static final int vertexSize = 3;
    private static final int colorSize = 3;

    private int vertexBufferHandle;
    private int colorBufferHandle;

    public Cube() {
        vertexBufferHandle = GL15.glGenBuffers();
        colorBufferHandle = GL15.glGenBuffers();

        FloatBuffer vertexBufferData = BufferUtils.createFloatBuffer(vertexData.length);
        vertexBufferData.put(vertexData);
        vertexBufferData.flip();

        FloatBuffer colorBufferData = BufferUtils.createFloatBuffer(colorData.length);
        colorBufferData.put(colorData);
        colorBufferData.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBufferData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBufferData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public int getVertices() {
        return vertices;
    }

    public int getVertexSize() {
        return vertexSize;
    }

    public int getColorSize() {
        return colorSize;
    }

    public int getVertexBufferHandle() {
        return vertexBufferHandle;
    }

    public int getColorBufferHandle() {
        return colorBufferHandle;
    }
}
