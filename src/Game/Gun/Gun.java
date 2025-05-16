package Game.Gun;

import Figures.Circle;
import Figures.Point;
import GameEngine.GameObject;
import GameEngine.IGameObject;
import GameEngine.Transform;
import GameEngine.InputEvent;

public class Gun extends Weapon {
    protected double bulletSpeed;
    protected double reloadTime;
    protected int magazineSize;
    protected int maxAmmo;

    protected int currentAmmo;
    protected int reserveAmmo;
    protected boolean isReloading = false;
    protected double reloadTimer = 0.0;


    public Gun(IGameObject owner, String name, double bulletSpeed, double damage, double fireRate, double reloadTime, int magazineSize, int maxAmmo, double distanceFromOwner) {
        super(owner, name, damage, fireRate, distanceFromOwner);
        this.bulletSpeed = bulletSpeed;
        this.reloadTime = reloadTime;
        this.magazineSize = magazineSize;
        this.maxAmmo = maxAmmo;
        this.currentAmmo = magazineSize;
        this.reserveAmmo = maxAmmo - magazineSize;
    }

    public boolean shoot() {
        if(!super.shoot()) {
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
        Bullet bullet = new Bullet(rotation, bulletSpeed);

        GameObject bulletObject = new GameObject("bullet", new Transform(new Point(bulletX, bulletY), 1, 0, 1), new Circle("0 0 10"), bullet);
        bullet.gameObject(bulletObject);
        gameEngine.addEnabled(bulletObject);

        currentAmmo--;
        return true;

    }

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
            }
        }
    }

    public void reload() {
        if (!isReloading && reserveAmmo > 0 && currentAmmo < magazineSize) {
            isReloading = true;
            reloadTimer = reloadTime;
        }
    }
    

    public void setBulletSpeed(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }
    
}
