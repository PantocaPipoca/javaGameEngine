package GameEngine;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import Game.Camera;

/**
 * Class that encapsulates input events for keyboard and mouse.
 * Tracks pressed keys, mouse buttons, and mouse position, and provides world-coordinate conversion.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Mouse position is always valid; pressed key/button sets are never null.
 */
public class InputEvent {
    private Set<Integer> pressedKeys = new HashSet<>(); // Currently pressed keys
    private Set<Integer> pressedMouseButtons = new HashSet<>(); // Pressed mouse buttons
    private Point mousePosition = new Point(0, 0); // Current mouse position

    /**
     * Updates the state of pressed keys.
     * @param keyCode code of the pressed key
     */
    public void keyPressed(int keyCode) {
        pressedKeys.add(keyCode);
    }

    /**
     * Updates the state of released keys.
     * @param keyCode code of the released key
     */
    public void keyReleased(int keyCode) {
        pressedKeys.remove(keyCode);
    }

    /**
     * Updates the state of pressed mouse buttons.
     * @param button code of the pressed mouse button
     */
    public void mouseButtonPressed(int button) {
        pressedMouseButtons.add(button);
    }

    /**
     * Updates the state of released mouse buttons.
     * @param button code of the released mouse button
     */
    public void mouseButtonReleased(int button) {
        pressedMouseButtons.remove(button);
    }

    /**
     * Updates the current mouse position on the screen.
     * @param x X position of the mouse
     * @param y Y position of the mouse
     */
    public void updateMousePosition(int x, int y) {
        mousePosition.setLocation(x, y);
    }

    /**
     * Converts the current mouse position into WORLD-COORDS.
     * @return mouse position in world coordinates as a Point object
     */
    public Point getMouseWorldPosition() {
        // 1) raw screen-pixels
        int sx = mousePosition.x;
        int sy = mousePosition.y;

        // 2) camera world-center
        double camX = Camera.getInstance().getPosition().x();
        double camY = Camera.getInstance().getPosition().y();

        // 3) screen center in pixels
        JFrame gui = GameEngine.getInstance().getGui();
        int screenCX = gui.getWidth() / 2;
        int screenCY = gui.getHeight() / 2;

        // 4) unproject
        double worldX = camX + (sx - screenCX);
        double worldY = camY + (sy - screenCY);

        return new Point((int)worldX, (int)worldY);
    }

    /**
     * Checks if a key is pressed.
     * @param keyCode code of the key
     * @return true if the key is pressed, false otherwise
     */
    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    /**
     * Checks if a mouse button is pressed.
     * @param button code of the mouse button
     * @return true if the button is pressed, false otherwise
     */
    public boolean isMouseButtonPressed(int button) {
        return pressedMouseButtons.contains(button);
    }

    /**
     * Returns the current mouse position.
     * @return mouse position as a Point object
     */
    public Point getMousePosition() {
        return mousePosition;
    }
}