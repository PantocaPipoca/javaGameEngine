package Game.UI;

import Figures.Circle;
import Figures.Point;
import Figures.Polygon;
import Game.Camera;
import GameEngine.*;

/**
 * Class that manages the Game Over UI.
 * Responsible for displaying the game over screen, handling button creation, and user interaction.
 * Singleton pattern is used to ensure only one instance exists.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (25/05/25)
 * @inv Only one instance of GameOverUI exists at a time.
 */
public class GameOverUI {
    private static GameOverUI instance;
    private boolean active = false;
    private GameObject blackout, gameOverSprite, yesButton, noButton;

    ///////////////////////////// Constructors /////////////////////////////

    /**
     * Private constructor for singleton pattern.
     */
    private GameOverUI() {}

    ///////////////////////////// Singleton Access /////////////////////////////

    /**
     * Returns the singleton instance of GameOverUI.
     * @return the singleton instance
     */
    public static GameOverUI getInstance() {
        if (instance == null) {
            instance = new GameOverUI();
        }
        return instance;
    }

    ///////////////////////////// UI Logic /////////////////////////////

    /**
     * Shows the Game Over UI, creating and enabling all UI elements.
     */
    public void show() {
        if (active) return;
        active = true;
        Point center = Camera.getInstance(null).position();

        UIBehaviour blackoutBehaviour = new UIBehaviour();
        blackout = new GameObject("ui_blackout", new Transform(center, 999, 0, 1), new Circle("0 0 1"), blackoutBehaviour);
        blackoutBehaviour.gameObject(blackout);
        blackout.setShape(ShapeFactory.createShape("blackout", 1));
        GameEngine.getInstance().addEnabled(blackout);

        UIBehaviour gameOverBehaviour = new UIBehaviour();
        gameOverSprite = new GameObject("ui_gameover_sprite", new Transform(center, 1000, 0, 1), new Circle("0 0 1"), gameOverBehaviour);
        gameOverBehaviour.gameObject(gameOverSprite);
        gameOverSprite.setShape(ShapeFactory.createShape("gameover", 1));
        GameEngine.getInstance().addEnabled(gameOverSprite);

        UIBehaviour yesBehaviour = new UIBehaviour();
        yesButton = new GameObject("ui_yes_button", new Transform(new Point(center.x() - 160, center.y() + 140), 1001, 0, 1), new Polygon("4 0 0 120 0 120 50 0 50"), yesBehaviour);
        yesBehaviour.gameObject(yesButton);
        yesButton.setShape(ShapeFactory.createShape("button_yes", 1));
        GameEngine.getInstance().addEnabled(yesButton);

        UIBehaviour noBehaviour = new UIBehaviour();
        noButton = new GameObject("ui_no_button", new Transform(new Point(center.x() + 20, center.y() + 145), 1001, 0, 1), new Polygon("4 0 0 120 0 120 50 0 50"), noBehaviour);
        noBehaviour.gameObject(noButton);
        noButton.setShape(ShapeFactory.createShape("button_no", 1));
        GameEngine.getInstance().addEnabled(noButton);
    }

    /**
     * Resets the Game Over UI, destroying all UI elements and deactivating the UI.
     */
    public void reset() {
        active = false;
        GameEngine engine = GameEngine.getInstance();
        engine.destroy(noButton);
        engine.destroy(blackout);
        engine.destroy(yesButton);
        engine.destroy(gameOverSprite);
    }

    /**
     * Checks if the Game Over UI is currently active.
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Checks if the given coordinates are within the "Yes" button.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the coordinates are within the "Yes" button, false otherwise
     */
    public boolean isInYes(int x, int y) {
        Point center = Camera.getInstance().position();
        int bx = (int) (center.x() - 160);
        int by = (int) (center.y() + 140);
        return x >= bx && x <= bx + 120 && y >= by && y <= by + 50;
    }

    /**
     * Checks if the given coordinates are within the "No" button.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the coordinates are within the "No" button, false otherwise
     */
    public boolean isInNo(int x, int y) {
        Point center = Camera.getInstance().position();
        int bx = (int) (center.x() + 20);
        int by = (int) (center.y() + 145);
        return x >= bx && x <= bx + 120 && y >= by && y <= by + 50;
    }
}