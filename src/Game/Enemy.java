package Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Figures.Point;
import Game.EnemyStates.*;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class Enemy implements IEntity {
    
    private final StateMachine stateMachine;
    private final Health healthManager;
    private IGameObject go;

    public Enemy(List<Point> patrolPoints, Health health) {
        this.healthManager = health;

        Map<String, State> enemyStates = new HashMap<>();
        enemyStates.put("Patrol", new PatrolState(patrolPoints));
        enemyStates.put("Chase", new ChaseState());
        enemyStates.put("Attack", new AttackState());
        enemyStates.put("Dead", new EnemyDeadState());
        this.stateMachine = new StateMachine(enemyStates, "Patrol");
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
    public Health getHealthManager() {
        return healthManager;
    }
    public StateMachine getStateMachine() {
        return stateMachine;
    }

}
