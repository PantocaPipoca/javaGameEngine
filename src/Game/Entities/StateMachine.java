package Game.Entities;

import GameEngine.IGameObject;
import GameEngine.InputEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * StateMachine class that manages the states of a game object.
 * It allows transitioning between different states and handles updates.
 * Each state can define its own behavior for entering, exiting, and updating.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv StateMachine always has a valid default state before use.
 */
public class StateMachine {

    private String currentStateName;
    private State currentState;
    private State defaultState;
    private String defaultStateName;
    private IEntity owner;
    private Map<String, State> states = new HashMap<>();

    /**
     * Constructs an empty StateMachine.
     */
    public StateMachine() {
        this.states = new HashMap<String, State>();
    }

    /**
     * Sets the owner entity for this state machine and initializes all states.
     * @param owner the entity that owns this state machine
     */
    public void setOwner(IEntity owner) {
        this.owner = owner;
        for (State s : states.values()) {
            s.onInit(this, owner);
        }
    }

    /**
     * Updates the current state of the state machine.
     * This method should be called every frame for any owner logic.
     * @param dT delta time since the last frame
     * @param ie input event
     * @throws IllegalStateException if no current state is set
     */
    public void onUpdate(double dT, InputEvent ie) {
        if (currentState == null) {
            throw new IllegalStateException("No current state is set.");
        }
        currentState.onUpdate(dT, ie);
    }

    /**
     * Sets the current state of the state machine.
     * @param state name of the new state
     * @throws IllegalArgumentException if the state does not exist
     */
    public void setState(String state) {
        if (!states.containsKey(state)) {
            throw new IllegalArgumentException("State " + state + " not found.");
        }
        if (currentState != null) {
            currentState.onExit();
        }
        currentState = states.get(state);
        currentStateName = state;
        currentState.onEnter();
    }

    /**
     * Adds a new state to the state machine.
     * @param name the name of the state
     * @param state the state instance
     * @throws IllegalArgumentException if the state already exists
     */
    public void addState(String name, State state) {
        if (states.containsKey(name)) {
            throw new IllegalArgumentException("State " + name + " already exists.");
        }
        states.put(name, state);
    }

    /**
     * Removes a state from the state machine.
     * @param name the name of the state to remove
     * @throws IllegalArgumentException if the state does not exist
     */
    public void removeState(String name) {
        if (!states.containsKey(name)) {
            throw new IllegalArgumentException("State " + name + " not found.");
        }
        states.remove(name);
    }

    /**
     * Resets the state machine to the default state.
     * @throws IllegalStateException if the default state is not set
     */
    public void resetToDefault() {
        if (defaultState == null) {
            throw new IllegalStateException("Default state is not set.");
        }
        setState(defaultStateName);
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    /**
     * Gets the owner game object.
     * @return the owner game object
     */
    public IGameObject getOwner() {
        return owner.gameObject();
    }

    /**
     * Sets the default state by name.
     * @param defaultState the name of the default state
     * @throws IllegalArgumentException if the state does not exist
     */
    public void setDefaultState(String defaultState) {
        if (!states.containsKey(defaultState)) {
            throw new IllegalArgumentException("Default state " + defaultState + " not found.");
        }
        this.defaultState = states.get(defaultState);
        this.defaultStateName = defaultState;
    }

    /**
     * Forwards collision events to the current state.
     * @param other the other game object collided with
     * @throws IllegalStateException if no current state is set
     */
    public void onCollision(IGameObject other) {
        if (currentState == null) {
            throw new IllegalStateException("No current state is set.");
        }
        currentState.onCollision(other);
    }

    /**
     * Gets the name of the current state.
     * @return the current state name
     */
    public String getCurrentStateName() {
        return currentStateName;
    }

    /**
     * Gets the name of the default state.
     * @return the default state name
     */
    public String getDefaultStateName() {
        return defaultStateName;
    }

    /**
     * Gets a state by its name.
     * @param name the name of the state
     * @return the State instance if found
     * @throws IllegalArgumentException if the state does not exist
     */
    public State getState(String name) {
        State state = states.get(name);
        if (state == null) {
            throw new IllegalArgumentException("State " + name + " not found.");
        }
        return state;
    }
}