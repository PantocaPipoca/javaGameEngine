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
 * @author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version: 1.0 (17/05/25)
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
     * @param damage weapon damage (not used in base class)
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

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    public IGameObject gameObject() { return go; }
    public void gameObject(IGameObject go) { this.go = (GameObject) go; }
    public String name() { return name; }
    public double fireRate() { return fireRate; }

    ///////////////////////////////////////////////////IBehaviour Methods///////////////////////////////////////////////////

    @Override
    public void onInit() {}

    @Override
    public void onEnabled() {}

    @Override
    public void onDisabled() {}

    @Override
    public void onDestroy() {}

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update();
        if (fireCooldown > 0) {
            fireCooldown -= dT;
            if (fireCooldown < 0) fireCooldown = 0;
        }
    }

    @Override
    public void onCollision(List<IGameObject> gol) {}
}