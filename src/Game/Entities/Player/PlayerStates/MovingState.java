package Game.Entities.Player.PlayerStates;

import Figures.Point;
import Game.Entities.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;
import GameEngine.ITransform;
import Figures.GeometryUtils;

import java.awt.event.KeyEvent;

public class MovingState extends State {

    private double speed;
    
    public MovingState(double speed) {
        this.speed = speed;
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        ITransform transform = owner.gameObject().transform();
        double distance = speed * dT;

        double dx = 0;
        double dy = 0;

        // Check which keys are pressed and update the directional vector
        if (ie.isKeyPressed(KeyEvent.VK_W)) { // Move up
            dy -= 1;
        }
        if (ie.isKeyPressed(KeyEvent.VK_S)) { // Move down
            dy += 1;
        }
        if (ie.isKeyPressed(KeyEvent.VK_A)) { // Move left
            dx -= 1;
        }
        if (ie.isKeyPressed(KeyEvent.VK_D)) { // Move right
            dx += 1;
        }

        Point direction = GeometryUtils.normalize(new Point(dx, dy));

        if (direction.x() != 0 || direction.y() != 0) {
            // Move the player in the direction of the key pressed
            transform.move(new Point(direction.x() * distance, direction.y() * distance), 0);
        }
        else {
            // If no keys are pressed, switch to IdleState
            stateMachine.setState("Idle");
        }


        if (ie.isKeyPressed(KeyEvent.VK_SPACE)) {
            stateMachine.setState("Rolling");
        }
        if (ie.isMouseButtonPressed(1)) {
            if(owner.getCurrentGun() != null) {
                owner.getCurrentGun().shoot();
            }
        }
        if (ie.isKeyPressed(KeyEvent.VK_1)) {
            owner.equipGun(0);
        }
    }

    @Override
    public void onEnter() {
        super.onEnter();

    }

    @Override
    public void onExit() {

    }

    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);

    }
}
