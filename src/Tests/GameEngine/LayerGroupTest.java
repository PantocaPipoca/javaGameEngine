package Tests.GameEngine;

import static org.junit.jupiter.api.Assertions.*;

import GameEngine.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LayerGroupTest {

    private LayerGroup group;
    private IGameObject obj1;
    private IGameObject obj2;

    // ======= DUMMY CLASS =======
    static class DummyGameObject implements IGameObject {
        private final String name;
        DummyGameObject(String name) { this.name = name; }
        @Override public String name() { return name; }
        @Override public ITransform transform() { return null; }
        @Override public Shape shape() { return null; }
        @Override public void setShape(Shape shape) {}
        @Override public ICollider collider() { return null; }
        @Override public void update() {}
        @Override public IBehaviour behaviour() { return null; }
        @Override public boolean isFlipped() { return false; }
        @Override public void setFlip(boolean flip) {}
    }

    // ======= SETUP =======
    @BeforeEach
    void setUp() {
        group = new LayerGroup(5);
        obj1 = new DummyGameObject("obj1");
        obj2 = new DummyGameObject("obj2");
    }

    // ======= TESTS =======

    // Tests LayerGroup.add()
    @Test
    void add_addsObjectIfNotPresent() {
        group.add(obj1);
        assertTrue(group.contains(obj1));
        // Adding again should not duplicate
        group.add(obj1);
        assertEquals(1, group.objects().size());
    }

    // Tests LayerGroup.remove()
    @Test
    void remove_removesObjectIfPresent() {
        group.add(obj1);
        group.remove(obj1);
        assertFalse(group.contains(obj1));
    }

    // Tests LayerGroup.contains()
    @Test
    void contains_returnsTrueIfPresentFalseIfNot() {
        group.add(obj1);
        assertTrue(group.contains(obj1));
        assertFalse(group.contains(obj2));
    }
}