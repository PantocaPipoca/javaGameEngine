package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Camera;
import GameEngine.GameObject;
import GameEngine.ShapeFactory;
import GameEngine.Transform;
import GameEngine.GameEngine;

import java.awt.*;

/**
 * UI class for displaying the Game Over screen and blackout overlay.
 * Handles showing/hiding the Game Over UI and detecting user interaction with "Yes" and "No" buttons.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class GameOverUI {
    private static final Rectangle yesBox = new Rectangle(645, 625, 120, 50);
    private static final Rectangle noBox  = new Rectangle(800, 510, 120, 50);
    private static boolean active = false;
    private static Point spriteWorldPosition = null;

    /**
     * Shows the blackout overlay and the Game Over sprite.
     * Only shows if not already active.
     */
    public static void showBlackout() {
        if (active) return; // already showing
        active = true;

        GameObject blackout = new GameObject(
                "ui_blackout",
                new Transform(Camera.getInstance(null).position(), 999, 0, 1),
                new Circle("0 0 1"),
                new UIBehaviour()
        );
        blackout.setShape(ShapeFactory.createShape("blackout", 1));
        GameEngine.getInstance().addEnabled(blackout);

        GameObject gameOverSprite = new GameObject(
                "ui_gameover_sprite",
                new Transform(Camera.getInstance(null).position(), 1000, 0, 1),
                new Circle("0 0 1"),
                new UIBehaviour()
        );
        gameOverSprite.setShape(ShapeFactory.createShape("gameover", 1));
        GameEngine.getInstance().addEnabled(gameOverSprite);
        spriteWorldPosition = gameOverSprite.transform().position();
    }

    /**
     * Checks if the Game Over UI is currently shown.
     * @return true if Game Over is shown, false otherwise
     */
    public static boolean isGameOverShown() {
        return active;
    }

    /**
     * Checks if the given screen coordinates are inside the "Yes" button box.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if inside the "Yes" box, false otherwise
     */
    public static boolean isInYesBox(int x, int y) {
        return yesBox.contains(x, y);
    }

    /**
     * Checks if the given screen coordinates are inside the "No" button box.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if inside the "No" box, false otherwise
     */
    public static boolean isInNoBox(int x, int y) {
        return noBox.contains(x, y);
    }

    /**
     * Gets the rectangle for the "Yes" button, adjusted to the sprite's world position.
     * @return the rectangle for the "Yes" button
     */
    public static Rectangle getYesBox() {
        if (spriteWorldPosition == null) return new Rectangle(0, 0, 0, 0);
        return new Rectangle((int) (spriteWorldPosition.x() - 165), (int) (spriteWorldPosition.y() + 140), 120, 50);
    }

    /**
     * Gets the rectangle for the "No" button, adjusted to the sprite's world position.
     * @return the rectangle for the "No" button
     */
    public static Rectangle getNoBox() {
        if (spriteWorldPosition == null) return new Rectangle(0, 0, 0, 0);
        return new Rectangle((int) (spriteWorldPosition.x() + 35), (int) (spriteWorldPosition.y() + 140), 120, 50);
    }

    /**
     * Resets the Game Over UI state to inactive.
     */
    public static void reset() {
        active = false;
    }
}