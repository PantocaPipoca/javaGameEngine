package Game.Gun;

import java.util.List;

import Game.Entities.Commons.Entity;
import GameEngine.GameEngine;
import GameEngine.IGameObject;

public class Bomb extends Weapon {
    private int blastDamage;
    private boolean exploded = false;

    public Bomb(IGameObject owner, String name, double damage, double fireRate, int blastDamage, double distanceFromOwner) {
        super(owner, name, damage, fireRate, distanceFromOwner);
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
        for (IGameObject go : gol) {
            if(go.name().equals("player") ||
               go.name().startsWith("gunner") ||
               go.name().contains("bomber") ||
               go.name().startsWith("striker")) {

                System.out.println("Bomb: onCollision with " + go.name());

                Entity entity = (Entity) go.behaviour();
                entity.getHealthManager().takeDamage(blastDamage);
            }
        }
        // Destroy the bomb after explosion
        if (go != null) {
            GameEngine.getInstance().destroy(go);
        }
    }
}
