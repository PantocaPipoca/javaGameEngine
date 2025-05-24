package Game.Entities.Player.PlayerStates;

import Figures.Point;
import Game.Audio.SoundPlayer;
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

    private double rollDuration;
    private double rollSpeedMultiplier; // How much faster than normal
    private double rollTime = 0;
    private Point rollDirection = new Point(0, 0);
    private double rollSpeed = 0;
    private boolean wasImmune = false;
    private double currentSpeed;

    Player player = null;

    /**
     * Constructs a RollingState.
     * @param currentSpeed the current movement speed of the player
     * @param rollSpeedMultiplier the speed multiplier during roll
     * @param rollDuration the duration of the roll
     */
    public RollingState(double currentSpeed, double rollSpeedMultiplier, double rollDuration) {
        super();
        this.currentSpeed = currentSpeed;
        this.rollSpeedMultiplier = rollSpeedMultiplier;
        this.rollDuration = rollDuration;
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the rolling timer and transitions to Idle when done.
     * Moves the player in the roll direction at roll speed.
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
     * Resets the rolling timer, sets immunity, and starts the roll animation.
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
        player.playAnimation("roll");
        SoundPlayer.playStateSound("songs/roll.wav");
    }

    /**
     * Called when exiting the rolling state.
     * Removes immunity if it was set.
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