package Game.Entities;

import GameEngine.IGameObject;
import GameEngine.InputEvent;

public abstract class State {

    public StateMachine stateMachine;
    public IEntity owner;

    public void onInit(StateMachine stateMachine, IEntity owner) {
        this.stateMachine = stateMachine;
        this.owner = owner;
    }

    public abstract void onUpdate(double dT, InputEvent ie);

    public void onEnter() {
        if (stateMachine == null || owner == null) {
            throw new IllegalStateException("StateMachine or owner is not initialized.");
        }
    }

    public abstract void onExit();

    public void onCollision(IGameObject other) {
        if (other == null) {
            throw new IllegalArgumentException("Colliding object cannot be null.");
        }
    }
}
