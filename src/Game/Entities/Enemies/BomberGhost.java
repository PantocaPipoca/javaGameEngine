package Game.Entities.Enemies;

import java.util.List;

import Figures.Point;
import Game.Entities.Commons.Health;
import GameEngine.IGameObject;
import GameEngine.Shape;

/**
 * Class that represents a BomberGhost enemy.
 * Inherits Bomber logic and state setup for ghost-type bombers.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class BomberGhost extends Bomber {

    /**
     * Constructs a BomberGhost enemy with patrol, chase, attack, and dead states.
     * @param health the health manager
     * @param player the player game object
     * @param patrolPoints the patrol points for the bomber
     * @param patrolSpeed the speed while patrolling
     * @param detectionRadius the detection radius for chasing
     * @param attackRadius the radius to start attacking
     * @param chaseSpeed the speed while chasing
     * @param forgetfullRadius the radius to forget the player
     */
    public BomberGhost(Health health, IGameObject player, List<Point> patrolPoints,
                  double patrolSpeed, double detectionRadius, double attackRadius,
                  double chaseSpeed, double forgetfullRadius) {
        super(health, player, patrolPoints, patrolSpeed, detectionRadius, attackRadius, chaseSpeed, forgetfullRadius);
    }

    @Override
    protected void loadAnimations() {
        animator.addAnimation("walk", Shape.loadAnimation("bomberGhost_walk", 6, (int) go.transform().scale()));
        animator.addAnimation("run", Shape.loadAnimation("bomberGhost_walk", 6, (int) go.transform().scale()));
        animator.addAnimation("death", Shape.loadAnimation("bomberGhost_death", 10, (int) go.transform().scale()));
    }
}