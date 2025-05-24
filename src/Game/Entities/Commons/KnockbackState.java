package Game.Entities.Commons;

import Figures.Point;
import Game.Gun.Weapon;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * State representing the knockback effect for an entity.
 * Handles applying knockback movement and restoring state after the effect ends.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class KnockbackState extends State {
    private double velocityX, velocityY;
    private double timer;
    private Weapon hiddenGun;

    /////////////////////////////////////////////////// Constructor ///////////////////////////////////////////////////

    /**
     * Constructs a KnockbackState with the specified duration.
     * @param duration the duration of the knockback effect
     */
    public KnockbackState(double duration) {
        this.timer = duration;
    }

    /////////////////////////////////////////////////// Knockback Methods ///////////////////////////////////////////////////

    /**
     * Starts the knockback effect with the given direction, strength, and duration.
     * @param dx the x direction of knockback
     * @param dy the y direction of knockback
     * @param strength the strength of the knockback
     * @param duration the duration of the knockback
     */
    public void knockbackStart(double dx, double dy, double strength, double duration) {
        this.velocityX = dx * strength;
        this.velocityY = dy * strength;
        timer = duration;
        IGameObject go = owner.gameObject();
        go.transform().move(new Point(velocityX, velocityY), 0);
        go.update();
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the knockback state, moving the entity and transitioning when finished.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        if (timer > 0) {
            IGameObject go = owner.gameObject();
            go.transform().move(new Point(velocityX * dT, velocityY * dT), 0);
            go.update();

            timer -= dT;
        } else {
            stateMachine.setState("Stunned");
            if (hiddenGun != null) {
                owner.equipGun(hiddenGun);
                hiddenGun = null;
            }
        }
    }

    /**
     * Called when entering the knockback state.
     * Stops animation and hides the current gun.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        if(owner.gameObject().name().contains("bomber")) {
            owner.getStateMachine().setState("Attack");
        }
        owner.getAnimator().stopAnimation();
        hiddenGun = owner.getCurrentGun();
        owner.hideCurrentGun();
    }

    /**
     * Called when exiting the knockback state.
     */
    @Override
    public void onExit() {}
}