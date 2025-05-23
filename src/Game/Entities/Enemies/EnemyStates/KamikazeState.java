package Game.Entities.Enemies.EnemyStates;

import Game.Entities.Commons.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Class that represents the kamikaze attack state for an enemy.
 * Handles logic for kamikaze enemies that explode or self-destruct on attack.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class KamikazeState extends State {

    /**
     * Constructs a KamikazeState.
     */
    public KamikazeState() {}

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the kamikaze state. (No default logic.)
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
    }

    /**
     * Called when entering the kamikaze state.
     * Spawns a bomb and destroys the enemy.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        // Shoot the bomb (trigger explosion)
        if (owner.getCurrentGun() != null) {
            owner.getCurrentGun().shoot();
        }
    }

    /**
     * Called when exiting the kamikaze state.
     */
    @Override
    public void onExit() {
    }

    /**
     * Handles collision while in kamikaze state.
     * @param other the other game object collided with
     */
    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
    }
}