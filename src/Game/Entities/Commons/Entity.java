package Game.Entities.Commons;

import java.util.List;
import java.util.ArrayList;
import GameEngine.*;
import Figures.Point;
import Game.Gun.Weapon;

/**
 * Abstract base class for all living entities (Player, Enemy, etc).
 * Contains shared logic for state, animation, collision, and weapons.
 */
public abstract class Entity implements IEntity {
    protected final StateMachine stateMachine;
    protected final Health healthManager;
    protected GameObject go;
    protected List<Weapon> guns;
    protected Weapon currentGun;
    protected Animator animator = new Animator(0.1f);
    protected Point lastSafePos;
    protected Point targetPos = new Point(0, 0);

    public Entity(Health health) {
        this.healthManager = health;
        this.stateMachine = new StateMachine();
        this.guns = new ArrayList<>();
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        animator.update((float) dT);
        if (go != null) {
            go.setShape(animator.currentShape());
            go.update();
            lastSafePos = go.transform().position();
            stateMachine.onUpdate(dT, ie);
            go.update();
        }
    }

    @Override
    public void onInit() {
        stateMachine.resetToDefault();
    }

    @Override
    public void onEnabled() {}

    @Override
    public void onDisabled() {}

    @Override
    public void onDestroy() {}

    @Override
    public IGameObject gameObject() {
        return go;
    }

    @Override
    public void gameObject(IGameObject go) {
        this.go = (GameObject) go;
        this.stateMachine.setOwner((IEntity) go.behaviour());
    }

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

    /**
     * Adds a gun to the player's inventory and equips it.
     * @param gun the weapon to add
     */
    public void addGun(Weapon gun) {
        if (gun != null) {
            guns.add(gun);
            currentGun = gun;
        }
    }

    // Common getters and setters

    public Health getHealthManager() {
        return healthManager;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public Animator getAnimator() {
        return animator;
    }

    public List<Weapon> getGuns() {
        return guns;
    }

    public Weapon getCurrentGun() {
        return currentGun;
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

    public void hideCurrentGun() {
    if (currentGun != null && currentGun.gameObject() != null) {
        GameEngine.getInstance().destroy(currentGun.gameObject());
    }
}

    public void lastSafePos(Point p) {
        this.lastSafePos = p;
    }

    public void setTargetPos(Point p) {
        this.targetPos = p;
    }
}