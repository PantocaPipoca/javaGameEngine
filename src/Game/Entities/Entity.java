package Game.Entities;

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
    protected IGameObject go;
    protected List<Weapon> guns;
    protected Weapon currentGun;
    protected Animator animator = new Animator(0.1f);
    protected Point lastSafePos;

    public Entity(Health health) {
        this.healthManager = health;
        this.stateMachine = new StateMachine();
        this.guns = new ArrayList<>();
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        animator.update((float) dT);
        if (go != null) {
            go.setShape(animator.getCurrentShape());
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
        this.go = go;
        this.stateMachine.setOwner((IEntity) go.behaviour());
    }

    @Override
    public void resolveAgainst(IGameObject wall) {
        Point from = lastSafePos;
        Point to   = go.transform().position();
        double vx = to.x() - from.x();
        double vy = to.y() - from.y();
        double lo = 0, hi = 1;
        for (int i = 0; i < 5; i++) {
            double mid = (lo + hi) / 2;
            Point test = new Point(from.x() + vx * mid, from.y() + vy * mid);
            go.transform().setPosition(test);
            go.update();
            if (go.collider().colides(wall.collider())) {
                hi = mid;
            } else {
                lo = mid;
            }
        }
        Point finalPos = new Point(from.x() + vx * lo, from.y() + vy * lo);
        go.transform().setPosition(finalPos);
        go.update();
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

    public void setCurrentGun(Weapon gun) {
        if (gun != null) {
            this.currentGun = gun;
        }
    }

    public void equipGun(int index) {
        if (index >= 0 && index < guns.size()) {
            currentGun = guns.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid gun index: " + index);
        }
    }

    public void lastSafePos(Point p) {
        this.lastSafePos = p;
    }
}