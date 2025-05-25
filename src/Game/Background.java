package Game;

import java.util.List;
import GameEngine.*;

/**
 * Behaviour class for the game background.
 * Responsible for managing background-specific logic and integration with the game engine.
 * Implements the IBehaviour interface.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Background must be attached to a valid GameObject.
 */
public class Background implements IBehaviour {

    private IGameObject go;

    ///////////////////////////// IBehaviour Methods /////////////////////////////

    /**
     * Updates the background each frame.
     * @param dT delta time since last update
     * @param ie input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {}

    /**
     * Called when the background is initialized.
     */
    @Override
    public void onInit() {}

    /**
     * Called when the background is enabled.
     */
    @Override
    public void onEnabled() {}

    /**
     * Called when the background is disabled.
     */
    @Override
    public void onDisabled() {}

    /**
     * Called when the background is destroyed.
     */
    @Override
    public void onDestroy() {}

    /**
     * Handles collision events for the background (not used).
     * @param gol list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {}

    ///////////////////////////// Getters and Setters /////////////////////////////

    /**
     * Gets the owner game object of this behaviour.
     * @return the owner game object
     */
    @Override
    public IGameObject gameObject() {
        return go;
    }

    /**
     * Sets the owner game object of this behaviour.
     * @param go the owner game object
     */
    @Override
    public void gameObject(IGameObject go) {
        this.go = go;
    }
}