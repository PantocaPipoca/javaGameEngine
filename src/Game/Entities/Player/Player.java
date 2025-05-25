package Game.Entities.Player;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import Figures.Point;
import Game.Audio.SoundPlayer;
import Game.Entities.Commons.Entity;
import Game.Entities.Commons.EntityUtils;
import Game.Entities.Commons.Health;
import Game.Entities.Commons.KnockbackState;
import Game.Entities.Commons.StunnedState;
import Game.Entities.Enemies.Enemy;
import Game.Entities.Player.PlayerStates.*;
import Game.Gun.Gun;
import Game.Observer.GameListener;
import Game.Observer.GamePublisher;
import Game.UI.GameUI;
import Game.UI.MainMenuUI;
import GameEngine.*;

/**
 * Class that represents the player entity in the game.
 * Responsible for handling player health, state, weapons, animation, collision logic,
 * and publishing events for UI updates and score changes.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.1 (25/05/25)
 * @inv Player must always have a valid health manager and state machine.
 */
public class Player extends Entity implements GamePublisher {

    private final List<GameListener> listeners = new ArrayList<>(); // Listeners for player events (UI, etc.)
    private float score; // Player's score
    private Point lastMoveDirection = new Point(1, 0); // Last move direction for rolling, etc.

    /////////////////////////////////////////////////// Constructors ///////////////////////////////////////////////////

    /**
     * Constructs a player with the specified health, movement speed, and rolling speed.
     * Adds all player-specific states to the state machine.
     * @param health the health manager
     * @param movingSpeed the movement speed
     * @param rollCooldown the cooldown between rolls
     * @param rollSpeedMultiplier the speed multiplier during roll
     * @param rollTime the duration of the roll
     */
    public Player(Health health, double movingSpeed, double rollCooldown, double rollSpeedMultiplier, double rollTime) {
        super(health);
        this.score = 0;

        stateMachine.addState("Idle", new IdleState());
        stateMachine.addState("Moving", new MovingState(movingSpeed, rollCooldown));
        stateMachine.addState("Rolling", new RollingState(movingSpeed, rollSpeedMultiplier, rollTime));
        stateMachine.addState("Stunned", new StunnedState(0.2));
        stateMachine.addState("Dead", new DeadState());
        stateMachine.addState("Knocked", new KnockbackState(0.2));

        stateMachine.setDefaultState("Idle");
    }

    /////////////////////////////////////////////////// Logic ///////////////////////////////////////////////////

    /**
     * Updates the player each frame.
     * Handles input, aiming, and delegates to Entity's update logic.
     * @param dT delta time since last update
     * @param ie input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        // Don't update player if main menu is visible
        if (MainMenuUI.getInstance().isVisible()) {
            return;
        }
        setTargetPos(new Point(ie.mouseWorldPosition().getX(), ie.mouseWorldPosition().getY()));
        super.onUpdate(dT, ie);

        // Debug: print position if F is pressed
        if (ie.isKeyPressed(KeyEvent.VK_F)) {
            System.out.println(go.transform().position());
        }

        // Check for death
        if (!healthManager.isAlive() && !stateMachine.getCurrentStateName().equals("Dead")) {
            stateMachine.setState("Dead");
            System.out.println("Game Over!");
            return;
        }
    }

    /**
     * Handles collision with other game objects.
     * Handles knockback from enemies, damage from bullets, and wall collision.
     * @param gol list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {
        boolean knocked = false;
        for (IGameObject other : gol) {
            stateMachine.onCollision(other);

            boolean isEnemy = other.name().startsWith("gunner") ||
                              other.name().startsWith("bomber") ||
                              other.name().startsWith("striker");

            // Ignore dead enemies
            if (isEnemy) {
                Enemy enemy = (Enemy) other.behaviour();
                if (enemy.getStateMachine().getCurrentStateName().equals("Dead")) {
                    continue;
                }
            }

            // Handle knockback and damage from enemies or enemy bullets
            if ((isEnemy || other.name().equals("enemyBullet")) &&
                !knocked && !stateMachine.getCurrentStateName().equals("Rolling") &&
                !stateMachine.getCurrentStateName().equals("Dead")) {
                EntityUtils.calculateKnockback(this, other, 20, 0.3);
                SoundPlayer.playSound("songs/hit.wav");
                stateMachine.setState("Knocked");
                knocked = true;
                healthManager.takeDamage(10);
            }

            // Handle wall collision
            if (other.name().equals("wall")) {
                resolveAgainst(other);
            }
        }
    }

    /**
     * Initializes the player, subscribes UI, and equips the first gun.
     */
    @Override
    public void onInit() {
        super.onInit();
        subscribe(GameUI.getInstance());
        getHealthManager().subscribe(GameUI.getInstance());
        equipGun(0);

        publishScoreChanged();
        for (GameListener l : listeners) {
            l.onPlayerHealthChanged(getHealthManager().getCurrentHealth());
        }
    }

