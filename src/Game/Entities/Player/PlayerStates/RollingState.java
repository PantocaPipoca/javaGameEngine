package Game.Entities.Player.PlayerStates;

import Game.Entities.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class RollingState extends State{

    private static final double rollDuration = 0.5;
    private double rollTime = 0;
    
    public RollingState() {
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        rollTime += dT;
        if (rollTime >= rollDuration) {
            stateMachine.setState("Idle");
        }
    }

    @Override
    public void onEnter() {
        super.onEnter();
        rollTime = 0;
    }

    @Override
    public void onExit() {

    }

    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);

    }
}
