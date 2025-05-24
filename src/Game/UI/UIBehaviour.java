package Game.UI;

import GameEngine.IBehaviour;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

import java.util.List;

/**
 * Passive behaviour used for UI GameObjects.
 * Does not react to collisions or require per-frame logic.
 * Implements all IBehaviour methods as no-ops.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class UIBehaviour implements IBehaviour {

    private IGameObject owner;

    /**
     * Updates the UI object each frame (no-op).
     * @param dT delta time since last update
     * @param ie input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {}

    /**
     * Initializes the UI object (no-op).
     */
    @Override
    public void onInit() {}

    /**
     * Called when the UI object is enabled (no-op).
     */
    @Override
    public void onEnabled() {}

    /**
     * Called when the UI object is disabled (no-op).
     */
    @Override
    public void onDisabled() {}

    /**
     * Called when the UI object is destroyed (no-op).
     */
    @Override
    public void onDestroy() {}

    /**
     * Handles collision with other game objects (no-op).
     * @param gol the list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {}

    /**
     * Gets the game object associated with this UI behaviour.
     * @return the game object
     */
    public IGameObject gameObject() {
        return owner;
    }

    /**
     * Sets the game object associated with this UI behaviour.
     * @param go the game object
     */
    public void gameObject(IGameObject go) {
        this.owner = go;
    }
}