    /////////////////////////////////////////////////// Player Logic ///////////////////////////////////////////////////

    /**
     * Adds score to the player and notifies listeners.
     * @param score amount to add
     */
    public void addScore(float score) {
        this.score += score;
        publishScoreChanged();
    }

    /**
     * Sets the player's score and notifies listeners.
     * @param score new score value
     */
    public void setScore(float score) {
        this.score = score;
        publishScoreChanged();
    }

    /**
     * Updates the score based on the enemy killed.
     * @param enemyName name of the enemy killed
     */
    public void onEnemyKilled(String enemyName) {
        if (enemyName.startsWith("gunner")) {
            addScore(30);
        } else if (enemyName.contains("bomber")) {
            addScore(50);
        } else if (enemyName.startsWith("striker")) {
            addScore(10);
        }
    }

    /**
     * Gets the player's score.
     * @return the player's score
     */
    public float getScore() {
        return score;
    }

    /**
     * Plays the specified animation.
     * @param name animation name
     */
    public void playAnimation(String name) {
        animator.play(name);
    }

    /**
     * Loads player animations.
     * Called when the player game object is set.
     */
    private void loadAnimations() {
        animator.addAnimation("walk", Shape.loadAnimation("player_walk", 8, (int) go.transform().scale()));
        animator.addAnimation("idle", Shape.loadAnimation("player_idle", 5, (int) go.transform().scale()));
        animator.addAnimation("roll", Shape.loadAnimation("player_roll", 5, (int) go.transform().scale()));
        animator.addAnimation("death", Shape.loadAnimation("player_death", 10, (int) go.transform().scale()));
    }

    /**
     * Sets the game object associated with the player and loads animations.
     * @param go the game object
     */
    @Override
    public void gameObject(IGameObject go) {
        this.go = (GameObject) go;
        this.stateMachine.setOwner(this);
        loadAnimations();
    }

    /**
     * Equips a gun by index, manages listener subscriptions for UI updates.
     * @param index the index of the gun in the inventory
     */
    @Override
    public void equipGun(int index) {
        if (index >= 0 && index < guns.size()) {
            // Unsubscribe listeners from the old gun (if any)
            if (currentGun != null && (
                currentGun.gameObject().name().equals("pistol") ||
                currentGun.gameObject().name().equals("shotgun") ||
                currentGun.gameObject().name().equals("rifle"))) {
                for (GameListener listener : listeners) {
                    Gun g = (Gun) currentGun;
                    g.unsubscribe(listener);
                }
            }
            currentGun = guns.get(index);
            setCurrentGun(currentGun);
            // Subscribe listeners to the new gun (if any)
            if (currentGun != null && (
                currentGun.gameObject().name().equals("pistol") ||
                currentGun.gameObject().name().equals("shotgun") ||
                currentGun.gameObject().name().equals("rifle"))) {
                for (GameListener listener : listeners) {
                    Gun g = (Gun) currentGun;
                    g.subscribe(listener);
                }
            }
            publishAmmoChanged();
        }
    }

    /////////////////////////////////////////////////// Observer Methods ///////////////////////////////////////////////////

    /**
     * Subscribes a listener to player events.
     * @param listener the listener to subscribe
     */
    @Override
    public void subscribe(GameListener listener) {
        listeners.add(listener);
    }

    /**
     * Unsubscribes a listener from player events.
     * @param listener the listener to unsubscribe
     */
    @Override
    public void unsubscribe(GameListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies listeners about ammo changes.
     */
    private void publishAmmoChanged() {
        for (GameListener l : listeners) {
            if (currentGun != null) {
                if (currentGun.gameObject().name().equals("pistol") ||
                    currentGun.gameObject().name().equals("shotgun") ||
                    currentGun.gameObject().name().equals("rifle")) {
                    Gun g = (Gun) currentGun;
                    l.onAmmoChanged(g.getCurrentAmmo(), g.getReserveAmmo());
                }
            }
        }
    }

    /**
     * Notifies listeners about score changes.
     */
    private void publishScoreChanged() {
        for (GameListener l : listeners) l.onScoreChanged(score);
    }

    /**
     * Sets the last move direction of the player.
     * @param dir the new direction
     */
    public void setLastMoveDirection(Point dir) {
        this.lastMoveDirection = dir;
    }

    /**
     * Gets the last move direction of the player.
     * @return the last move direction
     */
    public Point getLastMoveDirection() {
        return lastMoveDirection;
    }
}