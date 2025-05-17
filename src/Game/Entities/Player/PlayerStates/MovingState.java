package Game.Entities.Player.PlayerStates;

import Figures.Point;
import Game.Entities.Player.Player;
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

        double dx = 0, dy = 0;
        if (ie.isKeyPressed(KeyEvent.VK_W)) dy -= 1;
        if (ie.isKeyPressed(KeyEvent.VK_S)) dy += 1;
        if (ie.isKeyPressed(KeyEvent.VK_A)) dx -= 1;
        if (ie.isKeyPressed(KeyEvent.VK_D)) dx += 1;

        Point direction = GeometryUtils.normalize(new Point(dx, dy));

        // 👇 flip horizontal com base na direção
        if (direction.x() < 0) {
            owner.gameObject().setFlip(true);
        } else if (direction.x() > 0) {
            owner.gameObject().setFlip(false);
        }

        if (direction.x() != 0 || direction.y() != 0) {
            transform.move(new Point(direction.x() * distance, direction.y() * distance), 0);
        } else {
            stateMachine.setState("Idle");
        }

        if (ie.isKeyPressed(KeyEvent.VK_SPACE)) {
            stateMachine.setState("Rolling");
        }

        if (ie.isMouseButtonPressed(1)) {
            if (owner.getCurrentGun() != null) {
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
        //owner.playAnimation("walk");
        if (owner instanceof Player player) {
            player.playAnimation("walk");
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);

    }
}
