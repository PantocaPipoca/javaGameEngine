package Game.Entities;

import GameEngine.IGameObject;
import GameEngine.InputEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * StateMachine class that manages the states of a game object.
 * It allows transitioning between different states and handles updates.
 * Each state can define its own behavior for entering, exiting, and updating.
 * @author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version: 1.0 (22/04/25)
 */
public class StateMachine {
    
    private String currentStateName;
    private State currentState;
    private State defaultState;
    private String defaultStateName;
    private IEntity owner;
    private Map<String, State> states = new HashMap<>();


    /**
     * Constructor for the StateMachine
     * @param defaultState name of the default state
     * @param go game object that controls this state machine
     */
    public StateMachine() {
        this.states = new HashMap<String, State>();
    }

    public void setOwner(IGameObject go) {
        this.owner = (IEntity) go.behaviour();
        for (State s : states.values()) {
            s.onInit(this, go);
        }
    }

    /**
     * Updates the current state of the state machine.
     * This method should be called every frame for any owner logic.
     * @param dT delta time since the last frame
     * @param ie input event
     */
    public void onUpdate(double dT, InputEvent ie) {
        if (currentState == null) {
            throw new IllegalStateException("No current state is set.");
        }
        //System.out.println("State: " + currentState.getClass().getSimpleName() + "From: " + owner.name());
        currentState.onUpdate(dT, ie);
    }

    /**
     * Sets the current state of the state machine.
     * @param state name of the new state
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

    public void addState(String name, State state) {
        if (states.containsKey(name)) {
            throw new IllegalArgumentException("State " + name + " already exists.");
        }
        states.put(name, state);
    }

    /**
     * Removes a state from the state machine.
     * @param name the name of the state to remove
     */
    public void removeState(String name) {
        if (!states.containsKey(name)) {
            throw new IllegalArgumentException("State " + name + " not found.");
        }
        states.remove(name);
    }

    public void resetToDefault() {
        if (defaultState == null) {
            throw new IllegalStateException("Default state is not set.");
        }
        setState(defaultStateName);
    }


    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    public IGameObject getOwner() {
        return owner.gameObject();
    }

    public void setDefaultState(String defaultState) {
        if (!states.containsKey(defaultState)) {
            throw new IllegalArgumentException("Default state " + defaultState + " not found.");
        }
        this.defaultState = states.get(defaultState);
        this.defaultStateName = defaultState;
    }

    public void onCollision(IGameObject other) {
        if (currentState == null) {
            throw new IllegalStateException("No current state is set.");
        }
        currentState.onCollision(other);
    }

    public String getCurrentStateName() {
        return currentStateName;
    }
    public String getDefaultStateName() {
        return defaultStateName;
    }
}
