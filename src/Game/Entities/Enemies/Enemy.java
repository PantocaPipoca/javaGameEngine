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
 * Responsible for handling health, state, weapons, animation, and collision logic.
 * Subclasses must implement animation loading.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.1 (25/05/25)
 * @inv Enemy must always have a valid health manager and state machine.
 */
public abstract class Enemy extends Entity {

    /** Reference to the player game object for targeting */
    private IGameObject player;

    /////////////////////////////////////////////////// Constructors ///////////////////////////////////////////////////

    /**
     * Constructs an enemy with the specified health manager and player reference.
     * Adds default states for Stunned and Knocked.
     * @param health the health manager
     * @param player the player game object to target
     */
    public Enemy(Health health, IGameObject player) {
        super(health);
        this.player = player;
        stateMachine.addState("Stunned", new StunnedState(1.5));
        stateMachine.addState("Knocked", new KnockbackState(0.2));
    }

    /////////////////////////////////////////////////// Logic ///////////////////////////////////////////////////

    /**
     * Updates the enemy's state and position.
     * Sets the target position to the player and delegates to Entity's update logic.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        setTargetPos(player.transform().position());
        super.onUpdate(dT, ie);
    }

    /**
     * Handles collision with other game objects.
     * Handles wall collision, knockback from player, and damage from bullets.
     * @param gol list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {
        boolean knocked = false;
        for (IGameObject other : gol) {
            // Handle wall collision
            if (other.name().equals("wall")) {
                resolveAgainst(other);
                continue;
            }
            // Ignore further logic if already dead
            if(stateMachine.getCurrentStateName().equals("Dead")) {
                continue;
            }
            // Handle knockback and damage from player or bullets
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

    /////////////////////////////////////////////////// Animation ///////////////////////////////////////////////////

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
     * Sets the game object associated with the enemy and loads animations.
     * @param go the game object
     */
    @Override
    public void gameObject(IGameObject go) {
        this.go = (GameObject) go;
        this.stateMachine.setOwner(this);
        loadAnimations();
    }
}