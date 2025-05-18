package Game.Entities.Player.PlayerStates;

import Game.Entities.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Class that represents the dead state for the player.
 * Handles logic when the player is dead (no actions possible).
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class DeadState extends State {

    /**
     * Constructs a DeadState.
     */
    public DeadState() {}

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