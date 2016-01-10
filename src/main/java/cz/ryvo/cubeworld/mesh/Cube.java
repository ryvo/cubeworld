package cz.ryvo.cubeworld.mesh;

import cz.ryvo.asset.BlockTexture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.opengl.Texture;

import java.nio.FloatBuffer;

public class Cube {

    /**
           H_______________G
           /.             /|
          / .            / |
        D/_____________C/  |
        |   .          |   |
        |   .          |   |
        |   ...........|...|
        | . E          |  /F
        |______________|/
        A              B
    */

// @formatter:off
    private static final float[] vertexData = {
            // Position             // Normal               // Texture      // Color

            // Back side - E H G F
            -1.0f, -1.0f, -1.0f,     0.0f,  0.0f, -1.0f,    0.5f,  1.0f,    1.0f, 1.0f, 1.0f, 1.0f, // E
            -1.0f,  1.0f, -1.0f,     0.0f,  0.0f, -1.0f,    0.5f,  0.5f,    1.0f, 1.0f, 1.0f, 1.0f, // H
             1.0f,  1.0f, -1.0f,     0.0f,  0.0f, -1.0f,    0.0f,  0.5f,    1.0f, 1.0f, 1.0f, 1.0f, // G
             1.0f, -1.0f, -1.0f,     0.0f,  0.0f, -1.0f,    0.0f,  1.0f,    1.0f, 1.0f, 1.0f, 1.0f, // F

            // Right side - F G C B
             1.0f, -1.0f, -1.0f,     1.0f,  0.0f,  0.0f,    0.5f,  1.0f,    1.0f, 0.0f, 1.0f, 1.0f, // F
             1.0f,  1.0f, -1.0f,     1.0f,  0.0f,  0.0f,    0.5f,  0.5f,    1.0f, 0.0f, 1.0f, 1.0f, // G
             1.0f,  1.0f,  1.0f,     1.0f,  0.0f,  0.0f,    0.0f,  0.5f,    1.0f, 0.0f, 1.0f, 1.0f, // C
             1.0f, -1.0f,  1.0f,     1.0f,  0.0f,  0.0f,    0.0f,  1.0f,    1.0f, 0.0f, 1.0f, 1.0f, // B

            // Left side - A D H E
            -1.0f, -1.0f, -1.0f,    -1.0f,  0.0f,  0.0f,    0.5f,  1.0f,    0.0f, 1.0f, 0.0f, 1.0f, // A
            -1.0f,  1.0f, -1.0f,    -1.0f,  0.0f,  0.0f,    0.5f,  0.5f,    0.0f, 1.0f, 0.0f, 1.0f, // D
            -1.0f,  1.0f,  1.0f,    -1.0f,  0.0f,  0.0f,    0.0f,  0.5f,    0.0f, 1.0f, 0.0f, 1.0f, // H
            -1.0f, -1.0f,  1.0f,    -1.0f,  0.0f,  0.0f,    0.0f,  1.0f,    0.0f, 1.0f, 0.0f, 1.0f, // E

            // Front side - B C D A
             1.0f, -1.0f, 1.0f,      0.0f,  0.0f,  1.0f,    0.5f,  1.0f,    1.0f, 0.0f, 1.0f, 1.0f, // B
             1.0f,  1.0f, 1.0f,      0.0f,  0.0f,  1.0f,    0.5f,  0.5f,    1.0f, 0.0f, 1.0f, 1.0f, // C
            -1.0f,  1.0f, 1.0f,      0.0f,  0.0f,  1.0f,    0.0f,  0.5f,    1.0f, 0.0f, 1.0f, 1.0f, // D
            -1.0f, -1.0f, 1.0f,      0.0f,  0.0f,  1.0f,    0.0f,  1.0f,    1.0f, 0.0f, 1.0f, 1.0f, // A

            // Top side - C G H D
             1.0f,  1.0f, -1.0f,     0.0f,  1.0f,  0.0f,    0.5f,  0.5f,    1.0f, 1.0f, 0.0f, 1.0f, // C
             1.0f,  1.0f,  1.0f,     0.0f,  1.0f,  0.0f,    0.5f,  0.0f,    1.0f, 1.0f, 0.0f, 1.0f, // G
            -1.0f,  1.0f,  1.0f,     0.0f,  1.0f,  0.0f,    0.0f,  0.0f,    1.0f, 1.0f, 0.0f, 1.0f, // H
            -1.0f,  1.0f, -1.0f,     0.0f,  1.0f,  0.0f,    0.0f,  0.5f,    1.0f, 1.0f, 0.0f, 1.0f, // D

            // Bottom side - F B A E
             1.0f, -1.0f, -1.0f,     0.0f, -1.0f,  0.0f,    1.0f,  0.5f,    0.0f, 1.0f, 1.0f, 1.0f, // F
             1.0f, -1.0f,  1.0f,     0.0f, -1.0f,  0.0f,    1.0f,  0.0f,    0.0f, 1.0f, 1.0f, 1.0f, // B
            -1.0f, -1.0f,  1.0f,     0.0f, -1.0f,  0.0f,    0.5f,  0.0f,    0.0f, 1.0f, 1.0f, 1.0f, // A
            -1.0f, -1.0f, -1.0f,     0.0f, -1.0f,  0.0f,    0.5f,  0.5f,    0.0f, 1.0f, 1.0f, 1.0f, // E
    };
// @formatter:on

