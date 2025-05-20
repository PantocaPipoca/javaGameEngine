package Game.Entities.Player;

import java.util.List;

import Figures.Point;
import Game.Entities.Commons.Entity;
import Game.Entities.Commons.EntityUtils;
import Game.Entities.Commons.Health;
import Game.Entities.Commons.KnockbackState;
import Game.Entities.Commons.StunnedState;
import Game.Entities.Player.PlayerStates.*;
import GameEngine.*;

import java.awt.event.KeyEvent;

/**
 * Class that represents the player entity in the game.
 * Handles health, state, weapons, animation, and collision logic.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Player must always have a valid health manager and state machine.
 */
public class Player extends Entity {

    private float score;

    /**
     * Constructs a player with the specified health, movement speed, and rolling speed.
     * @param health the health manager
     * @param movingSpeed the movement speed
     * @param rollingSpeed the rolling speed
     */
    public Player(Health health, double movingSpeed, double rollingSpeed) {
        super(health);
        this.score = 0;

        stateMachine.addState("Idle", new IdleState());
        stateMachine.addState("Moving", new MovingState(movingSpeed));
        stateMachine.addState("Rolling", new RollingState());
        stateMachine.addState("Stunned", new StunnedState(0.2));
        stateMachine.addState("Dead", new DeadState());
        stateMachine.addState("Knocked", new KnockbackState(0.2));

        stateMachine.setDefaultState("Idle");
    }

    /////////////////////////////////////////////////// IBehaviour Methods ///////////////////////////////////////////////////

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        animator.update((float) dT);
        if (ie.isMouseButtonPressed(3)) {
            System.out.println(go.transform().position());
        }
        if (go != null) {
            go.setShape(animator.getCurrentShape());
            go.update();
            lastSafePos = go.transform().position();
            setTargetPos(new Point(ie.getMouseWorldPosition().getX(), ie.getMouseWorldPosition().getY()));
            currentGun.updateRotation(targetPos);
            stateMachine.onUpdate(dT, ie);
            go.update();
        }

    }

    @Override
    public void onCollision(List<IGameObject> gol) {
        boolean knocked = false;
        for (IGameObject other : gol) {
            stateMachine.onCollision(other);
            if ((other.name().startsWith("gunner") ||
                other.name().startsWith("bomber") ||
                other.name().startsWith("striker")) && !knocked) {
                EntityUtils.calculateKnockback(this, other, 20, 0.3);
                stateMachine.setState("Knocked");
                knocked = true;
            }
            if (other.name().equals("wall")) {
                resolveAgainst(other);
                System.out.println("Player colliding with wall: " + other.name());
            }
        }
    }

    /////////////////////////////////////////////////// Player Logic ///////////////////////////////////////////////////

    /**
     * Adds score to the player.
     * @param score the score to add
     */
    public void addScore(float score) {
        this.score += score;
    }

    /**
     * Gets the player's score.
     * @return the score
     */
    public float getScore() {
        return score;
    }

    /**
     * Plays the specified animation.
     * @param name the animation name
     */
    public void playAnimation(String name) {
        animator.play(name);
    }

    /**
     * Loads player animations.
     */
    private void loadAnimations() {
        animator.addAnimation("walk", Shape.loadAnimation("player_walk", 8, (int) go.transform().scale()));
        animator.addAnimation("idle", Shape.loadAnimation("player_idle", 5, (int) go.transform().scale()));
    }

    @Override
    public void gameObject(IGameObject go) {
        this.go = (GameObject) go;
        this.stateMachine.setOwner(this);
        loadAnimations();
    }
}