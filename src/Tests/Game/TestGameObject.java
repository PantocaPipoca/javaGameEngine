package Tests.Game;

import Figures.Point;
import GameEngine.IGameObject;
import GameEngine.Transform;
import GameEngine.ICollider;
import GameEngine.IBehaviour;

public class TestGameObject implements IGameObject {
    private final String name;
    private final Transform transform;

    public TestGameObject(String name) {
        this.name = name;
        this.transform = new Transform(new Point(0, 0), 0, 0, 1);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Transform transform() {
        return transform;
    }

    @Override
    public ICollider collider() {
        return null; // Not needed for these tests
    }

    @Override
    public IBehaviour behaviour() {
        return null; // Not needed for these tests
    }

    @Override
    public void update() {
        // No update logic needed for these tests
    }
}