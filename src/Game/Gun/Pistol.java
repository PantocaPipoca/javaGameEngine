package Game.Gun;

import GameEngine.IGameObject;

public class Pistol extends Gun {

    public Pistol(IGameObject owner, double bulletSpeed, double damage, double fireRate, double reloadTime, int magazineSize, int maxAmmo, double distanceFromOwner) {
        super(owner, "pistol", bulletSpeed, damage, fireRate, reloadTime, magazineSize, maxAmmo, distanceFromOwner);

    }
}
