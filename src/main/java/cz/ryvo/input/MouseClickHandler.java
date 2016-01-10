package cz.ryvo.input;

import cz.ryvo.GameStatus;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;

public class MouseClickHandler extends GLFWMouseButtonCallback {

    @Override
    public void invoke(final long window, final int button, final int action, final int mods) {
        GameStatus gameStatus = GameStatus.instanceOf();

        // Button pressed
        if (action == GLFW_PRESS) {
            // Left button
            if (button == GLFW_MOUSE_BUTTON_1) {
                gameStatus.setMouseLeftButtonPressed(true);
            }
            // Right button
            if (button == GLFW_MOUSE_BUTTON_2) {
                gameStatus.setMouseRightButtonPressed(true);
            }
        }
        // Button released
        else if (action == GLFW_RELEASE) {
            // Left button
            if (button == GLFW_MOUSE_BUTTON_1) {
                gameStatus.setMouseLeftButtonPressed(false);
            }
            // Right button
            if (button == GLFW_MOUSE_BUTTON_2) {
                gameStatus.setMouseRightButtonPressed(false);
            }
        }
    }
}
