package Game.Gun;

import java.util.List;

import Game.Audio.SoundPlayer;
import Game.Entities.Commons.Entity;
import Game.Entities.Commons.IEntity;
import GameEngine.GameEngine;
import GameEngine.IGameObject;

/**
 * Represents a bomb weapon that can be shot and explodes on collision.
 * Deals blast damage to entities and destroys itself after exploding.
 * Also sets the bomber entity to the "Dead" state after explosion.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class Bomb extends Weapon {
    private double blastDamage;
    private boolean exploded = false;
    private boolean destroyed = false;

    /**
     * Constructs a Bomb with the specified parameters.
     * @param owner the game object that owns this bomb
     * @param name the name of the bomb
     * @param blastDamage the damage dealt by the bomb explosion
     * @param fireRate the fire rate of the bomb
     * @param distanceFromOwner the distance from the owner when spawned
     */
    public Bomb(IGameObject owner, String name, double blastDamage, double fireRate, double distanceFromOwner) {
        super(owner, name, blastDamage, fireRate, distanceFromOwner);
        this.blastDamage = blastDamage;
    }

    /**
     * Shoots the bomb, marking it as exploded.
     * @return true if the bomb was shot, false otherwise
     */
    @Override
    public boolean shoot() {
        if (!super.shoot()) return false;
        exploded = true;
        return true;
    }

    /**
     * Handles collision with other game objects.
     * Plays explosion sound, applies damage, and destroys the bomb.
     * @param gol the list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {
        if (!exploded) return; // Only explode if bomb was shot
        SoundPlayer.playSound("songs/explosion.wav");
        for (IGameObject go : gol) {
            if(go.name().equals("player")) {
                System.out.println("Bomb: onCollision with " + go.name());
                Entity entity = (Entity) go.behaviour();
                entity.getHealthManager().takeDamage((int) blastDamage);
            }
        }
        // Destroy the bomb after explosion
        if (this.go != null && !destroyed) {
            destroyed = true;
            GameEngine.getInstance().destroy(this.go);
            IEntity bomber = (IEntity) owner.behaviour();
            if (bomber != null) {
                bomber.getStateMachine().setState("Dead");
            }
        }
    }
}