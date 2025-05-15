package Game.Entities.Enemies.EnemyStates;

import Figures.Point;
import Game.Entities.State;
import Game.Entities.Player.Player;
import GameEngine.IGameObject;
import GameEngine.InputEvent;
import Figures.GeometryUtils;

import java.util.List;

public class PatrolState extends State {

    private IGameObject player;
    private List<Point> patrolPoints;
    private int currentPointIndex = 0;
    private static final double finishThreshold = 10;
    private double patrolSpeed;
    private double detectionRadius;
    
    public PatrolState(List<Point> patrolPoints, IGameObject player, double patrolSpeed, double detectionRadius) {
        if (patrolPoints == null || patrolPoints.isEmpty()) {
            throw new IllegalArgumentException("Patrol points cannot be null or empty.");
        }
        this.player = player;
        this.patrolPoints = patrolPoints;
        this.patrolSpeed = patrolSpeed;
        this.detectionRadius = detectionRadius;
    }

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

    @Override
    public void onEnter() {
        super.onEnter();
    }

    @Override
    public void onExit() {
    }

    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
        if (other.name().startsWith("player")) {
            Player player = (Player) other.behaviour();
            player.getHealthManager().takeDamage(10);
        }
    }

}
