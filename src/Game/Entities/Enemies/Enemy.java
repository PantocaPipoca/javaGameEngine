package Game.Entities.Enemies;

import java.util.List;

import Game.Entities.Commons.Entity;
import Game.Entities.Commons.EntityUtils;
import Game.Entities.Commons.Health;
import Game.Entities.Commons.KnockbackState;
import Game.Entities.Commons.StunnedState;
import GameEngine.*;

/**
 * Abstract class that represents a generic enemy entity in the game.
 * Handles health, state, weapons, animation, and collision logic.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Enemy must always have a valid health manager and state machine.
 */
public abstract class Enemy extends Entity {

    private IGameObject player;

    /**
     * Constructs an enemy with the specified health manager and player reference.
     * @param health the health manager
     * @param player the player game object to target
     */
    public Enemy(Health health, IGameObject player) {
        super(health);
        this.player = player;
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
        animator.update((float) dT);
        go.setShape(animator.currentShape());
        go.update();
        lastSafePos = go.transform().position();
        if (!healthManager.isAlive() && !stateMachine.getCurrentStateName().equals("Dead")) {
            stateMachine.setState("Dead");
            return;
        }
        setTargetPos(player.transform().position());
        if (currentGun != null) {
            currentGun.updateRotation(targetPos);

            double gunX = currentGun.gameObject().transform().position().x();
            double bodyX = go.transform().position().x();
            go.setFlip(gunX < bodyX);
        }
        stateMachine.onUpdate(dT, ie);
        go.update();
    }

    /**
     * Handles collision with other game objects.
     * @param gol list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {
        boolean knocked = false;
        for (IGameObject other : gol) {
            if (other.name().equals("wall")) {
                resolveAgainst(other);
                continue;
            }
            if(stateMachine.getCurrentStateName().equals("Dead")) {
                continue;
            }
            if(!knocked) {
                knocked = true;
                if (other.name().equals("player")) {
                    EntityUtils.calculateKnockback(this, other, 200, 0.3);
                    stateMachine.setState("Knocked");
                    continue;
                }
                if (other.name().equals("bullet")) {
                    healthManager.takeDamage(10);
                    stateMachine.setState("Stunned");
                    continue;
                }
            }
        }
    }

    /**
     * Plays the specified animation for the enemy.
     * @param name the animation name
     */
    public void playAnimation(String name) {
        animator.play(name);
    }

    /**
     * Loads enemy animations.
     * Implemented by subclasses.
     */
    protected abstract void loadAnimations();

    /**
     * Sets the game object associated with the enemy.
     * @param go the game object
     */
    @Override
    public void gameObject(IGameObject go) {
        this.go = (GameObject) go;
        this.stateMachine.setOwner(this);
        loadAnimations();
    }
}