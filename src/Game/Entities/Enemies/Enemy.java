package Game.Entities.Enemies;

import java.util.List;

import Game.Entities.Entity;
import Game.Entities.EntityUtils;
import Game.Entities.Health;
import Game.Entities.KnockbackState;
import Game.Entities.StunnedState;
import GameEngine.*;

/**
 * Abstract class that represents a generic enemy entity in the game.
 * Handles health, state, weapons, animation, and collision logic.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Enemy must always have a valid health manager and state machine.
 */
public abstract class Enemy extends Entity {

    /**
     * Constructs an enemy with the specified health manager.
     * @param health the health manager
     */
    public Enemy(Health health) {
        super(health);
        stateMachine.addState("Stunned", new StunnedState(1.5));
        stateMachine.addState("Knocked", new KnockbackState(0.2));
    }

    /**
     * Updates the enemy's state and position.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update();
        lastSafePos = go.transform().position();
        stateMachine.onUpdate(dT, ie);
        go.update();
    }

    @Override
    public void onCollision(List<IGameObject> gol) {
        boolean knocked = false;
        for (IGameObject other : gol) {
            if ((other.name().startsWith("bullet") || other.name().equals("player")) && !knocked) {
                EntityUtils.calculateKnockback(this, other, 10, 0.2);
                stateMachine.setState("Knocked");
                knocked = true;
            }
            if (other.name().equals("wall")) {
                resolveAgainst(other);
            }
        }
    }
}