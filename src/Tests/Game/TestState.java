package Tests.Game;

import Game.Entities.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class TestState extends State {
    @SuppressWarnings("unused")
    private final String name;
    private boolean entered = false;
    private boolean exited = false;
    private boolean updated = false;
    private boolean collided = false;

    public TestState(String name) {
        this.name = name;
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        updated = true;
    }

    @Override
    public void onEnter() {
        entered = true;
    }

    @Override
    public void onExit() {
        exited = true;
    }

    @Override
    public void onCollision(IGameObject other) {
        collided = true;
    }

    public boolean isEntered() {
        return entered;
    }

    public boolean isExited() {
        return exited;
    }

    public boolean isUpdated() {
        return updated;
    }

    public boolean isCollided() {
        return collided;
    }
}