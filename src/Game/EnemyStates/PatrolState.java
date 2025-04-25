package Game.EnemyStates;

import Figures.Point;
import Game.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

import java.util.List;

public class PatrolState extends State {

    private List<Point> patrolPoints;
    
    public PatrolState(List<Point> patrolPoints) {
        this.patrolPoints = patrolPoints;
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        stateMachine.setState("Chase");
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
