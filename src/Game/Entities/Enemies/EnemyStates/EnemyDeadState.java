package Game.Entities.Enemies.EnemyStates;

import Game.Entities.Commons.State;
import GameEngine.GameEngine;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Class that represents the dead state for an enemy.
 * Handles logic when the enemy is dead (no actions possible).
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class EnemyDeadState extends State {

    /**
     * Constructs an EnemyDeadState.
     */
    public EnemyDeadState() {}

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the dead state. No actions are performed while dead.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
    }

    /**
     * Called when entering the dead state.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        GameEngine.getInstance().destroy(owner.gameObject());
        GameEngine.getInstance().destroy(owner.getCurrentGun().gameObject());
    }

    /**
     * Called when exiting the dead state.
     */
    @Override
    public void onExit() {
    }

    /**
     * Handles collision while dead.
     * @param other the other game object collided with
     */
    @Override
    public void onCollision(IGameObject other) {
    }
}