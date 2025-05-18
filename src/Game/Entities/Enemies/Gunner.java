package Game.Entities.Enemies;

import java.util.List;

import GameEngine.IGameObject;
import Figures.Point;
import Game.Entities.Commons.Health;
import Game.Entities.Enemies.EnemyStates.*;

/**
 * Class that represents a Gunner enemy.
 * Handles patrol, chase, attack, and dead states for gunner-type enemies.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class Gunner extends Enemy {

    /**
     * Constructs a Gunner enemy with patrol, chase, attack, and dead states.
     * @param health the health manager
     * @param player the player game object
     * @param patrolPoints the patrol points for the gunner
     * @param patrolSpeed the speed while patrolling
     * @param detectionRadius the detection radius for chasing
     * @param attackRadius the radius to start attacking
     * @param chaseSpeed the speed while chasing
     * @param forgetfullRadius the radius to forget the player
     */
    public Gunner(Health health, IGameObject player, List<Point> patrolPoints,
                  double patrolSpeed, double detectionRadius, double attackRadius,
                  double chaseSpeed, double forgetfullRadius) {
        super(health, player);

        stateMachine.addState("Patrol", new PatrolState(patrolPoints, player, patrolSpeed, detectionRadius));
        stateMachine.addState("Chase", new ChaseState(player, chaseSpeed, attackRadius, forgetfullRadius));
        stateMachine.addState("Dead", new EnemyDeadState());
        stateMachine.addState("Attack", new AttackState());

        stateMachine.setDefaultState("Patrol");
    }
}