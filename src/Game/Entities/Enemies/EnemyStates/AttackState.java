package Game.Entities.Enemies.EnemyStates;

import Figures.Point;
import Game.Entities.Commons.State;
import Game.Entities.Player.Player;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Class that represents the attack state for an enemy.
 * Handles logic for attacking the player and collision damage.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class AttackState extends State {

    private double shootCooldown = 0;
    private double shootTimer = 0;

    private final IGameObject player;
    private final double outOfRangeRadius;

    /**
     * Constructs an AttackState.
     * @param player the target game object (e.g., player)
     * @param outOfRangeRadius the radius after which the enemy stops attacking and chases
     */
    public AttackState(IGameObject player, double outOfRangeRadius) {
        this.player = player;
        this.outOfRangeRadius = outOfRangeRadius;
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the attack state, handles shooting and state transitions.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        shootTimer -= dT;
        if (shootTimer <= 0.0) {
            owner.getCurrentGun().shoot();
            shootTimer = shootCooldown;
        }

        Point enemyPosition = owner.gameObject().transform().position();
        Point playerPosition = player.transform().position();

        // Calculate the direction vector towards the player
        double dx = playerPosition.x() - enemyPosition.x();
        double dy = playerPosition.y() - enemyPosition.y();

        // Calculate the distance to the player
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > outOfRangeRadius) {
            stateMachine.setState("Chase");
            owner.hideCurrentGun();
            return;
        }
    }

    /**
     * Called when entering the attack state.
     * Sets up shooting cooldown and equips the gun.
     */
    @Override
    public void onEnter() {
        owner.getAnimator().stopAnimation();
        shootCooldown = 3.0 / owner.getCurrentGun().fireRate();
        super.onEnter();
        owner.equipGun(0);
        shootTimer = 0.0;
    }

    /**
     * Called when exiting the attack state.
     */
    @Override
    public void onExit() {
    }

    /**
     * Handles collision while attacking.
     * Deals damage to the player on collision.
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