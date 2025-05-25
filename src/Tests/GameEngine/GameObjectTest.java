package Tests.GameEngine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import GameEngine.*;
import Figures.*;
import org.junit.jupiter.api.BeforeEach;

public class GameObjectTest {

    private GameObject go;
    private ITransform transform;
    private GeometricFigure figure;
    private IBehaviour behaviour;

    // ======= SETUP =======
    @BeforeEach
    void setUp() {
        transform = new Transform(new Point(1, 2), 0, 0, 1);
        figure = new Circle("1 2 3");
        behaviour = new DummyBehaviour();
        go = new GameObject("player", transform, figure, behaviour);
    }

    // ======= DUMMY CLASSES =======
    static class DummyBehaviour implements IBehaviour {
        @Override public void onUpdate(double dT, InputEvent ie) {}
        @Override public void onInit() {}
        @Override public void onEnabled() {}
        @Override public void onDisabled() {}
        @Override public void onDestroy() {}
        @Override public void onCollision(java.util.List<IGameObject> gol) {}
        @Override public IGameObject gameObject() { return null; }
        @Override public void gameObject(IGameObject go) {}
    }

    // ======= TESTS =======

    // Tests GameObject constructor validation
    @Test
    void constructor_throwsOnNullName() {
        assertThrows(IllegalArgumentException.class, () ->
            new GameObject(null, transform, figure, behaviour));
    }

    @Test
    void constructor_throwsOnEmptyName() {
        assertThrows(IllegalArgumentException.class, () ->
            new GameObject("", transform, figure, behaviour));
    }

    @Test
    void constructor_throwsOnNullTransform() {
        assertThrows(IllegalArgumentException.class, () ->
            new GameObject("player", null, figure, behaviour));
    }

    @Test
    void constructor_throwsOnNullFigure() {
        assertThrows(IllegalArgumentException.class, () ->
            new GameObject("player", transform, null, behaviour));
    }

    @Test
    void constructor_throwsOnNullBehaviour() {
        assertThrows(IllegalArgumentException.class, () ->
            new GameObject("player", transform, figure, null));
    }

    // Tests GameObject.update()
    @Test
    void update_updatesCollider() {
        ICollider oldCollider = go.collider();
        // Change transform to move the object
        go.transform().position(new Point(10, 10));
        go.update();
        ICollider newCollider = go.collider();
        assertNotSame(oldCollider, newCollider);
    }

    // Tests GameObject.updateCollider()
    @Test
    void updateCollider_changesCollider() {
        ICollider oldCollider = go.collider();
        go.transform().position(new Point(5, 5));
        go.updateCollider();
        ICollider newCollider = go.collider();
        assertNotSame(oldCollider, newCollider);
    }

    // Tests GameObject.setShape()
    @Test
    void setShape_setsShape() {
        Shape s = ShapeFactory.createShape("test", 1);
        go.setShape(s);
        assertSame(s, go.shape());
    }

    // Tests GameObject.setFlip()
    @Test
    void setFlip_setsFlip() {
        go.setFlip(true);
        assertTrue(go.isFlipped());
        go.setFlip(false);
        assertFalse(go.isFlipped());
    }
}