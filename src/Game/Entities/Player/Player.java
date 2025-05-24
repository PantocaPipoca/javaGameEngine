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
import GameEngine.*;

/**
 * Class that represents the player entity in the game.
 * Handles health, state, weapons, animation, and collision logic.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Player must always have a valid health manager and state machine.
 */
public class Player extends Entity implements GamePublisher {

    private final List<GameListener> listeners = new ArrayList<>();
    private float score;
    private Point lastMoveDirection = new Point(1, 0);

    /**
     * Constructs a player with the specified health, movement speed, and rolling speed.
     * @param health the health manager
     * @param movingSpeed the movement speed
     * @param rollingSpeed the rolling speed
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

    /////////////////////////////////////////////////// IBehaviour Methods ///////////////////////////////////////////////////

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        animator.update((float) dT);
        if (ie.isKeyPressed(KeyEvent.VK_F)) {
            System.out.println(go.transform().position());
        }
        if (go != null) {
            go.setShape(animator.currentShape());
            go.update();
            lastSafePos = go.transform().position();
            setTargetPos(new Point(ie.mouseWorldPosition().getX(), ie.mouseWorldPosition().getY()));
            if (currentGun != null && !stateMachine.getCurrentStateName().equals("Dead")) {
                currentGun.updateRotation(targetPos);

                // Flip horizontal com base na posição da arma em relação ao corpo
                double gunX = currentGun.gameObject().transform().position().x();
                double bodyX = go.transform().position().x();
                go.setFlip(gunX < bodyX); // vira para a esquerda se arma estiver à esquerda
            }
            if (!healthManager.isAlive() && !stateMachine.getCurrentStateName().equals("Dead")) {
                System.out.println(stateMachine.getCurrentStateName());
                stateMachine.setState("Dead");
                System.out.println("Game Over!");
                return;
            }
            stateMachine.onUpdate(dT, ie);
            go.update();
        }

        //System.out.println(Game.getInstance().currentEnemyCount());
    }

    @Override
    public void onCollision(List<IGameObject> gol) {
        boolean knocked = false;
        for (IGameObject other : gol) {
            stateMachine.onCollision(other);

            boolean isEnemy = other.name().startsWith("gunner") ||
                            other.name().startsWith("bomber") ||
                            other.name().startsWith("striker");

            if (isEnemy) {
                Enemy enemy = (Enemy) other.behaviour();
                if (enemy.getStateMachine().getCurrentStateName().equals("Dead")) {
                    continue;
                }
            }

            if ((isEnemy || other.name().equals("enemyBullet")) &&
                !knocked && !stateMachine.getCurrentStateName().equals("Rolling") &&
                !stateMachine.getCurrentStateName().equals("Dead")){
                EntityUtils.calculateKnockback(this, other, 20, 0.3);
                SoundPlayer.playSound("songs/hit.wav");
                stateMachine.setState("Knocked");
                knocked = true;
                healthManager.takeDamage(10);
            }
            if (other.name().equals("wall")) {
                resolveAgainst(other);
            }
        }
    }

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
     * Adds score to the player.
     * @param score the score to add
     */
    public void addScore(float score) {
        this.score += score;
        publishScoreChanged();
    }

    public void setScore(float score) {
        this.score = score;
        publishScoreChanged();
    }

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
     * @return the score
     */
    public float getScore() {
        return score;
    }

    /**
     * Plays the specified animation.
     * @param name the animation name
     */
    public void playAnimation(String name) {
        animator.play(name);
    }

    /**
     * Loads player animations.
     */
    private void loadAnimations() {
        animator.addAnimation("walk", Shape.loadAnimation("player_walk", 8, (int) go.transform().scale()));
        animator.addAnimation("idle", Shape.loadAnimation("player_idle", 5, (int) go.transform().scale()));
        animator.addAnimation("roll", Shape.loadAnimation("player_roll", 5, (int) go.transform().scale()));
        animator.addAnimation("death", Shape.loadAnimation("player_death", 10, (int) go.transform().scale()));
    }

    @Override
    public void gameObject(IGameObject go) {
        this.go = (GameObject) go;
        this.stateMachine.setOwner(this);
        loadAnimations();
    }

    /**
     * Equips a gun by index.
     * @param index the index of the gun in the inventory
     */
    @Override
    public void equipGun(int index) {
        if (index >= 0 && index < guns.size()) {
            // Unsubscribe listeners from the old gun (if any)
            if (currentGun != null && currentGun.gameObject().name().equals("pistol")) {
                for (GameListener listener : listeners) {
                    Gun g = (Gun) currentGun;
                    g.unsubscribe(listener);
                }
            }
            currentGun = guns.get(index);
            setCurrentGun(currentGun);
            // Subscribe listeners to the new gun (if any)
            if (currentGun != null && currentGun.gameObject().name().equals("pistol")) {
                for (GameListener listener : listeners) {
                    Gun g = (Gun) currentGun;
                    g.subscribe(listener);
                }
            }
            publishAmmoChanged();
        }
    }

    /////////////////////////////////////////////////// Observer Methods ///////////////////////////////////////////////////

    @Override
    public void subscribe(GameListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unsubscribe(GameListener listener) {
        listeners.remove(listener);
    }

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

    private void publishScoreChanged() {
        for (GameListener l : listeners) l.onScoreChanged(score);
    }

    
    public void setLastMoveDirection(Point dir) {
        this.lastMoveDirection = dir;
    }

    public Point getLastMoveDirection() {
        return lastMoveDirection;
    }
}