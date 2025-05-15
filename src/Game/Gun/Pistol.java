package Game.Gun;

import Figures.Circle;
import Figures.Point;
import GameEngine.GameObject;
import GameEngine.IGameObject;
import GameEngine.Transform;

public class Pistol extends Gun {
    private double bulletSpeed = 500.0; // Speed of the bullets

    public Pistol(IGameObject owner) {
        super(owner, "Pistol");
    }

    @Override
    public void shoot() {
        if (go == null) {
            System.out.println("Gun is not initialized.");
            return;
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

    }

    public void setBulletSpeed(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }
}