    private static final int TOTAL_NUMBER_OF_VERTICES = 24;
    private static final int VERTEX_ATTRIBUTE_SIZE = 12 * Float.BYTES;       // In bytes
    private static final int VERTEX_POSITION_SIZE = 3;                       // In floats
    private static final int VERTEX_TEXTURE_SIZE = 2;                        // In floats
    private static final int VERTEX_COLOR_SIZE = 4;                          // In floats
    private static final int FIRST_VERTEX_POSITION_POINTER = 0;              // In bytes
    private static final int FIRST_VERTEX_NORMAL_POINTER = 3 * Float.BYTES;  // In bytes
    private static final int FIRST_VERTEX_TEXTURE_POINTER = 6 * Float.BYTES; // In bytes
    private static final int FIRST_VERTEX_COLOR_POINTER = 8 * Float.BYTES;   // In bytes

    private final int vertexBufferObjectHandle;
    private final FloatBuffer vertexBufferObject;

    public Cube() {
        vertexBufferObject = BufferUtils.createFloatBuffer(vertexData.length);  // Allocate direct float buffer
        vertexBufferObject.put(vertexData).flip();  // Write data to the buffer and reset the position to zero

        vertexBufferObjectHandle = GL15.glGenBuffers(); // Create named buffer object
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObjectHandle);  // Hey, GL, we will be using named buffer object we just generated
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBufferObject, GL15.GL_STATIC_DRAW);   // Write vertexBufferObject data to our named buffer object
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // Tell GL we aren't using any named buffer since now

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
        //GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);

    }

    public void render(float x, float y, float z, float xrot, float yrot, float zrot, BlockTexture texture) {
        GL11.glLoadIdentity();

        GL11.glTranslatef(x, y, z);
        GL11.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

/*
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
        //GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
*/

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObjectHandle);

        GL11.glVertexPointer(VERTEX_POSITION_SIZE, GL11.GL_FLOAT, VERTEX_ATTRIBUTE_SIZE, FIRST_VERTEX_POSITION_POINTER);
        GL11.glNormalPointer(GL11.GL_FLOAT, VERTEX_ATTRIBUTE_SIZE, FIRST_VERTEX_NORMAL_POINTER);
        //GL11.glTexCoordPointer(VERTEX_TEXTURE_SIZE, GL11.GL_FLOAT, VERTEX_ATTRIBUTE_SIZE, FIRST_VERTEX_TEXTURE_POINTER);
        GL11.glColorPointer(VERTEX_COLOR_SIZE, GL11.GL_FLOAT, VERTEX_ATTRIBUTE_SIZE, FIRST_VERTEX_COLOR_POINTER);

        GL11.glDrawArrays(GL11.GL_QUADS, 0, TOTAL_NUMBER_OF_VERTICES);

/*
        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
        //GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
        GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
*/
    }
}
