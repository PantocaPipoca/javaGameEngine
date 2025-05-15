package Game.Entities.Enemies;

import java.util.List;

import Figures.Point;
import Game.Entities.Health;
import Game.Entities.Enemies.EnemyStates.ChaseState;
import Game.Entities.Enemies.EnemyStates.EnemyDeadState;
import Game.Entities.Enemies.EnemyStates.PatrolState;
import Game.Entities.Enemies.EnemyStates.AttackState;
import GameEngine.IGameObject;

public class Striker extends Enemy {

    public Striker(Health health, IGameObject player, List<Point> patrolPoints,
                  double patrolSpeed, double detectionRadius, double attackRadius,
                  double chaseSpeed, double forgetfullRadius) {
        super(health);

        stateMachine.addState("Patrol", new PatrolState(patrolPoints, player, patrolSpeed, detectionRadius));
        stateMachine.addState("Chase", new ChaseState(player, chaseSpeed, attackRadius, forgetfullRadius));
        stateMachine.addState("Dead", new EnemyDeadState());
        stateMachine.addState("Attack", new AttackState());

        stateMachine.setDefaultState("Patrol");
    }
    
}
