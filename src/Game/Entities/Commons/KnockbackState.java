package Game.Entities.Commons;

import Figures.Point;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class KnockbackState extends State {
    private double velocityX, velocityY;
    private double timer;

    public KnockbackState(double duration) {
        this.timer = duration;
    }

    public void knockbackStart(double dx, double dy, double strength, double duration) {
        this.velocityX = dx * strength;
        this.velocityY = dy * strength;
        timer = duration;
        IGameObject go = owner.gameObject();
        go.transform().move(new Point(velocityX, velocityY), 0);
        go.update();
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        if (timer > 0) {
            IGameObject go = owner.gameObject();
            go.transform().move(new Point(velocityX * dT, velocityY * dT), 0);
            go.update();

            timer -= dT;
        } else {
            stateMachine.setState("Stunned");
        }
    }

    @Override
    public void onEnter() {
        super.onEnter();
        owner.getAnimator().stopAnimation();
    }

    @Override
    public void onExit() {}
}