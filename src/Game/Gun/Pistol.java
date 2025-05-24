package Game.Gun;

import GameEngine.IGameObject;

/**
 * Class that represents a pistol weapon.
 * Inherits all shooting and reloading logic from Gun.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class Pistol extends Gun {

    /**
     * Constructs a pistol with the specified parameters.
     * @param owner the owning game object
     * @param bulletSpeed speed of the bullet
     * @param damage damage per shot
     * @param fireRate shots per second
     * @param reloadTime time to reload
     * @param magazineSize bullets per magazine
     * @param maxAmmo maximum ammo
     * @param distanceFromOwner distance from owner to orbit
     */
    public Pistol(IGameObject owner, double bulletSpeed, double damage, double fireRate, double reloadTime, int magazineSize, int maxAmmo, double distanceFromOwner) {
        super(owner, "pistol", bulletSpeed, damage, fireRate, reloadTime, magazineSize, maxAmmo, distanceFromOwner);
    }
}