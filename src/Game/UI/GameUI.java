package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Camera;
import Game.Observer.GameListener;
import GameEngine.GameObject;
import GameEngine.Transform;
import GameEngine.GameEngine;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * UI manager for displaying player health, score, and ammo.
 * Implements GameListener to update UI elements in response to game events.
 * Handles initialization, positioning, and rendering of UI components.
 * 
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class GameUI implements GameListener {

    private static GameUI instance;
    private GameUI() {}
    private GameObject healthUI;
    private GameObject scoreUI;
    private GameObject ammoUI;
    private ArrayList<GameObject> uiObjects = new ArrayList<>();

    /**
     * Initializes the UI elements for health, score, and ammo.
     * Only initializes once per game session.
     * @param engine the game engine
     * @param cam the camera position
     */
    public void initUI(GameEngine engine, Point cam) {
        if (healthUI != null && scoreUI != null && ammoUI != null) {
            return; // Already initialized
        }

        UITextShape healthText = new UITextShape("Health: ---");
        healthUI = new GameObject("ui_health", new Transform(new Point(cam.x(), cam.y()), 3, 0, 1), new Circle("0 0 1"), new UIBehaviour());
        UITextShape scoreText = new UITextShape("Score: ---");
        scoreUI = new GameObject("ui_score", new Transform(new Point(cam.x(), cam.y()), 3, 0, 1), new Circle("0 0 1"), new UIBehaviour());
        UITextShape ammoText = new UITextShape("Ammo: ---");
        ammoUI = new GameObject("ui_ammo", new Transform(new Point(cam.x(), cam.y()), 3, 0, 1), new Circle("0 0 1"), new UIBehaviour());

        healthUI.setShape(healthText);
        scoreUI.setShape(scoreText);
        ammoUI.setShape(ammoText);

        uiObjects.add(healthUI);
        uiObjects.add(scoreUI);
        uiObjects.add(ammoUI);
    }

    /**
     * Updates the positions of UI elements based on the camera position.
     * @param cam the camera position
     */
    public void updatePositions(Point cam) {
        healthUI.transform().position(new Point(cam.x() - 830, cam.y() - 490));
        scoreUI.transform().position(new Point(cam.x() + 730, cam.y() - 490));
        ammoUI.transform().position(new Point(cam.x() + 730, cam.y() + 400));
    }

    /**
     * Renders the UI elements on the screen.
     * @param g the graphics context
     */
    public void render(Graphics2D g) {
        if (uiObjects.isEmpty()) return;

        // Get camera position
        Point cam = Camera.getInstance(null).position();
        int screenCX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        int screenCY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

        double offsetX = screenCX - cam.x();
        double offsetY = screenCY - cam.y();

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform();
        g2.translate(offsetX, offsetY);

        for (GameObject go : uiObjects) {
            if (go.shape() != null && go.transform() != null) {
                go.shape().render(g2, go.transform(), go.isFlipped(), go.transform().angle());
            }
        }

        g2.setTransform(old);
    }

    /**
     * Updates the health UI when the player's health changes.
     * @param health the new health value
     */
    @Override
    public void onPlayerHealthChanged(int health) {
        ((UITextShape) healthUI.shape()).setText("Health: " + health);
    }

    /**
     * Updates the ammo UI when the player's ammo changes.
     * @param currentAmmo the current ammo count
     * @param reserveAmmo the reserve ammo count
     */
    @Override
    public void onAmmoChanged(int currentAmmo, int reserveAmmo) {
        ((UITextShape) ammoUI.shape()).setText("Ammo: " + currentAmmo + " / " + reserveAmmo);
    }

    /**
     * Updates the score UI when the player's score changes.
     * @param score the new score value
     */
    @Override
    public void onScoreChanged(float score) {
        ((UITextShape) scoreUI.shape()).setText("Score: " + score);
    }

    /**
     * Gets the singleton instance of the GameUI.
     * @return the GameUI instance
     */
    public static GameUI getInstance() {
        if (instance == null) {
            instance = new GameUI();
        }
        return instance;
    }
}