package Game.Entities.Enemies;

import java.util.List;

import Figures.Point;
import Game.Entities.Health;
import Game.Entities.Enemies.EnemyStates.ChaseState;
import Game.Entities.Enemies.EnemyStates.EnemyDeadState;
import Game.Entities.Enemies.EnemyStates.KamikazeState;
import Game.Entities.Enemies.EnemyStates.PatrolState;
import GameEngine.IGameObject;

public abstract class Bomber extends Enemy {

    public Bomber(Health health, IGameObject player, List<Point> patrolPoints) {
        super(health);

        stateMachine.addState("Patrol", new PatrolState(patrolPoints, player));
        stateMachine.addState("Chase", new ChaseState(player));
        stateMachine.addState("Dead", new EnemyDeadState());
        stateMachine.addState("Attack", new KamikazeState());

        stateMachine.setDefaultState("Patrol");
    }
    
}
