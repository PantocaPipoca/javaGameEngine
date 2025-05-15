package Game.Entities.Enemies.EnemyStates;

import Figures.GeometryUtils;
import Figures.Point;
import Game.Entities.State;
import Game.Entities.Player.Player;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class ChaseState extends State {

    private final IGameObject player;
    private double chaseSpeed;
    private double attackRadius;
    private double forgetfullRadius;
    
    public ChaseState(IGameObject player, double chaseSpeed, double attackRadius, double forgetfullRadius) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        this.player = player;
        this.chaseSpeed = chaseSpeed;
        this.attackRadius = attackRadius;
        this.forgetfullRadius = forgetfullRadius;
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        Point enemyPosition = owner.gameObject().transform().position();
        Point playerPosition = player.transform().position();

        // Calculate the direction vector towards the player
        double dx = playerPosition.x() - enemyPosition.x();
        double dy = playerPosition.y() - enemyPosition.y();

        // Calculate the distance to the player
        double distance = Math.sqrt(dx * dx + dy * dy);

        // If within attack radius, switch to AttackState
        if (distance <= attackRadius && !owner.gameObject().name().startsWith("striker")) {
            stateMachine.setState("Attack");
            return;
        }
        if( distance > forgetfullRadius) {
            stateMachine.setState("Patrol");
            return;
        }


        Point direction = GeometryUtils.normalize(new Point(dx, dy));

        // Move the enemy towards the player
        owner.gameObject().transform().move(new Point(direction.x() * chaseSpeed * dT, direction.y() * chaseSpeed * dT), 0);
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
