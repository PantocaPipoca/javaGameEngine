package Game.Gun;

import java.util.List;

import Game.Game;
import Game.Audio.SoundPlayer;
import Game.Entities.Commons.Entity;
import GameEngine.GameEngine;
import GameEngine.IGameObject;

public class Bomb extends Weapon {
    private double blastDamage;
    private boolean exploded = false;
    private boolean destroyed = false;

    public Bomb(IGameObject owner, String name, double blastDamage, double fireRate, double distanceFromOwner) {
        super(owner, name, blastDamage, fireRate, distanceFromOwner);
        this.blastDamage = blastDamage;
    }

    @Override
    public boolean shoot() {
        if (!super.shoot()) return false;
        exploded = true;
        return true;
    }

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
            GameEngine.getInstance().destroy(owner);
            Game.getInstance().currentEnemyCount(Game.getInstance().currentEnemyCount() - 1);
        }
    }
}
