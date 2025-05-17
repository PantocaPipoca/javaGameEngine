package Game.Entities.Player;

import java.util.List;

import java.util.ArrayList;

import Game.Entities.Health;
import Game.Entities.IEntity;
import Game.Entities.StateMachine;
import Game.Entities.StunnedState;
import Game.Entities.Player.PlayerStates.*;
import Game.Gun.Weapon;
import GameEngine.*;
import Figures.Point;

public class Player implements IEntity {
    private final Health healthManager;
    private final StateMachine stateMachine;
    private float score;
    private IGameObject go;
    private List<Weapon> guns;
    private Weapon currentGun;
    private Animator animator = new Animator(0.1f);

    private Point lastSafePos;

    public Player(Health health, double movingSpeed, double rollingSpeed) {
        this.healthManager = health;
        this.score = 0;
        this.stateMachine = new StateMachine();
        this.guns = new ArrayList<>();

        stateMachine.addState("Idle", new IdleState());
        stateMachine.addState("Moving", new MovingState(movingSpeed));
        stateMachine.addState("Rolling", new RollingState());
        stateMachine.addState("Stunned", new StunnedState(0.2));
        stateMachine.addState("Dead", new DeadState());

        stateMachine.setDefaultState("Idle");
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

    public void playAnimation(String name) {
        animator.play(name);
    }

    private void loadAnimations() {
        animator.addAnimation("walk", Shape.loadAnimation("player_walk", 8, (int) go.transform().scale()));
        animator.addAnimation("idle", Shape.loadAnimation("player_idle", 5, (int) go.transform().scale()));
    }

    public void addGun(Weapon gun) {
        if (gun != null) {
            guns.add(gun);
            currentGun = gun;
        }
    }

    public void setCurrentGun(Weapon gun) {
        if (gun != null) {
            
            this.currentGun = gun;

            if(gun.gameObject() != null) {
                GameEngine.getInstance().destroy(gun.gameObject());
            }

            GameEngine.getInstance().addEnabled(gun.gameObject());
        }
    }

    public void equipGun(int index) {
        if (index >= 0 && index < guns.size()) {
            currentGun = guns.get(index);
            setCurrentGun(currentGun);
        }
    }

    @Override
public void onCollision(List<IGameObject> gol) {
    boolean stunned = false;
    for (IGameObject other : gol) {
        // Knockback and stun on enemy collision
        if ((other.name().startsWith("gunner") ||
             other.name().startsWith("bomber") ||
             other.name().startsWith("striker")) && !stunned) {
            // Calculate knockback direction (from other to player)
            Point myPos = go.transform().position();
            Point otherPos = other.transform().position();
            double dx = myPos.x() - otherPos.x();
            double dy = myPos.y() - otherPos.y();
            double len = Math.sqrt(dx*dx + dy*dy);
            if (len == 0) len = 1; // prevent div by zero
            dx /= len;
            dy /= len;

            // Apply knockback (e.g., 50 units)
            double knockbackStrength = 0;
            lastSafePos = go.transform().position();
            go.transform().move(new Point(dx * knockbackStrength, dy * knockbackStrength), 0);
            go.update();

            // After knockback, resolve against any walls collided
            for (IGameObject wall : gol) {
                if (wall.name().equals("wall")) {
                    resolveAgainst(wall);
                }
            }

            // Switch to stunned state
            stateMachine.setState("Stunned");
            System.out.println("Player stunned by enemy!");
            stunned = true; // Only stun once per collision event
        }
        if (other.name().equals("wall")) {
            resolveAgainst(other);
            System.out.println("Player collided with wall");
        }
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

    public void addScore(float score) {
        this.score += score;
    }
    public float getScore() {
        return score;
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
        loadAnimations();
    }
    public List<Weapon> getGuns() {
        return guns;
    }
    public Weapon getCurrentGun() {
        return currentGun;
    }
    public Animator getAnimator() {
        return animator;
    }

    /**
     * 
     * @param wall
     */
    private void resolveAgainst(IGameObject wall) {
        Point from = lastSafePos;
        Point to   = go.transform().position();
        // movement vector
        double vx = to.x() - from.x();
        double vy = to.y() - from.y();

        double lo = 0, hi = 1;
        for (int i = 0; i < 5; i++) {
            double mid = (lo + hi) / 2;
            Point test = new Point(from.x() + vx * mid,
                                   from.y() + vy * mid);
            go.transform().setPosition(test);
            go.update();  // refresh collider

            if (go.collider().colides(wall.collider())) {
                hi = mid;  // still inside go back furgher
            } else {
                lo = mid;  // clear we can go farther
            }
        }

        // final just-outside position
        Point finalPos = new Point(from.x() + vx * lo,
                                   from.y() + vy * lo);
        go.transform().setPosition(finalPos);
        go.update();
    }
}
