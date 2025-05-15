package Tests.GameEngine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Figures.Circle;
import Figures.Polygon;
import Figures.Point;
import GameEngine.*;

public class ColliderCircleTest {

    @Test
    public void twoCirclesThatCollide() {
        Circle c1 = new Circle("0 0 2");
        Circle c2 = new Circle("3 0 2");

        ITransform t1 = new Transform(new Point(0, 0), 0, 0, 1);
        ITransform t2 = new Transform(new Point(3, 0), 0, 0, 1);

        ColliderCircle collider1 = new ColliderCircle(c1, t1);
        ColliderCircle collider2 = new ColliderCircle(c2, t2);

        assertTrue(collider1.colides(collider2));
    }

    @Test
    public void twoCirclesThatDoNotCollide() {
        Circle c1 = new Circle("0 0 1");    // radius 1
        Circle c2 = new Circle("5 0 1");    // radius 1

        ITransform t1 = new Transform(new Point(0, 0), 0, 0, 1);
        ITransform t2 = new Transform(new Point(5, 0), 0, 0, 1);

        ColliderCircle collider1 = new ColliderCircle(c1, t1);
        ColliderCircle collider2 = new ColliderCircle(c2, t2);

        assertFalse(collider1.colides(collider2));
    }

    @Test
    public void getFigure() {
        Circle original = new Circle("1 1 1"); // radius 1
        ITransform t = new Transform(new Point(3, 3), 0, 0, 2); // scale ×2

        ColliderCircle collider = new ColliderCircle(original, t);
        Circle result = collider.figure();

        assertEquals(2, result.radius(), 0.0001);
    }

    @Test
    public void centroid() {
        Circle c = new Circle("2 2 1");
        ITransform t = new Transform(new Point(5, 5), 0, 0, 1);

        ColliderCircle collider = new ColliderCircle(c, t);
        Point center = collider.centroid();

        assertEquals(5.0, center.x(), 0.001);
        assertEquals(5.0, center.y(), 0.001);
    }

    @Test
    public void collidesWithPolygon() {
        Circle c = new Circle("4 4 1");
        ITransform tC = new Transform(new Point(4, 4), 0, 0, 1);
        ColliderCircle collider = new ColliderCircle(c, tC);

        Polygon p = new Polygon("4 3 3 5 3 5 5 3 5");
        ITransform tP = new Transform(new Point(4, 4), 0, 0, 1);
        ColliderPolygon colliderPol = new ColliderPolygon(p, tP);

        assertTrue(collider.colides(colliderPol));
    }

    @Test
    public void doesNotCollideWithPolygon() {
        Circle c = new Circle("4 4 1");
        ITransform tC = new Transform(new Point(4, 4), 0, 0, 1);
        ColliderCircle collider = new ColliderCircle(c, tC);

        Polygon p = new Polygon("4 3 3 5 3 5 5 3 5");
        ITransform tP = new Transform(new Point(0, 0), 0, 0, 1);
        ColliderPolygon colliderPol = new ColliderPolygon(p, tP);

        assertFalse(collider.colides(colliderPol));
    }
}