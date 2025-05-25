package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Observer.GameListener;
import GameEngine.*;

/**
 * Class that manages the main in-game UI.
 * Responsible for displaying and updating health, score, and ammo UI elements.
 * Implements the GameListener interface to receive player events.
 * Singleton pattern is used to ensure only one instance exists.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (25/05/25)
 * @inv Only one instance of GameUI exists at a time.
 */
public class GameUI implements GameListener {
    private static GameUI instance;
    private GameObject healthUI, scoreUI, ammoUI;

    ///////////////////////////// Constructors /////////////////////////////

    /**
     * Private constructor for singleton pattern.
     */
    private GameUI() {}

    ///////////////////////////// Singleton Access /////////////////////////////

    /**
     * Returns the singleton instance of GameUI.
     * @return the singleton instance
     */
    public static GameUI getInstance() {
        if (instance == null) instance = new GameUI();
        return instance;
    }

    ///////////////////////////// UI Logic /////////////////////////////

    /**
     * Resets the UI elements, clearing references.
     */
    public void reset() {
        healthUI = null;
        scoreUI = null;
        ammoUI = null;
    }

    /**
     * Initializes the UI elements for health, score, and ammo.
     * @param engine the game engine instance
     * @param cam the camera position
     */
    public void initUI(GameEngine engine, Point cam) {
        if (healthUI != null && scoreUI != null && ammoUI != null) return;

        UIBehaviour healthBehaviour = new UIBehaviour();
        healthUI = new GameObject("ui_health", new Transform(new Point(cam.x(), cam.y()), 1001, 0, 1), new Circle("0 0 1"), healthBehaviour);
        healthBehaviour.gameObject(healthUI);
        healthUI.setShape(new UITextShape("Health: ---"));
        engine.addEnabled(healthUI);

        UIBehaviour scoreBehaviour = new UIBehaviour();
        scoreUI = new GameObject("ui_score", new Transform(new Point(cam.x(), cam.y()), 1001, 0, 1), new Circle("0 0 1"), scoreBehaviour);
        scoreBehaviour.gameObject(scoreUI);
        scoreUI.setShape(new UITextShape("Score: ---"));
        engine.addEnabled(scoreUI);

        UIBehaviour ammoBehaviour = new UIBehaviour();
        ammoUI = new GameObject("ui_ammo", new Transform(new Point(cam.x(), cam.y()), 1001, 0, 1), new Circle("0 0 1"), ammoBehaviour);
        ammoBehaviour.gameObject(ammoUI);
        ammoUI.setShape(new UITextShape("Ammo: ---"));
        engine.addEnabled(ammoUI);
    }

    ///////////////////////////// GameListener Methods /////////////////////////////

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
     * @param currentAmmo the current ammo in the magazine
     * @param reserveAmmo the reserve ammo
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
}