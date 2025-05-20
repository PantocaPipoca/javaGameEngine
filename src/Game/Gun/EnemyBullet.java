package Game.Gun;

import java.util.List;

import GameEngine.GameEngine;
import GameEngine.IGameObject;

/**
 * Class that represents an enemy bullet.
 * Handles the bullet's collision with other game objects.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class EnemyBullet extends Bullet {
    /**
     * Constructs an enemy bullet with the specified rotation and speed.
     * @param rotation the rotation of the bullet
     * @param speed the speed of the bullet
     */
    public EnemyBullet(double rotation, double speed) {
        super(rotation, speed);
    }

    /**
     * Handles the collision of the bullet with other game objects.
     * If the bullet collides with any object that is not itself and is not an enemy,
     * it is destroyed.
     * @param gol List of game objects to check for collision
     */
    @Override
    public void onCollision(List<IGameObject> gol) {
        for (IGameObject other : gol) {
            // Ignore self and enemies
            if (!other.name().contains("ullet") && !other.name().startsWith("gunner") && !other.name().startsWith("bomber") && !other.name().startsWith("striker") && !other.name().equals("bomb")) {
                GameEngine.getInstance().destroy(go);
                break;
            }
        }
    }
}