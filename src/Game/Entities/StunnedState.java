package Game.Entities;

import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Represents the stunned state for an entity.
 * When stunned, the entity cannot act until the stun duration expires.
 * Automatically resets to the default state after the timer runs out.
 * @author
 * @version 1.0
 */
public class StunnedState extends State {

    private double stunDuration;
    private double timer = 0.0;

    /**
     * Constructs a StunnedState with the specified stun duration.
     * @param stunDuration duration of the stun in seconds
     */
    public StunnedState(double stunDuration) {
        this.stunDuration = stunDuration;
    }

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
     * Resets the timer.
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
        // No special logic needed on exit
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