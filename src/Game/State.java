package Game;

import GameEngine.IGameObject;
import GameEngine.InputEvent;

public abstract class State {

    public StateMachine stateMachine;
    public IGameObject owner;

    public void onInit(StateMachine stateMachine, IGameObject owner) {
        this.stateMachine = stateMachine;
        this.owner = owner;
    }

    public abstract void onUpdate(double dT, InputEvent ie);

    public abstract void onEnter();

    public abstract void onExit();

    public abstract void onCollision(IGameObject other);
}
