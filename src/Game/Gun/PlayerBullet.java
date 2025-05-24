package Game.Gun;

import java.util.List;

import GameEngine.GameEngine;
import GameEngine.IGameObject;

/**
 * Class that represents a player bullet.
 * Handles the bullet's collision with other game objects.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class PlayerBullet extends Bullet {
    /**
     * Constructs a player bullet with the specified rotation and speed.
     * @param rotation the rotation of the bullet
     * @param speed the speed of the bullet
     */
    public PlayerBullet(double rotation, double speed) {
        super(rotation, speed);
    }

    /**
     * Handles the collision of the bullet with other game objects.
     * If the bullet collides with any object that is not itself, the player, or a bomb, it is destroyed.
     * @param gol List of game objects to check for collision
     */
    @Override
    public void onCollision(List<IGameObject> gol) {
        for (IGameObject other : gol) {
            if (!other.name().contains("ullet") && !other.name().equals("player") && !other.name().equals("bomb")) {
                System.out.println("Player bullet destroyed by" + other.name());
                GameEngine.getInstance().destroy(go);
                break;
            }
        }
    }
}