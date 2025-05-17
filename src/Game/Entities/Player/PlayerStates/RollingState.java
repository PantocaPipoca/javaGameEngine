package Game.Entities.Player.PlayerStates;

import Game.Entities.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Class that represents the rolling state for the player.
 * Handles the rolling timer and transitions back to idle after rolling ends.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class RollingState extends State {

    private static final double rollDuration = 0.5;
    private double rollTime = 0;

    /**
     * Constructs a RollingState.
     */
    public RollingState() {}

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the rolling timer and transitions to Idle when done.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        rollTime += dT;
        if (rollTime >= rollDuration) {
            stateMachine.setState("Idle");
        }
    }

    /**
     * Called when entering the rolling state.
     * Resets the rolling timer.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        rollTime = 0;
    }

    /**
     * Called when exiting the rolling state.
     */
    @Override
    public void onExit() {}

    /**
     * Handles collision while rolling.
     * @param other the other game object collided with
     */
    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
    }
}