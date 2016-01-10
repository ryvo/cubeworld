package cz.ryvo;

public class GameStatus {

    private static GameStatus gameStatus = null;

    private double mouseX = 0d;
    private double mouseY = 0d;

    private boolean mouseLeftButtonPressed  = false;
    private boolean mouseRightButtonPressed = false;

    private boolean forwardsKeyPressed    = false;
    private boolean backwardsKeyPressed   = false;
    private boolean strafeLeftKeyPressed  = false;
    private boolean strafeRightKeyPressed = false;

    private GameStatus() {
    }

    public static GameStatus instanceOf() {
        if (gameStatus == null) {
            gameStatus = new GameStatus();
        }
        return gameStatus;
    }

    public static GameStatus getGameStatus() {
        return gameStatus;
    }

    public static void setGameStatus(GameStatus gameStatus) {
        GameStatus.gameStatus = gameStatus;
    }

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public boolean isMouseLeftButtonPressed() {
        return mouseLeftButtonPressed;
    }

    public void setMouseLeftButtonPressed(boolean mouseLeftButtonPressed) {
        this.mouseLeftButtonPressed = mouseLeftButtonPressed;
    }

    public boolean isMouseRightButtonPressed() {
        return mouseRightButtonPressed;
    }

    public void setMouseRightButtonPressed(boolean mouseRightButtonPressed) {
        this.mouseRightButtonPressed = mouseRightButtonPressed;
    }

    public boolean isForwardsKeyPressed() {
        return forwardsKeyPressed;
    }

    public void setForwardsKeyPressed(boolean forwardsKeyPressed) {
        this.forwardsKeyPressed = forwardsKeyPressed;
    }

    public boolean isBackwardsKeyPressed() {
        return backwardsKeyPressed;
    }

    public void setBackwardsKeyPressed(boolean backwardsKeyPressed) {
        this.backwardsKeyPressed = backwardsKeyPressed;
    }

    public boolean isStrafeLeftKeyPressed() {
        return strafeLeftKeyPressed;
    }

    public void setStrafeLeftKeyPressed(boolean strafeLeftKeyPressed) {
        this.strafeLeftKeyPressed = strafeLeftKeyPressed;
    }

    public boolean isStrafeRightKeyPressed() {
        return strafeRightKeyPressed;
    }

    public void setStrafeRightKeyPressed(boolean strafeRightKeyPressed) {
        this.strafeRightKeyPressed = strafeRightKeyPressed;
    }
}
