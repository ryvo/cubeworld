package cz.ryvo.input;

import cz.ryvo.GameStatus;
import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class KeyboardHandler extends GLFWKeyCallback {

    @Override
    public void invoke(long window, int key, int scanCode, int action, int mods) {
        GameStatus gameStatus = GameStatus.instanceOf();

        // Key pressed
        if (action == GLFW_PRESS) {
            // Move forwards
            if (key == GLFW_KEY_W) {
                gameStatus.setForwardsKeyPressed(true);
            }
            // Move backwards
            if (key == GLFW_KEY_S) {
                gameStatus.setBackwardsKeyPressed(true);
            }
            // Strafe left
            if (key == GLFW_KEY_A) {
                gameStatus.setBackwardsKeyPressed(true);
            }
            // Strafe right
            if (key == GLFW_KEY_D) {
                gameStatus.setBackwardsKeyPressed(true);
            }
        }
        // Key released
        else if (action == GLFW_RELEASE) {
            // Move forwards
            if (key == GLFW_KEY_W) {
                gameStatus.setForwardsKeyPressed(true);
            }
            // Move backwards
            if (key == GLFW_KEY_S) {
                gameStatus.setBackwardsKeyPressed(true);
            }
            // Strafe left
            if (key == GLFW_KEY_A) {
                gameStatus.setBackwardsKeyPressed(true);
            }
            // Strafe right
            if (key == GLFW_KEY_D) {
                gameStatus.setBackwardsKeyPressed(true);
            }

            if (key == GLFW_KEY_ESCAPE)
                glfwSetWindowShouldClose(window, GLFW_TRUE);
        }
    }
}
