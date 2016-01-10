package cz.ryvo.cubeworld;

import cz.ryvo.asset.BlockTexture;
import cz.ryvo.cubeworld.mesh.Cube;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengles.GLES;
import org.lwjgl.opengles.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrixf;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayExample {

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;

    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;

    // The window handle
    private long window;

    // Matrices
    Matrix4f projMatrix = new Matrix4f();
    Matrix4f viewMatrix = new Matrix4f();

    // FloatBuffer for transferring matrices to OpenGL
    FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);

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
            loop();
            cleanup();
        } finally {
            // Terminate GLFW and release the GLFWErrorCallback
            glfwTerminate();
            errorCallback.release();
        }
    }

    private void init() {
        createWindow();
        initGL();
        loadTextures();

        voxel = new Cube();
    }

    private void createWindow() {
        // Setup an error callback. The default implementation will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GLFW_TRUE) {
            throw new RuntimeException("Unable to initialize GLFW");
        }

        // Configure the window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Cube World", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GLFW_TRUE); // We will detect this in our rendering loop
            }
        });

        // Get the resolution of the primary monitor
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center the window
        glfwSetWindowPos(window, (vidMode.width() - SCREEN_WIDTH) / 2, (vidMode.height() - SCREEN_HEIGHT) / 2);
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(window);
    }

    private void initGL() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        GLES.createCapabilities();

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
        ///GLES20.glClearDepthf(1.0f); // Depth Buffer Setup
        ///GLES20.glEnable(GL11.GL_TEXTURE_2D); // Enable AbstractTexture Mapping
        GLES20.glEnable(GL11.GL_COLOR_MATERIAL);
        GLES20.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        //GL11.glShadeModel(GL11.GL_SMOOTH); // Enable Smooth Shading (There's no replacement for this in GLES20)
        ///GLES20.glViewport(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        ///GLES20.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Testing To Do
        ///glMatrixMode(GL_PROJECTION); // Select The Projection Matrix (There's no replacement for this in GLES20)
        ///glLoadIdentity(); // Reset The Projection Matrix
        ///glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
        ///GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations

        ///GL11.glEnable(GL11.GL_LIGHTING);

        ///ByteBuffer lightBuffer = ByteBuffer.allocateDirect(16);
        ///lightBuffer.order(ByteOrder.nativeOrder());
//        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, (FloatBuffer) lightBuffer.asFloatBuffer().put(lightAmbient).flip()); // Setup The Ambient Light
//        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, (FloatBuffer) lightBuffer.asFloatBuffer().put(lightDiffuse).flip()); // Setup The Diffuse Light
//        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, (FloatBuffer) lightBuffer.asFloatBuffer().put(lightPosition).flip()); // Position The Light
//        GL11.glEnable(GL11.GL_LIGHT1);  // Enable Light One
    }

    private void loop() {
        // Set perpective matrix
        projMatrix = new Matrix4f()
                .perspective(
                        (float) Math.toRadians(45.0f),
                        1.0f,
                        0.01f,
                        100f)
                .lookAt(
                        0.0f, 0.0f, 3.0f,
                        0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f
                );
        projMatrix.get(floatBuffer);
        glMatrixMode(GL_PROJECTION); // Select to the projection matrix
        glLoadMatrixf(floatBuffer);

        glMatrixMode(GL_MODELVIEW); // Switch back to the model view matrix

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (glfwWindowShouldClose(window) == GLFW_FALSE ) {
            GLES20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            render();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void cleanup() {
        // Release window and window callbacks
        glfwDestroyWindow(window);
        keyCallback.release();
    }

    private void loadTextures() {
//        blockTexture =  new BlockTexture("textures/dirt_with_grass.png");
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        voxel.render(0, 0, -5, xrot, yrot ,zrot, blockTexture);

        xrot += 0.5f;
        yrot += 0.4f;
        zrot += 0.3f;
    }
}