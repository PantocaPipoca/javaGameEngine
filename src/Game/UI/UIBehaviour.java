package Game.UI;

import Game.Camera;
import Game.Game;
import GameEngine.*;
import Figures.Point;
import java.util.List;

/**
 * Behaviour class for UI elements.
 * Responsible for updating UI positions relative to the camera, handling UI-specific logic,
 * and processing UI button clicks.
 * Implements the IBehaviour interface for integration with the game engine.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (25/05/25)
 * @inv Owner must be a valid UI GameObject.
 */
public class UIBehaviour implements IBehaviour {
    private IGameObject owner;
    private double timer = 0;

    /**
     * Updates the UI element each frame.
     * Adjusts UI position based on camera and handles special UI logic.
     * @param dT delta time since last update
     * @param ie input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        if (owner == null) return;

        String name = owner.name();

        // HUD UI
        if (name.equals("ui_health") || name.equals("ui_score") || name.equals("ui_ammo")) {
            Point cam = Camera.getInstance(null).position();
            if (name.equals("ui_health"))
                owner.transform().position(new Point(cam.x() - 830, cam.y() - 490));
            if (name.equals("ui_score"))
                owner.transform().position(new Point(cam.x() + 730, cam.y() - 490));
            if (name.equals("ui_ammo"))
                owner.transform().position(new Point(cam.x() + 730, cam.y() + 400));
        }

        // GameOver UI
        if (name.equals("ui_gameover_sprite") || name.equals("ui_blackout") ||
            name.equals("ui_yes_button") || name.equals("ui_no_button")) {
            Point cam = Camera.getInstance(null).position();
            if (name.equals("ui_gameover_sprite") || name.equals("ui_blackout")) {
                owner.transform().position(cam);
            }
            if (name.equals("ui_yes_button")) {
                owner.transform().position(new Point(cam.x() - 160, cam.y() + 140));
            }
            if (name.equals("ui_no_button")) {
                owner.transform().position(new Point(cam.x() + 20, cam.y() + 145));
            }
        }

        // Victory UI
        if (name.equals("ui_victory")) {
            timer += dT;
            if (timer >= 10.0) {
                GameEngine.getInstance().destroy(owner);
                // Optionally: return to main menu, etc.
            }
            owner.transform().position(Camera.getInstance(null).position());
        }
    }

    /**
     * Handles UI button click events.
     * Processes clicks for "Yes" and "No" buttons on the Game Over UI.
     * @param x the x coordinate of the click
     * @param y the y coordinate of the click
     */
    public void onUIClick(int x, int y) {
        String name = owner.name();
        Point cam = Camera.getInstance(null).position();
        if (name.equals("ui_yes_button")) {
            int bx = (int) (cam.x() - 160);
            int by = (int) (cam.y() + 140);
            if (x >= bx && x <= bx + 120 && y >= by && y <= by + 50) {
                GameOverUI.getInstance().reset();
                Game.getInstance().previousScore(0);
                Game.getInstance().restart();
            }
        }
        if (name.equals("ui_no_button")) {
            int bx = (int) (cam.x() + 20);
            int by = (int) (cam.y() + 145);
            if (x >= bx && x <= bx + 120 && y >= by && y <= by + 50) {
                System.exit(0);
            }
        }
    }

    /**
     * Called when the UI element is initialized.
     */
    @Override public void onInit() {}

    /**
     * Called when the UI element is enabled.
     */
    @Override public void onEnabled() {}

    /**
     * Called when the UI element is disabled.
     */
    @Override public void onDisabled() {}

    /**
     * Called when the UI element is destroyed.
     */
    @Override public void onDestroy() {}

    /**
     * Handles collision events for the UI element (not used for UI).
     * @param gol list of game objects collided with
     */
    @Override public void onCollision(List<IGameObject> gol) {}

    /**
     * Gets the owner game object of this behaviour.
     * @return the owner game object
     */
    @Override public IGameObject gameObject() { return owner; }

    /**
     * Sets the owner game object of this behaviour.
     * @param go the owner game object
     */
    @Override public void gameObject(IGameObject go) { this.owner = go; }
}