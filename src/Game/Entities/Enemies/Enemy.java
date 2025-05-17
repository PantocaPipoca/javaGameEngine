package Game.Entities.Enemies;

import java.util.List;

import Figures.Point;
import Game.Entities.Health;
import Game.Entities.IEntity;
import Game.Entities.StateMachine;
import Game.Entities.StunnedState;
import Game.Gun.Weapon;
import GameEngine.Animator;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Abstract class that represents a generic enemy entity in the game.
 * Handles health, state, weapons, animation, and collision logic.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv Enemy must always have a valid health manager and state machine.
 */
public abstract class Enemy implements IEntity {

    protected StateMachine stateMachine;
    private final Health healthManager;
    private IGameObject go;
    private List<Weapon> guns;
    private Weapon currentGun;
    private Animator animator = new Animator(0.1f);

    /**
     * Constructs an enemy with the specified health manager.
     * @param health the health manager
     */
    public Enemy(Health health) {
        this.healthManager = health;
        this.stateMachine = new StateMachine();
        stateMachine.addState("Stunned", new StunnedState(1.5));
        currentGun = null;
    }

    /////////////////////////////////////////////////// IBehaviour Methods ///////////////////////////////////////////////////

    /**
     * Updates the enemy's state and position.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update(); // Updates position and colliders
        stateMachine.onUpdate(dT, ie); // Updates what the enemy does
    }

    /**
     * Handles collision with other game objects.
     * Applies knockback and switches to stunned state if hit by a bullet or player.
     * @param gol list of game objects collided with
     */
    @Override
    public void onCollision(List<IGameObject> gol) {
        for (IGameObject other : gol) {
            if (other.name().startsWith("bullet") || other.name().equals("player")) {
                // Calculate knockback direction (from other to this enemy)
                Point myPos = go.transform().position();
                Point otherPos = other.transform().position();
                double dx = myPos.x() - otherPos.x();
                double dy = myPos.y() - otherPos.y();
                double len = Math.sqrt(dx*dx + dy*dy);
                if (len == 0) len = 1; // prevent div by zero
                dx /= len;
                dy /= len;

                // Apply knockback (e.g., 50 units)
                double knockbackStrength = 50;
                go.transform().move(new Point(dx * knockbackStrength, dy * knockbackStrength), 0);

                // Switch to stunned state (e.g., 1.5 seconds)
                stateMachine.setState("Stunned");
            }
            stateMachine.onCollision(other);
        }
    }

    @Override
    public void onInit() {
        stateMachine.resetToDefault();
    }

    @Override public void onEnabled() {}
    @Override public void onDisabled() {}
    @Override public void onDestroy() {}

    /////////////////////////////////////////////////// Getters and Setters ///////////////////////////////////////////////////

    /**
     * Sets the current gun for the enemy.
     * @param gun the weapon to set as current
     */
    public void setCurrentGun(Weapon gun) {
        if (gun != null) {
            this.currentGun = gun;
        }
    }

    /**
     * Gets the animator for the enemy.
     * @return the animator
     */
    public Animator getAnimator() {
        return animator;
    }

    /**
     * Equips a gun by index.
     * @param index the index of the gun in the inventory
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void equipGun(int index) {
        if (index >= 0 && index < guns.size()) {
            currentGun = guns.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid gun index: " + index);
        }
    }

    /**
     * Gets the health manager.
     * @return the health manager
     */
    public Health getHealthManager() {
        return healthManager;
    }

    /**
     * Gets the state machine.
     * @return the state machine
     */
    public StateMachine getStateMachine() {
        return stateMachine;
    }

    /**
     * Gets the game object.
     * @return the game object
     */
    @Override
    public IGameObject gameObject() {
        return go;
    }

    /**
     * Sets the game object and assigns the state machine owner.
     * @param go the game object
     */
    @Override
    public void gameObject(IGameObject go) {
        this.go = go;
        this.stateMachine.setOwner((IEntity) go.behaviour());
    }

    /**
     * Gets the currently equipped gun.
     * @return the current gun
     */
    public Weapon getCurrentGun() {
        return currentGun;
    }
}