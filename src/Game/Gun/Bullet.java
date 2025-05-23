package Game.Gun;

import java.util.List;

import Figures.Point;
import GameEngine.IGameObject;
import GameEngine.InputEvent;
import GameEngine.GameEngine;
import GameEngine.IBehaviour;

/**
 * Represents a bullet fired from a gun.
 * Handles movement, lifetime, and collision destruction.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Bullet lifetime is always positive.
 */
public class Bullet implements IBehaviour {
    private double rotation;
    private double speed;
    protected IGameObject go;
    private final GameEngine gameEngine = GameEngine.getInstance();
    private final double lifeTime = 0.7;
    private double timeAlive = 0.0; // Time counter

    /**
     * Constructs a bullet with the given rotation and speed.
     * @param rotation the angle in radians
     * @param speed the speed of the bullet
     */
    public Bullet(double rotation, double speed) {
        this.rotation = rotation;
        this.speed = speed;
    }

    /////////////////////////////////////////////////// IBehaviour Methods ///////////////////////////////////////////////////

    /**
     * Updates the bullet's position and destroys it after its lifetime expires.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        double dx = Math.cos(rotation) * speed * dT;
        double dy = Math.sin(rotation) * speed * dT;
        go.transform().move(new Point(dx, dy), 0);
        go.update();

        timeAlive += dT;
        if (timeAlive >= lifeTime) {
            gameEngine.destroy(go);
        }
    }

    /**
     * Handles collision with other game objects.
     * @param gol the list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {}

    /**
     * Initializes the bullet.
     */
    @Override
    public void onInit() {}

    /**
     * Called when the bullet is enabled.
     */
    @Override
    public void onEnabled() {}

    /**
     * Called when the bullet is disabled.
     */
    @Override
    public void onDisabled() {}

    /**
     * Called when the bullet is destroyed.
     */
    @Override
    public void onDestroy() {}

    /////////////////////////////////////////////////// Getters and Setters ///////////////////////////////////////////////////

    /**
     * Gets the game object associated with this bullet.
     * @return the game object
     */
    public IGameObject gameObject() {
        return go;
    }

    /**
     * Sets the game object associated with this bullet.
     * @param go the game object
     */
    public void gameObject(IGameObject go) {
        this.go = go;
    }
}