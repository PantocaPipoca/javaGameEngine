package Game.Gun;

import java.util.ArrayList;
import java.util.List;

import Figures.Circle;
import Figures.Point;
import Game.Observer.GameListener;
import Game.Observer.GamePublisher;
import GameEngine.GameObject;
import GameEngine.IGameObject;
import GameEngine.Transform;
import GameEngine.InputEvent;

/**
 * Represents a generic gun weapon with ammo, reloading, and shooting logic.
 * Extends Weapon and provides bullet firing and reload mechanics.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Gun always has positive magazine size and max ammo.
 */
public class Gun extends Weapon implements GamePublisher {
    protected double bulletSpeed;
    protected double reloadTime;
    protected int magazineSize;
    protected int maxAmmo;

    protected int currentAmmo;
    protected int reserveAmmo;
    protected boolean isReloading = false;
    protected double reloadTimer = 0.0;

    private final List<GameListener> listeners = new ArrayList<>();

    /**
     * Constructs a gun with the specified parameters.
     * @param owner the owning game object
     * @param name weapon name
     * @param bulletSpeed speed of the bullet
     * @param damage damage per shot
     * @param fireRate shots per second
     * @param reloadTime time to reload
     * @param magazineSize bullets per magazine
     * @param maxAmmo maximum ammo
     * @param distanceFromOwner distance from owner to orbit
     * @throws IllegalArgumentException if magazineSize or maxAmmo is not positive
     */
    public Gun(IGameObject owner, String name, double bulletSpeed, double damage, double fireRate, double reloadTime, int magazineSize, int maxAmmo, double distanceFromOwner) {
        super(owner, name, damage, fireRate, distanceFromOwner);
        if (magazineSize <= 0 || maxAmmo <= 0) {
            throw new IllegalArgumentException("Gun: magazineSize and maxAmmo must be positive.");
        }
        this.bulletSpeed = bulletSpeed;
        this.reloadTime = reloadTime;
        this.magazineSize = magazineSize;
        this.maxAmmo = maxAmmo;
        this.currentAmmo = magazineSize;
        this.reserveAmmo = maxAmmo - magazineSize;
    }

    /**
     * Attempts to shoot a bullet from the gun.
     * @return true if a bullet was fired, false otherwise
     */
    @Override
    public boolean shoot() {
        if (!super.shoot()) {
            return false;
        }
        if (isReloading) {
            return false;
        }
        if (currentAmmo <= 0) {
            reload();
            return false;
        }
        // Calculate the bullet's initial position (at the tip of the gun)
        Point ownerPosition = owner.transform().position();
        double rotation = Math.toRadians(go.transform().angle());
        double bulletX = ownerPosition.x() + Math.cos(rotation);
        double bulletY = ownerPosition.y() + Math.sin(rotation);

        // Create a new bullet object
        Bullet bullet;
        GameObject bulletObject;
        if (owner.name().equals("player")) {
            bullet = new PlayerBullet(rotation, bulletSpeed);
            bulletObject = new GameObject("bullet", new Transform(new Point(bulletX, bulletY), 1, 0, 1), new Circle("0 0 10"), bullet);
        } else {
            bullet = new EnemyBullet(rotation, bulletSpeed);
            bulletObject = new GameObject("enemyBullet", new Transform(new Point(bulletX, bulletY), 1, 0, 1), new Circle("0 0 10"), bullet);
        }
        bullet.gameObject(bulletObject);
        gameEngine.addEnabled(bulletObject);

        currentAmmo--;
        publishAmmoChanged();
        return true;
    }

    /**
     * Updates the gun's state, including reloading logic.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        super.onUpdate(dT, ie);
        if (isReloading) {
            reloadTimer -= dT;
            if (reloadTimer <= 0) {
                int ammoNeeded = magazineSize - currentAmmo;
                int ammoToLoad = Math.min(ammoNeeded, reserveAmmo);
                currentAmmo += ammoToLoad;
                reserveAmmo -= ammoToLoad;
                isReloading = false;
                publishAmmoChanged();
            }
        }
    }

    /**
     * Starts reloading the gun if possible.
     */
    public void reload() {
        if (!isReloading && reserveAmmo > 0 && currentAmmo < magazineSize) {
            isReloading = true;
            reloadTimer = reloadTime;
        }
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    public void setBulletSpeed(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }
    public int getReserveAmmo() {
        return reserveAmmo;
    }

    ////////////////////////////////////////////////////// Observer Methods ///////////////////////////////////////////////////
    @Override
    public void subscribe(GameListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unsubscribe(GameListener listener) {
        listeners.remove(listener);
    }

    private void publishAmmoChanged() {
        for (GameListener l : listeners) {
            l.onAmmoChanged(getCurrentAmmo(), getReserveAmmo());
        }
    }
}