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
 */
public class InputEvent {
    private Set<Integer> pressedKeys = new HashSet<>();
    private Set<Integer> pressedMouseButtons = new HashSet<>();
    private Point mousePosition = new Point(0, 0);

    /////////////////////////////////////////////////// Keyboard Logic ///////////////////////////////////////////////////

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
     * Checks if a key is pressed.
     * @param keyCode code of the key
     * @return true if the key is pressed, false otherwise
     */
    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    /////////////////////////////////////////////////// Mouse Logic ///////////////////////////////////////////////////

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
     * Checks if a mouse button is pressed.
     * @param button code of the mouse button
     * @return true if the button is pressed, false otherwise
     */
    public boolean isMouseButtonPressed(int button) {
        return pressedMouseButtons.contains(button);
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
     * Returns the current mouse position.
     * @return mouse position as a Point object
     */
    public Point mousePosition() {
        return mousePosition;
    }

    /**
     * Converts the current mouse position into world coordinates.
     * @return mouse position in world coordinates as a Point object
     */
    public Point mouseWorldPosition() {
        int sx = mousePosition.x;
        int sy = mousePosition.y;

        double camX = Camera.getInstance().position().x();
        double camY = Camera.getInstance().position().y();

        JFrame gui = GameEngine.getInstance().getGui();
        int screenCX = gui.getWidth() / 2;
        int screenCY = gui.getHeight() / 2;

        double worldX = camX + (sx - screenCX);
        double worldY = camY + (sy - screenCY);

        return new Point((int)worldX, (int)worldY);
    }
}