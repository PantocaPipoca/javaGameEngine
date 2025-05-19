package Game.Gun;

import java.util.List;

import Game.Entities.Commons.Entity;
import GameEngine.IGameObject;

public class Bomb extends Weapon {
    private double blastRadius;
    private int blastDamage;

    public Bomb(IGameObject owner, String name, double damage, double fireRate, double blastRadius, int blastDamage, double distanceFromOwner) {
        super(owner, name, damage, fireRate, distanceFromOwner);
        this.blastRadius = blastRadius;
        this.blastDamage = blastDamage;
    }

    @Override
    public void onCollision(List<IGameObject> gol) {
        for (IGameObject go : gol) {
            if(go.name().equals("player") ||
               go.name().startsWith("gunner") ||
               go.name().contains("bomber") ||
               go.name().startsWith("striker")) {

                double distance = go.transform().position().distance(owner.transform().position());
                if (distance > blastRadius) {
                    continue;
                }

                    Entity entity = (Entity) go;
                    entity.getHealthManager().takeDamage(blastDamage);
            }
        }
    }
}
