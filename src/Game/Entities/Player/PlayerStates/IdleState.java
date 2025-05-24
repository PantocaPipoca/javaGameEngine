package Game.Entities.Player.PlayerStates;

import Game.Audio.SoundPlayer;
import Game.Entities.Commons.State;
import Game.Entities.Player.Player;
import Game.Gun.Gun;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

import java.awt.event.KeyEvent;

/**
 * Class that represents the idle state for the player.
 * Handles input for movement, shooting, gun switching, and reloading.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class IdleState extends State {

    /**
     * Constructs an IdleState.
     */
    public IdleState() {}

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the idle state, handles input for movement, shooting, gun switching, and reloading.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        if (ie.isKeyPressed(KeyEvent.VK_W) || ie.isKeyPressed(KeyEvent.VK_S) ||
            ie.isKeyPressed(KeyEvent.VK_A) || ie.isKeyPressed(KeyEvent.VK_D)) {
            stateMachine.setState("Moving");
        }
        if (ie.isMouseButtonPressed(1)) {
            if (owner.getCurrentGun() != null) {
                owner.getCurrentGun().shoot();
            }
        }
        for (int i = 1; i <= 9; i++) {
            int keyCode = KeyEvent.VK_1 + (i - 1);
            if (ie.isKeyPressed(keyCode)) {
                if (owner.getGuns().size() >= i && owner.getGuns().get(i - 1) != null) {
                    if (owner.getCurrentGun() != null) {
                        owner.hideCurrentGun();
                    }
                    owner.equipGun(i - 1);
                }
            }
        }
        if (ie.isKeyPressed(KeyEvent.VK_R)) {
            Gun gun = (Gun) owner.getCurrentGun();
            if (gun != null) {
                gun.reload();
            }
        }
    }

    /**
     * Called when entering the idle state.
     * Plays the idle animation.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        Player player = (Player) owner; 
        player.playAnimation("idle");
        SoundPlayer.stopStateSound();
    }

    /**
     * Called when exiting the idle state.
     */
    @Override
    public void onExit() {
        // No actions needed on exit from idle state.
    }

    /**
     * Handles collision while idle.
     * @param other the other game object collided with
     */
    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
    }
}