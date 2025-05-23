package Game.Entities.Player.PlayerStates;

import Figures.Point;
import Game.Entities.Commons.State;
import Game.Entities.Player.Player;
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
    private static final double rollSpeedMultiplier = 2.5; // How much faster than normal
    private double rollTime = 0;
    private Point rollDirection = new Point(0, 0);
    private double rollSpeed = 0;
    private boolean wasImmune = false;
    private double currentSpeed;

    Player player = null;

    /**
     * Constructs a RollingState.
     */
    public RollingState(double currentSpeed) {
        super();
        this.currentSpeed = currentSpeed;
    }

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
            if (wasImmune) {
                player.getHealthManager().setImmune(false);
                wasImmune = false;
            }
            stateMachine.setState("Idle");
            return;
        }
        // Move in the roll direction at rollSpeed
        player.gameObject().transform().move(
            new Point(rollDirection.x() * rollSpeed * dT, rollDirection.y() * rollSpeed * dT), 0
        );
    }

    /**
     * Called when entering the rolling state.
     * Resets the rolling timer.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        player = (Player) owner;
        rollTime = 0;

        Point dir = player.getLastMoveDirection();
        rollDirection = Figures.GeometryUtils.normalize(dir);
        if (rollDirection.x() == 0 && rollDirection.y() == 0) {
            rollDirection = new Point(1, 0); // Default to right if no direction
        }
        rollSpeed = currentSpeed * rollSpeedMultiplier;
        player.getHealthManager().setImmune(true);
        wasImmune = true;
    }

    /**
     * Called when exiting the rolling state.
     */
    @Override
    public void onExit() {
        if (wasImmune) {
            player.getHealthManager().setImmune(false);
            wasImmune = false;
        }
    }

    /**
     * Handles collision while rolling.
     * @param other the other game object collided with
     */
    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
    }
}