package Game.Entities.Enemies.EnemyStates;

import Figures.Point;
import Game.Entities.State;
import Game.Entities.Player.Player;
import GameEngine.IGameObject;
import GameEngine.InputEvent;
import Figures.GeometryUtils;

import java.util.List;

/**
 * Class that represents the patrol state for an enemy.
 * Handles patrolling between points and transitions to chase state if the player is detected.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Patrol points cannot be null or empty.
 */
public class PatrolState extends State {

    private IGameObject player;
    private List<Point> patrolPoints;
    private int currentPointIndex = 0;
    private static final double finishThreshold = 10;
    private double patrolSpeed;
    private double detectionRadius;

    /**
     * Constructs a PatrolState with patrol points, player, speed, and detection radius.
     * @param patrolPoints the patrol points for the enemy
     * @param player the player game object
     * @param patrolSpeed the speed while patrolling
     * @param detectionRadius the detection radius for chasing
     */
    public PatrolState(List<Point> patrolPoints, IGameObject player, double patrolSpeed, double detectionRadius) {
        if (patrolPoints == null || patrolPoints.isEmpty()) {
            throw new IllegalArgumentException("Patrol points cannot be null or empty.");
        }
        this.player = player;
        this.patrolPoints = patrolPoints;
        this.patrolSpeed = patrolSpeed;
        this.detectionRadius = detectionRadius;
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the patrol state, moves between points, and checks for player detection.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        if (player != null) {
            double dx = player.transform().position().x() - owner.gameObject().transform().position().x();
            double dy = player.transform().position().y() - owner.gameObject().transform().position().y();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < detectionRadius) {
                stateMachine.setState("Chase");
                return;
            }
        }
        Point currentPoint = patrolPoints.get(currentPointIndex);

        Point position = owner.gameObject().transform().position();
        double dx = currentPoint.x() - position.x();
        double dy = currentPoint.y() - position.y();

        Point direction = GeometryUtils.normalize(new Point(dx, dy));

        owner.gameObject().transform().move(new Point(direction.x() * dT * patrolSpeed, direction.y() * dT * patrolSpeed), 0);

        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance < finishThreshold) {
            currentPointIndex = (currentPointIndex + 1) % patrolPoints.size();
        }
    }

    /**
     * Called when entering the patrol state.
     */
    @Override
    public void onEnter() {
        super.onEnter();
    }

    /**
     * Called when exiting the patrol state.
     */
    @Override
    public void onExit() {}

    /**
     * Handles collision while patrolling.
     * @param other the other game object collided with
     */
    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
        if (other.name().startsWith("player")) {
            Player player = (Player) other.behaviour();
            player.getHealthManager().takeDamage(10);
        }
    }
}