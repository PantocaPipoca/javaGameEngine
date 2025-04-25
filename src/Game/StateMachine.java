package Game;

import GameEngine.IGameObject;
import GameEngine.InputEvent;

import java.util.HashMap;
import java.util.Map;

import Game.PlayerStates.DeadState;
import Game.PlayerStates.IdleState;
import Game.PlayerStates.MovingState;
import Game.PlayerStates.RollingState;
import Game.PlayerStates.StunnedState;


/**
 * StateMachine class that manages the states of a game object.
 * It allows transitioning between different states and handles updates.
 * Each state can define its own behavior for entering, exiting, and updating.
 * @author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version: 1.0 (22/04/25)
 */
public class StateMachine {
    
    private State currentState;
    private State defaultState;
    private IGameObject owner;
    private Map<String, State> states = new HashMap<>();


    /**
     * Constructor for the StateMachine
     * @param defaultState name of the default state
     * @param go game object that controls this state machine
     */
    public StateMachine(Map<String, State> states, String defaultState) {
        if (!states.containsKey(defaultState))
            throw new IllegalArgumentException("Default state not found");
        this.states = states;
        this.defaultState = states.get(defaultState);
        this.currentState = this.defaultState;
    }

    public void setOwner(IGameObject go) {
        this.owner = go;
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
        System.out.println("State: " + currentState.getClass().getSimpleName() + "From: " + owner.name());
        currentState.onUpdate(dT, ie);
    }

    /**
     * Sets the current state of the state machine.
     * @param state name of the new state
     */
    public void setState(String state) {
        if (currentState != null) {
            currentState.onExit();
        }
        currentState = states.get(state);
        if (currentState == null) {
            throw new IllegalArgumentException("State " + state + " not found");
        }
        currentState.onEnter();
    }

    // Getters and Setters
    public IGameObject getOwner() {
        return owner;
    }
}
