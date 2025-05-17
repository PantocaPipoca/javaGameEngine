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

public abstract class Enemy implements IEntity {
    
    protected StateMachine stateMachine;
    private final Health healthManager;
    private IGameObject go;
    private List<Weapon> guns;
    private Weapon currentGun;

    private Animator animator = new Animator(0.1f);

    public Enemy(Health health) {
        this.healthManager = health;
        this.stateMachine = new StateMachine();
        stateMachine.addState("Stunned", new StunnedState(1.5));

        currentGun = null;
    }

    public void setCurrentGun(Weapon gun) {
        if (gun != null) {
            this.currentGun = gun;
        }
    }

    public Animator getAnimator() {
        return animator;
    }

    public void equipGun(int index) {
        if (index >= 0 && index < guns.size()) {
            currentGun = guns.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid gun index: " + index);
        }
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update(); // Updates position and colliders essencially
        stateMachine.onUpdate(dT, ie); // Updates what the player does
    }

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

    @Override
    public void onEnabled() {

    }

    @Override
    public void onDisabled() {

    }

    @Override
    public void onDestroy() {

    }

    public Health getHealthManager() {
        return healthManager;
    }
    public StateMachine getStateMachine() {
        return stateMachine;
    }
    @Override
    public IGameObject gameObject() {
        return go;
    }
    @Override
    public void gameObject(IGameObject go) {
        this.go = go;
        this.stateMachine.setOwner((IEntity) go.behaviour());
    }

    public Weapon getCurrentGun() {
        return currentGun;
    }

}