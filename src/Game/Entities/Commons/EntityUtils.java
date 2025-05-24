package Game.Entities.Commons;

import Figures.Point;
import GameEngine.IGameObject;

/**
 * Utility class for entity-related helper methods.
 * Provides static methods for common entity operations.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class EntityUtils {

    /**
     * Calculates and applies knockback to an entity from another game object.
     * @param entity the entity to apply knockback to
     * @param other the other game object causing the knockback
     * @param strength the strength of the knockback
     * @param duration the duration of the knockback
     */
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