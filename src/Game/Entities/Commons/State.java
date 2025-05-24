package Game.Entities.Commons;

import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Abstract base class for all entity states.
 * Provides hooks for state initialization, update, entry, exit, and collision handling.
 * @author Daniel Pantyukhov
 * @version 1.0 (17/05/25)
 * @inv State must always have a valid stateMachine and owner before use.
 */
public abstract class State {

    protected StateMachine stateMachine;
    protected IEntity owner;

    /////////////////////////////////////////////////// Initialization ///////////////////////////////////////////////////

    /**
     * Initializes the state with its state machine and owner.
     * @param stateMachine the state machine managing this state
     * @param owner the entity that owns this state
     */
    public void onInit(StateMachine stateMachine, IEntity owner) {
        this.stateMachine = stateMachine;
        this.owner = owner;
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Called every frame to update the state.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    public abstract void onUpdate(double dT, InputEvent ie);

    /**
     * Called when entering this state.
     * Checks that stateMachine and owner are initialized.
     * @throws IllegalStateException if stateMachine or owner is not initialized
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
     * @throws IllegalArgumentException if the colliding object is null
     */
    public void onCollision(IGameObject other) {
        if (other == null) {
            throw new IllegalArgumentException("Colliding object cannot be null.");
        }
    }
}