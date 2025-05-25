package Game.Entities.Commons;

import java.util.List;
import java.util.ArrayList;
import GameEngine.*;
import Figures.Point;
import Game.Gun.Weapon;

/**
 * Abstract base class for all living entities (Player, Enemy, etc).
 * Responsible for shared logic such as state management, animation, collision resolution, and weapon handling.
 * Subclasses should implement their own animation loading and state setup.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.1 (25/05/25)
 * @inv Entity must always have a valid health manager and state machine.
 */
public abstract class Entity implements IEntity {

    /** State machine for entity behavior */
    protected final StateMachine stateMachine;
    /** Health manager for the entity */
    protected final Health healthManager;
    /** The main game object representing this entity */
    protected GameObject go;
    /** List of weapons the entity can use */
    protected List<Weapon> guns;
    /** The currently equipped weapon */
    protected Weapon currentGun;
    /** Animator for handling entity animations */
    protected Animator animator = new Animator(0.1f);
    /** Last safe position for collision resolution */
    protected Point lastSafePos;
    /** Target position for aiming or movement */
    protected Point targetPos = new Point(0, 0);

    /////////////////////////////////////////////////// Constructors ///////////////////////////////////////////////////

    /**
     * Constructs an Entity with the specified health manager.
     * @param health the health manager
     */
    public Entity(Health health) {
        this.healthManager = health;
        this.stateMachine = new StateMachine();
        this.guns = new ArrayList<>();
    }

    /////////////////////////////////////////////////// Logic ///////////////////////////////////////////////////

    /**
     * Updates the entity's state, animation, and position.
     * Handles animation, gun flipping, health checks, and state machine updates.
     * Subclasses may override for additional logic.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        animator.update((float) dT);
        if (go != null) {
            go.setShape(animator.currentShape());
            go.update();
            lastSafePos = go.transform().position();

            // Gun flip logic (if applicable)
            if (currentGun != null && !stateMachine.getCurrentStateName().equals("Dead")) {
                currentGun.updateRotation(targetPos);
                double gunX = currentGun.gameObject().transform().position().x();
                double bodyX = go.transform().position().x();
                go.setFlip(gunX < bodyX);
            }

            // Health check (let subclasses override for custom behavior)
            if (!healthManager.isAlive() && !stateMachine.getCurrentStateName().equals("Dead")) {
                stateMachine.setState("Dead");
                return;
            }

            stateMachine.onUpdate(dT, ie);
            go.update();
        }
    }

    /**
     * Initializes the entity and resets its state machine.
     */
    @Override
    public void onInit() {
        stateMachine.resetToDefault();
    }

    /** Called when the entity is enabled. */
    @Override
    public void onEnabled() {}

    /** Called when the entity is disabled. */
    @Override
    public void onDisabled() {}

    /** Called when the entity is destroyed. */
    @Override
    public void onDestroy() {}

    /////////////////////////////////////////////////// GameObject Association ///////////////////////////////////////////////////

    /**
     * Gets the game object associated with this entity.
     * @return the game object
     */
    @Override
    public IGameObject gameObject() {
        return go;
    }

    /**
     * Sets the game object associated with this entity.
     * @param go the game object
     */
    @Override
    public void gameObject(IGameObject go) {
        this.go = (GameObject) go;
        this.stateMachine.setOwner((IEntity) go.behaviour());
    }

    /////////////////////////////////////////////////// Collision ///////////////////////////////////////////////////

    /**
     * Resolves collision against a wall by moving the entity back to the last safe position.
     * Uses binary search to find the last non-colliding position.
     * @param wall the wall game object to resolve against
     */
    @Override
    public void resolveAgainst(IGameObject wall) {
        if (lastSafePos == null) {
            return;
        }
        Point from = lastSafePos;
        Point to   = go.transform().position();
        double vx = to.x() - from.x();
        double vy = to.y() - from.y();
        double lo = 0, hi = 1;
        for (int i = 0; i < 5; i++) {
            double mid = (lo + hi) / 2;
            Point test = new Point(from.x() + vx * mid, from.y() + vy * mid);
            go.transform().position(test);
            go.update();
            if (go.collider().colides(wall.collider())) {
                hi = mid;
            } else {
                lo = mid;
            }
        }
        Point finalPos = new Point(from.x() + vx * lo, from.y() + vy * lo);
        go.transform().position(finalPos);
        go.update();
    }

    /////////////////////////////////////////////////// Weapons ///////////////////////////////////////////////////

    /**
     * Adds a gun to the entity's inventory and equips it.
     * @param gun the weapon to add
     */
    public void addGun(Weapon gun) {
        if (gun != null) {
            guns.add(gun);
            currentGun = gun;
        }
    }

    /**
     * Sets the current gun and adds it to the game engine.
     * @param gun the weapon to equip
     */
    public void setCurrentGun(Weapon gun) {
        if (gun != null) {
            this.currentGun = gun;
            if (gun.gameObject() != null) {
                GameEngine.getInstance().destroy(gun.gameObject());
            }
            GameEngine.getInstance().addEnabled(gun.gameObject());
        }
    }

    /**
     * Equips a gun by index.
     * @param index the index of the gun in the inventory
     */
    public void equipGun(int index) {
        if (index >= 0 && index < guns.size()) {
            currentGun = guns.get(index);
            setCurrentGun(currentGun);
        }
    }

    /**
     * Equips the specified gun if it is in the inventory.
     * @param gun the weapon to equip
     */
    public void equipGun(Weapon gun) {
        if (gun != null && guns.contains(gun)) {
            currentGun = gun;
            setCurrentGun(currentGun);
        }
    }

    /**
     * Hides the current gun by destroying its game object.
     */
    public void hideCurrentGun() {
        if (currentGun != null && currentGun.gameObject() != null) {
            GameEngine.getInstance().destroy(currentGun.gameObject());
        }
    }

    /////////////////////////////////////////////////// Getters & Setters ///////////////////////////////////////////////////

    /**
     * Gets the health manager.
     * @return the health manager
     */
    public Health getHealthManager() { return healthManager; }

    /**
     * Gets the state machine.
     * @return the state machine
     */
    public StateMachine getStateMachine() { return stateMachine; }

    /**
     * Gets the animator.
     * @return the animator
     */
    public Animator getAnimator() { return animator; }

    /**
     * Gets the list of guns.
     * @return the list of guns
     */
    public List<Weapon> getGuns() { return guns; }

    /**
     * Gets the currently equipped gun.
     * @return the currently equipped gun
     */
    public Weapon getCurrentGun() { return currentGun; }

    /**
     * Sets the last safe position of the entity.
     * @param p the last safe position
     */
    public void lastSafePos(Point p) { this.lastSafePos = p; }

    /**
     * Sets the target position of the entity.
     * @param p the target position
     */
    public void setTargetPos(Point p) { this.targetPos = p; }
}