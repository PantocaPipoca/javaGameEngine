package Game.Entities.Enemies;

import java.util.List;

import Figures.Point;
import Game.Entities.Health;
import Game.Entities.Enemies.EnemyStates.ChaseState;
import Game.Entities.Enemies.EnemyStates.EnemyDeadState;
import Game.Entities.Enemies.EnemyStates.KamikazeState;
import Game.Entities.Enemies.EnemyStates.PatrolState;
import GameEngine.IGameObject;

/**
 * Abstract class that represents a Bomber enemy.
 * Handles patrol, chase, attack, and dead states for bomber-type enemies.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public abstract class Bomber extends Enemy {

    /**
     * Constructs a Bomber enemy with patrol, chase, attack, and dead states.
     * @param health the health manager
     * @param player the player game object
     * @param patrolPoints the patrol points for the bomber
     * @param patrolSpeed the speed while patrolling
     * @param detectionRadius the detection radius for chasing
     * @param attackRadius the radius to start attacking
     * @param chaseSpeed the speed while chasing
     * @param forgetfullRadius the radius to forget the player
     */
    public Bomber(Health health, IGameObject player, List<Point> patrolPoints,
                  double patrolSpeed, double detectionRadius, double attackRadius,
                  double chaseSpeed, double forgetfullRadius) {

        super(health);

        stateMachine.addState("Patrol", new PatrolState(patrolPoints, player, patrolSpeed, detectionRadius));
        stateMachine.addState("Chase", new ChaseState(player, chaseSpeed, attackRadius, forgetfullRadius));
        stateMachine.addState("Dead", new EnemyDeadState());
        stateMachine.addState("Attack", new KamikazeState());

        stateMachine.setDefaultState("Patrol");
    }
}