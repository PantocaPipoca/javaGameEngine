package Game.Entities.Enemies.EnemyStates;

import Figures.GeometryUtils;
import Figures.Point;
import Game.Entities.Commons.State;
import Game.Entities.Enemies.Enemy;
import Game.Entities.Player.Player;
import GameEngine.Animator;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Class that represents the chase state for an enemy.
 * Handles chasing the player, transitions to attack or patrol based on distance.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class ChaseState extends State {

    private final IGameObject player;
    private double chaseSpeed;
    private double attackRadius;
    private double forgetfullRadius;

    /**
     * Constructs a ChaseState with player, speed, attack, and forgetful radii.
     * @param player the player game object
     * @param chaseSpeed the speed while chasing
     * @param attackRadius the radius to start attacking
     * @param forgetfullRadius the radius to forget the player
     */
    public ChaseState(IGameObject player, double chaseSpeed, double attackRadius, double forgetfullRadius) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null.");
        }
        this.player = player;
        this.chaseSpeed = chaseSpeed;
        this.attackRadius = attackRadius;
        this.forgetfullRadius = forgetfullRadius;
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the chase state, moves towards the player, and handles state transitions.
     * @param dT delta time since last update
     * @param ie the current input event
     */
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
        if (distance > forgetfullRadius) {
            stateMachine.setState("Patrol");
            return;
        }

        Point direction = GeometryUtils.normalize(new Point(dx, dy));

        if (direction.x() < 0) {
            owner.gameObject().setFlip(true);
        } else if (direction.x() > 0) {
            owner.gameObject().setFlip(false);
        }

        // Move the enemy towards the player
        owner.gameObject().transform().move(new Point(direction.x() * chaseSpeed * dT, direction.y() * chaseSpeed * dT), 0);
    }

    /**
     * Called when entering the chase state.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        Enemy e = (Enemy) owner;
        Animator animator = e.getAnimator();


        if(owner.gameObject().name().startsWith("bomber_ghost")) {
            animator.frameDuration(0.25f);
            e.playAnimation("run");
        }
        else {
            animator.frameDuration(0.05f);
            e.playAnimation("run");
        }

        if (owner.gameObject().name().contains("bomb")) {
            owner.equipGun(0);
        }
    }

    /**
     * Called when exiting the chase state.
     */
    @Override
    public void onExit() {}

    /**
     * Handles collision while chasing.
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