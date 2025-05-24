package Game.Entities.Commons;

import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Represents the stunned state for an entity.
 * When stunned, the entity cannot act until the stun duration expires.
 * Automatically resets to the default state after the timer runs out.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class StunnedState extends State {

    private double stunDuration;
    private double timer = 0.0;

    /////////////////////////////////////////////////// Constructor ///////////////////////////////////////////////////

    /**
     * Constructs a StunnedState with the specified stun duration.
     * @param stunDuration duration of the stun in seconds
     */
    public StunnedState(double stunDuration) {
        this.stunDuration = stunDuration;
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the stun timer and resets state when finished.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        timer -= dT;
        if (timer <= 0) {
            stateMachine.resetToDefault();
        }
    }

    /**
     * Called when entering the stunned state.
     * Resets the timer and stops the owner's animation.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        timer = stunDuration;
        owner.getAnimator().stopAnimation();
    }

    /**
     * Called when exiting the stunned state.
     */
    @Override
    public void onExit() {
    }

    /**
     * Handles collision while stunned.
     * @param other the other game object collided with
     */
    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
    }
}