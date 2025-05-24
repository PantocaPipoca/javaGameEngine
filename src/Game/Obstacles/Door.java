package Game.Obstacles;

import GameEngine.IGameObject;
import GameEngine.IBehaviour;
import GameEngine.InputEvent;
import Game.Game;

import java.util.List;

/**
 * Behaviour for Door in the game.
 * Handles player interaction and room progression.
 * The door allows the player to proceed to the next room when all enemies are defeated.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv The camera must be initialized with a GUI before use.
 */
public class Door implements IBehaviour {
    private IGameObject go;

    ////////////////////// IBehaviour Methods //////////////////////

    /**
     * Initializes the door.
     */
    @Override
    public void onInit() {}

    /**
     * Called when the door is enabled.
     */
    @Override
    public void onEnabled() {}

    /**
     * Called when the door is disabled.
     */
    @Override
    public void onDisabled() {}

    /**
     * Called when the door is destroyed.
     */
    @Override
    public void onDestroy() {}

    /**
     * Updates the door each frame.
     * @param dT delta time since last update
     * @param ie input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {}

    /**
     * Handles collision with other game objects.
     * If the player collides with the door and all enemies are defeated,
     * loads the next room.
     * @param others list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> others) {
        for (IGameObject other : others) {
            if (other.name().equals("player") && Game.getInstance().currentEnemyCount() <= 0) {
                Game game = Game.getInstance();
                game.loadRoom(game.currentRoomIndex() + 1);
            }
        }
    }

    ////////////////////// Getters //////////////////////

    /**
     * Gets the game object associated with this door.
     * @return the game object
     */
    public IGameObject gameObject() {
        return go;
    }

    ////////////////////// Setters //////////////////////

    /**
     * Sets the game object associated with this door.
     * @param go the game object
     */
    public void gameObject(IGameObject go) {
        this.go = go;
    }
}