package Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Game.PlayerStates.DeadState;
import Game.PlayerStates.IdleState;
import Game.PlayerStates.MovingState;
import Game.PlayerStates.RollingState;
import Game.PlayerStates.StunnedState;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class Player implements IEntity {
    private final Health healthManager;
    private final StateMachine stateMachine;
    private float score;
    private IGameObject go;

    public Player(Health health) {
        this.healthManager = health;
        this.score = 0;

        Map<String, State> playerStates = new HashMap<>();
        playerStates.put("Idle", new IdleState());
        playerStates.put("Moving", new MovingState());
        playerStates.put("Rolling", new RollingState());
        playerStates.put("Stunned", new StunnedState());
        playerStates.put("Dead", new DeadState());
        this.stateMachine = new StateMachine(playerStates, "Idle");
    }

    @Override
    public IGameObject gameObject() {
        return go;
    }

    @Override
    public void gameObject(IGameObject go) {
        this.go = go;
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update(); // Updates position and colliders essencially
        stateMachine.onUpdate(dT, ie); // Updates what the player does
    }

    @Override
    public void onCollision(List<IGameObject> gol) {

    }

    @Override
    public void onInit() {

    }

    @Override
    public void onEnabled() {

    }

    @Override
    public void onDisabled() {

    }

    @Override
    public void onDestroy() {

    }

    public void addScore(float score) {
        this.score += score;
    }
    public float getScore() {
        return score;
    }
    public Health getHealthManager() {
        return healthManager;
    }
    public StateMachine getStateMachine() {
        return stateMachine;
    }
}
