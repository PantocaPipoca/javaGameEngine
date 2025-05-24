package Game.Obstacles;

import java.util.List;

import GameEngine.IGameObject;
import GameEngine.InputEvent;
import GameEngine.IBehaviour;

/**
 * Behaviour for wall obstacles in the game.
 * Walls are static obstacles that block movement and do not interact with other objects.
 * 
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv The camera must be initialized with a GUI before use.
 */
public class Wall implements IBehaviour {

    private IGameObject go;

    ////////////////////// IBehaviour Methods //////////////////////

    /**
     * Updates the wall each frame.
     * @param dT delta time since last update
     * @param ie input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {}

    /**
     * Initializes the wall.
     */
    @Override
    public void onInit() {}

    /**
     * Called when the wall is enabled.
     */
    @Override
    public void onEnabled() {}

    /**
     * Called when the wall is disabled.
     */
    @Override
    public void onDisabled() {}

    /**
     * Called when the wall is destroyed.
     */
    @Override
    public void onDestroy() {}

    /**
     * Handles collision with other game objects.
     * @param gol the list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {}

    ////////////////////// Getters //////////////////////

    /**
     * Gets the game object associated with this wall.
     * @return the game object
     */
    public IGameObject gameObject() {
        return go;
    }

    ////////////////////// Setters //////////////////////

    /**
     * Sets the game object associated with this wall.
     * @param go the game object
     */
    public void gameObject(IGameObject go) {
        this.go = go;
    }
}