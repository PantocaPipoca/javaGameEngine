package Game.Entities.Enemies;

import java.util.List;

import GameEngine.IGameObject;
import Figures.Point;
import Game.Entities.Health;
import Game.Entities.Enemies.EnemyStates.*;

public class Gunner extends Enemy {

    public Gunner(Health health, IGameObject player, List<Point> patrolPoints, double patrolSpeed, double chaseSpeed) {
        super(health);

        stateMachine.addState("Patrol", new PatrolState(patrolPoints, player));
        stateMachine.addState("Chase", new ChaseState(player));
        stateMachine.addState("Dead", new EnemyDeadState());
        stateMachine.addState("Attack", new AttackState());

        stateMachine.setDefaultState("Patrol");
    }


    

}
