package Tests;

import static org.junit.jupiter.api.Assertions.*;

import GameEngine.*;

import org.junit.jupiter.api.Test;

import Figures.*;

import java.util.List;

public class GameEngineTest {

    @Test
    public void testAddGameObject() {
        GameEngine engine = new GameEngine();
        GameObject go = new GameObject("Object1", new Transform(new Point(0, 0), 1, 0, 1), new Circle("0 0 1"), new Movement(0, 0, 0, 0, 0));

        engine.add(go);

        assertEquals(1, engine.getGameObjects().size());
    }

    @Test
    public void testDestroyGameObject() {
        GameEngine engine = new GameEngine();
        GameObject go = new GameObject("Object1", new Transform(new Point(0, 0), 1, 0, 1), new Circle("0 0 1"), new Movement(0, 0, 0, 0, 0));

        engine.add(go);
        engine.destroy(go);

        assertTrue(engine.getCollisions().isEmpty());
    }

    @Test
    public void testSimulateFrames() {
        GameEngine engine = new GameEngine();
        GameObject go1 = new GameObject("Object1", new Transform(new Point(0, 0), 1, 0, 1), new Circle("0 0 1"), new Movement(1, 0, 0, 0, 0));
        GameObject go2 = new GameObject("Object2", new Transform(new Point(5, 0), 1, 0, 1), new Circle("5 0 1"), new Movement(-1, 0, 0, 0, 0));

        engine.add(go1);
        engine.add(go2);

        engine.simulateFrames(3);

        List<String> collisions = engine.getCollisions();
        assertEquals(2, collisions.size());
        assertTrue(collisions.get(0).contains("Object1"));
        assertTrue(collisions.get(0).contains("Object2"));
    }

    @Test
    public void testGetCollisions() {
        GameEngine engine = new GameEngine();
        GameObject go1 = new GameObject("Object1", new Transform(new Point(0, 0), 1, 0, 1), new Circle("0 0 1"), new Movement(0, 0, 0, 0, 0));
        GameObject go2 = new GameObject("Object2", new Transform(new Point(1, 0), 1, 0, 1), new Circle("1 0 1"), new Movement(0, 0, 0, 0, 0));

        engine.add(go1);
        engine.add(go2);

        List<String> collisions = engine.getCollisions();

        assertEquals(2, collisions.size());
        assertTrue(collisions.get(0).contains("Object2"));
        assertTrue(collisions.get(1).contains("Object1"));
    }

    @Test
    public void testUpdateObjectLayer() {
        GameEngine engine = new GameEngine();
        GameObject go = new GameObject("Object1", new Transform(new Point(0, 0), 1, 0, 1), new Circle("0 0 1"), new Movement(0, 0, 1, 0, 0));

        engine.add(go);
        engine.simulateFrames(1);

        assertEquals(2, go.transform().layer());
    }
}