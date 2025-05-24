package Game.Gun;

import java.util.List;
import Figures.Point;
import GameEngine.GameEngine;
import GameEngine.GameObject;
import GameEngine.IBehaviour;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Abstract base class for all weapons.
 * Handles rotation, firing logic, and attachment to a game object.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public abstract class Weapon implements IBehaviour {

    protected IGameObject owner;
    protected GameEngine gameEngine;
    protected GameObject go;
    protected double fireRate;
    private double distanceFromOwner;
    private String name;
    protected double fireCooldown = 0.0;

    /**
     * Constructor for a weapon.
     * @param owner the owning game object
     * @param name weapon name
     * @param damage weapon damage
     * @param fireRate shots per second
     * @param distanceFromOwner distance from owner to orbit
     */
    public Weapon(IGameObject owner, String name, double damage, double fireRate, double distanceFromOwner) {
        this.owner = owner;
        this.name = name;
        this.fireRate = fireRate;
        this.gameEngine = GameEngine.getInstance();
        this.distanceFromOwner = distanceFromOwner;
    }

    /**
     * Updates the weapon's rotation and position to follow the mouse.
     * @param mousePosition the mouse position in world coordinates
     */
    public void updateRotation(Point mousePosition) {
        if (go == null) return;
        Point ownerPosition = owner.transform().position();
        double dx = mousePosition.x() - ownerPosition.x();
        double dy = mousePosition.y() - ownerPosition.y();
        double targetRotation = Math.atan2(dy, dx);
        go.transform().angle(Math.toDegrees(targetRotation));
        double gunX = ownerPosition.x() + Math.cos(targetRotation) * distanceFromOwner;
        double gunY = ownerPosition.y() + Math.sin(targetRotation) * distanceFromOwner;
        go.transform().move(
            new Point(gunX - go.transform().position().x(), gunY - go.transform().position().y()), 0);
        double angle = Math.toDegrees(targetRotation);
        if (angle < 0) angle += 360;
        go.setFlip(angle > 90 && angle < 270);
    }

    /**
     * Attempts to shoot the weapon.
     * @return true if the weapon fired, false if still on cooldown or not initialized
     */
    public boolean shoot() {
        if (go == null) return false;
        if (fireCooldown > 0) return false;
        fireCooldown = 1.0 / fireRate;
        return true;
    }

    /////////////////////////////////////////////////// Getters and Setters ///////////////////////////////////////////////////

    /**
     * Gets the game object associated with this weapon.
     * @return the game object
     */
    public IGameObject gameObject() { return go; }

    /**
     * Sets the game object associated with this weapon.
     * @param go the game object
     */
    public void gameObject(IGameObject go) { this.go = (GameObject) go; }

    /**
     * Gets the name of the weapon.
     * @return the weapon name
     */
    public String name() { return name; }

    /**
     * Gets the fire rate of the weapon.
     * @return the fire rate
     */
    public double fireRate() { return fireRate; }

    /////////////////////////////////////////////////// IBehaviour Methods ///////////////////////////////////////////////////

    /**
     * Initializes the weapon.
     */
    @Override
    public void onInit() {}

    /**
     * Called when the weapon is enabled.
     */
    @Override
    public void onEnabled() {}

    /**
     * Called when the weapon is disabled.
     */
    @Override
    public void onDisabled() {}

    /**
     * Called when the weapon is destroyed.
     */
    @Override
    public void onDestroy() {}

    /**
     * Updates the weapon's cooldown and game object each frame.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update();
        if (fireCooldown > 0) {
            fireCooldown -= dT;
            if (fireCooldown < 0) fireCooldown = 0;
        }
    }

    /**
     * Handles collision with other game objects.
     * @param gol the list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {}
}