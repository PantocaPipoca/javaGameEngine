package Game.Entities.Player.PlayerStates;

import Figures.Point;
import Game.Entities.Commons.State;
import Game.Entities.Player.Player;
import GameEngine.GameObject;
import GameEngine.IGameObject;
import GameEngine.InputEvent;
import GameEngine.ITransform;
import Figures.GeometryUtils;

import java.awt.event.KeyEvent;

/**
 * Class that represents the moving state for the player.
 * Handles movement, animation, gun switching, and transitions to other states.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class MovingState extends State {

    private double speed;
    private double rollCooldown = 0.5;

    private double rollTime = 0;

    /**
     * Constructs a MovingState with the specified movement speed.
     * @param speed the movement speed
     */
    public MovingState(double speed) {
        this.speed = speed;
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Updates the player's movement, handles state transitions, and input.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        GameObject ownerGo = (GameObject) owner.gameObject();
        ITransform transform = ownerGo.transform();
        double distance = speed * dT;

        double dx = 0, dy = 0;
        if (ie.isKeyPressed(KeyEvent.VK_W)) dy -= 1;
        if (ie.isKeyPressed(KeyEvent.VK_S)) dy += 1;
        if (ie.isKeyPressed(KeyEvent.VK_A)) dx -= 1;
        if (ie.isKeyPressed(KeyEvent.VK_D)) dx += 1;

        Point direction = GeometryUtils.normalize(new Point(dx, dy));

        // Flip horizontal based on direction
        if (direction.x() < 0) {
            ownerGo.setFlip(true);
        } else if (direction.x() > 0) {
            ownerGo.setFlip(false);
        }

        Player player = (Player) owner;
        if (direction.x() != 0 || direction.y() != 0) {
            player.setLastMoveDirection(direction);
            transform.move(new Point(direction.x() * distance, direction.y() * distance), 0);
        } else {
            stateMachine.setState("Idle");
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

        if(rollTime > 0) {
            rollTime -= dT;
        }

        if (ie.isMouseButtonPressed(3) && rollTime <= 0) {
            stateMachine.setState("Rolling");
            rollTime = rollCooldown;
        }
    }

    /**
     * Called when entering the moving state.
     * Plays the walking animation.
     */
    @Override
    public void onEnter() {
        super.onEnter();
        if (owner instanceof Player player) {
            player.playAnimation("walk");
        }
    }

    /**
     * Called when exiting the moving state.
     */
    @Override
    public void onExit() {}

    /**
     * Handles collision while moving.
     * @param other the other game object collided with
     */
    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
    }
}