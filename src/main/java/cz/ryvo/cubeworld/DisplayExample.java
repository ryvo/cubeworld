package cz.ryvo.cubeworld;

import cz.ryvo.asset.BlockTexture;
import cz.ryvo.cubeworld.mesh.Cube;
import cz.ryvo.exception.ResourceException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class DisplayExample {

    private float xrot;
    private float yrot;
    private float zrot;
    private float lightAmbient[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    private float lightDiffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    private float lightPosition[] = { 0.0f, 0.0f, 0.1f, 1.0f };
    private Cube voxel;
    private BlockTexture blockTexture;

    public static void main(String[] args) {
        DisplayExample displayExample = new DisplayExample();
        displayExample.start();
    }

    public void start() {
        try {
            init();
            loadTextures();
            voxel = new Cube();
            while (!Display.isCloseRequested()) {
                render();
                Display.update();
                Display.sync(60);
                xrot += 0.2f;
                yrot += 0.3f;
                zrot += 0.1f;
            }

            cleanup();
        } catch (LWJGLException | IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void init() throws LWJGLException, IOException {
        createWindow();
        initGL();
    }

    private void createWindow() throws LWJGLException {
        DisplayMode displayMode = null;
        for (DisplayMode mode : Display.getAvailableDisplayModes()) {
            if (mode.getWidth() == 640 && mode.getHeight() == 480 && mode.getBitsPerPixel() == 24) {
                displayMode = mode;
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("CubeWorld");
        Display.create();
    }

    private void initGL() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
        GL11.glClearDepth(1.0); // Depth Buffer Setup
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable AbstractTexture Mapping
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading
        GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Testing To Do
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix
        GLU.gluPerspective(45.0f, (float) Display.getWidth() / (float) Display.getHeight(), 0.1f, 100.0f); // Calculate The Aspect Ratio Of The Window
        GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations

        GL11.glEnable(GL11.GL_LIGHTING);

        ByteBuffer lightBuffer = ByteBuffer.allocateDirect(16);
        lightBuffer.order(ByteOrder.nativeOrder());
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, (FloatBuffer) lightBuffer.asFloatBuffer().put(lightAmbient).flip()); // Setup The Ambient Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, (FloatBuffer) lightBuffer.asFloatBuffer().put(lightDiffuse).flip()); // Setup The Diffuse Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, (FloatBuffer) lightBuffer.asFloatBuffer().put(lightPosition).flip()); // Position The Light
        GL11.glEnable(GL11.GL_LIGHT1);  // Enable Light One
    }

    private void cleanup() {
        Display.destroy();
    }

    private void loadTextures() {
        blockTexture =  new BlockTexture("textures/dirt_with_grass.png");
    }
    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        voxel.render(0, 0, -5, xrot, yrot ,zrot, blockTexture);
    }
}