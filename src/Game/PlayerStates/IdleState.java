package Game.PlayerStates;

import Game.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class IdleState extends State {
    
    public IdleState() {
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        stateMachine.setState("Dead");
    }

    @Override
    public void onEnter() {
    }

    @Override
    public void onExit() {

    }

    @Override
    public void onCollision(IGameObject other) {
        
    }
}
