package cz.ryvo.input;

import cz.ryvo.GameStatus;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseMoveHandler extends GLFWCursorPosCallback {

    @Override
    public void invoke(long window, double xpos, double ypos) {
        GameStatus gameStatus = GameStatus.instanceOf();
        gameStatus.setMouseX(xpos);
        gameStatus.setMouseY(ypos);
    }
}
