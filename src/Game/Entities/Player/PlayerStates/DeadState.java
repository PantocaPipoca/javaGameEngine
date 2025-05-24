package Game.Entities.Player.PlayerStates;

import java.util.List;

import Game.Audio.SoundPlayer;
import Game.Entities.Commons.State;
import Game.Entities.Player.Player;
import GameEngine.IGameObject;
import GameEngine.InputEvent;
import GameEngine.Shape;

/**
 * Class that represents the dead state for the player.
 * Handles logic when the player is dead (no actions possible).
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class DeadState extends State {

    private float timer = 0f;
    private boolean animationStopped = false;

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
        timer += dT;
        Player player = (Player) owner;
        List<Shape> frames = player.getAnimator().getAnimationFrames("death");
        int frameCount = frames != null ? frames.size() : 0;
        float frameDuration = player.getAnimator().frameDuration();
        float totalDuration = (frameCount - 1) * frameDuration;

        if (!animationStopped && timer >= totalDuration) {
            player.getAnimator().stopAnimation();
            animationStopped = true;
        }
    }

    /**
     * Called when entering the dead state.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        timer = 0f;
        animationStopped = false;
        Player player = (Player) owner;
        player.playAnimation("death");
        SoundPlayer.playSound("songs/death.wav");
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