package Game.Entities.Commons;

import Figures.Point;

import GameEngine.IGameObject;

public class EntityUtils {
    
    public static void calculateKnockback(Entity entity, IGameObject other, double strength, double duration) {
        // Calculate knockback direction (from other to this enemy)
        Point myPos = entity.gameObject().transform().position();
        Point otherPos = other.transform().position();
        double dx = myPos.x() - otherPos.x();
        double dy = myPos.y() - otherPos.y();
        double len = Math.sqrt(dx*dx + dy*dy);
        if (len == 0) len = 1;
        dx /= len;
        dy /= len;

        // Use the KnockbackState logic
        KnockbackState ks = (KnockbackState) entity.getStateMachine().getState("Knocked");
        ks.knockbackStart(dx, dy, strength, duration);
    }
}
