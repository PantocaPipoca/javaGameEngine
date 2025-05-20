package Game.Entities.Enemies;

import java.util.List;

import Figures.Point;
import Game.Entities.Enemies.EnemyStates.ChaseState;
import Game.Entities.Enemies.EnemyStates.EnemyDeadState;
import Game.Entities.Enemies.EnemyStates.PatrolState;
import Game.Entities.Commons.Health;
import Game.Entities.Enemies.EnemyStates.AttackState;
import GameEngine.IGameObject;
import GameEngine.Shape;

/**
 * Class that represents a Striker enemy.
 * Handles patrol, chase, attack, and dead states for striker-type enemies.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class Striker extends Enemy {

    /**
     * Constructs a Striker enemy with patrol, chase, attack, and dead states.
     * @param health the health manager
     * @param player the player game object
     * @param patrolPoints the patrol points for the striker
     * @param patrolSpeed the speed while patrolling
     * @param detectionRadius the detection radius for chasing
     * @param attackRadius the radius to start attacking
     * @param chaseSpeed the speed while chasing
     * @param forgetfullRadius the radius to forget the player
     */
    public Striker(Health health, IGameObject player, List<Point> patrolPoints,
                  double patrolSpeed, double detectionRadius, double attackRadius,
                  double chaseSpeed, double forgetfullRadius) {
        super(health, player);

        stateMachine.addState("Patrol", new PatrolState(patrolPoints, player, patrolSpeed, detectionRadius));
        stateMachine.addState("Chase", new ChaseState(player, chaseSpeed, attackRadius, forgetfullRadius));
        stateMachine.addState("Dead", new EnemyDeadState());

        stateMachine.setDefaultState("Patrol");
    }

    
    @Override
    protected void loadAnimations() {
        animator.addAnimation("walk", Shape.loadAnimation("striker_walk", 8, (int) go.transform().scale()));
        animator.addAnimation("run", Shape.loadAnimation("striker_walk", 8, (int) go.transform().scale()));
        animator.addAnimation("death", Shape.loadAnimation("striker_death", 10, (int) go.transform().scale()));
    }
}