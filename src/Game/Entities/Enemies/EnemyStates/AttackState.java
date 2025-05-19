package Game.Entities.Enemies.EnemyStates;

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

    /**
     * Constructs an AttackState.
     * @param target the target game object (e.g., player)
     */
    public AttackState() {
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the attack state. (No default logic.)
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
    }

    /**
     * Called when entering the attack state.
     */
    @Override
    public void onEnter() {
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
        // Additional logic for exiting attack state can be added here
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