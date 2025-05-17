package Game.Entities;

import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Abstract base class for all entity states.
 * Provides hooks for state initialization, update, entry, exit, and collision handling.
 * @author Daniel Pantyukhov
 * @version 1.0
 */
public abstract class State {

    protected StateMachine stateMachine;
    protected IEntity owner;

    /**
     * Initializes the state with its state machine and owner.
     * @param stateMachine the state machine managing this state
     * @param owner the entity that owns this state
     */
    public void onInit(StateMachine stateMachine, IEntity owner) {
        this.stateMachine = stateMachine;
        this.owner = owner;
    }

    /**
     * Called every frame to update the state.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    public abstract void onUpdate(double dT, InputEvent ie);

    /**
     * Called when entering this state.
     * Checks that stateMachine and owner are initialized.
     */
    public void onEnter() {
        if (stateMachine == null || owner == null) {
            throw new IllegalStateException("StateMachine or owner is not initialized.");
        }
    }

    /**
     * Called when exiting this state.
     */
    public abstract void onExit();

    /**
     * Handles collision with another game object.
     * @param other the colliding game object
     */
    public void onCollision(IGameObject other) {
        if (other == null) {
            throw new IllegalArgumentException("Colliding object cannot be null.");
        }
    }
}