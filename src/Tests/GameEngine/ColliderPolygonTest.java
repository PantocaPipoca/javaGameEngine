package Tests.GameEngine;

import static org.junit.jupiter.api.Assertions.*;

import GameEngine.*;
import org.junit.jupiter.api.Test;

import Figures.Circle;
import Figures.Polygon;
import Figures.Point;

public class ColliderPolygonTest {

    @Test
    public void twoPolygonsThatCollide() {
        Polygon p1 = new Polygon("4 0 0 4 0 4 4 0 4");
        Polygon p2 = new Polygon("4 3 3 5 3 5 5 3 5");

        ITransform t1 = new Transform(new Point(0, 0), 0, 0, 1);
        ITransform t2 = new Transform(new Point(3, 3), 0, 0, 1);

        ColliderPolygon collider1 = new ColliderPolygon(p1, t1);
        ColliderPolygon collider2 = new ColliderPolygon(p2, t2);

        assertTrue(collider1.colides(collider2));
    }

    @Test
    public void twoPolygonsThatDoNotCollide() {
        Polygon p1 = new Polygon("4 0 0 4 0 4 4 0 4");
        Polygon p2 = new Polygon("4 10 10 14 10 14 14 10 14");

        ITransform t1 = new Transform(new Point(0, 0), 0, 0, 1);
        ITransform t2 = new Transform(new Point(10, 10), 0, 0, 1);

        ColliderPolygon collider1 = new ColliderPolygon(p1, t1);
        ColliderPolygon collider2 = new ColliderPolygon(p2, t2);

        assertFalse(collider1.colides(collider2));
    }

    @Test
    public void polygonCollidesWithCircle() {
        Polygon p = new Polygon("4 0 0 4 0 4 4 0 4");
        Circle c = new Circle("2 2 1");

        ITransform tP = new Transform(new Point(0, 0), 0, 0, 1);
        ITransform tC = new Transform(new Point(2, 2), 0, 0, 1);

        ColliderPolygon colliderPol = new ColliderPolygon(p, tP);
        ColliderCircle colliderCir = new ColliderCircle(c, tC);

        assertTrue(colliderPol.colides(colliderCir));
    }

    @Test
    public void polygonDoesNotCollideWithCircle() {
        Polygon p = new Polygon("4 0 0 4 0 4 4 0 4");
        Circle c = new Circle("10 10 1");

        ITransform tP = new Transform(new Point(0, 0), 0, 0, 1);
        ITransform tC = new Transform(new Point(10, 10), 0, 0, 1);

        ColliderPolygon colliderPol = new ColliderPolygon(p, tP);
        ColliderCircle colliderCir = new ColliderCircle(c, tC);

        assertFalse(colliderPol.colides(colliderCir));
    }

    @Test
    public void pointInsidePolygon() {
        Polygon p = new Polygon("4 0 0 4 0 4 4 0 4");
        Point inside = new Point(2, 2);
        Point outside = new Point(5, 5);

        assertTrue(ColliderPolygon.pointIsInsidePolygon(inside, p));
        assertFalse(ColliderPolygon.pointIsInsidePolygon(outside, p));
    }

    @Test
    public void polygonCentroid() {
        Polygon p = new Polygon("4 0 0 4 0 4 4 0 4");
        ITransform t = new Transform(new Point(2, 2), 0, 0, 1);

        ColliderPolygon collider = new ColliderPolygon(p, t);
        Point centroid = collider.centroid();

        assertEquals(2.0, centroid.x(), 0.001);
        assertEquals(2.0, centroid.y(), 0.001);
    }

    @Test
    public void rayIntersectsSegment() {
        Point p1 = new Point(2, 2);
        Point a1 = new Point(1, 1);
        Point b1 = new Point(3, 3);
        assertTrue(ColliderPolygon.rayIntersectsSegment(p1, a1, b1));

        Point p2 = new Point(2, 2);
        Point a2 = new Point(2, 1);
        Point b2 = new Point(2, 3);
        assertTrue(ColliderPolygon.rayIntersectsSegment(p2, a2, b2));

        // Touches the endpoint
        Point p4 = new Point(3, 3);
        Point a4 = new Point(1, 1);
        Point b4 = new Point(3, 3);
        assertTrue(ColliderPolygon.rayIntersectsSegment(p4, a4, b4));
    }

    @Test
    public void rayDoesNotIntersectSegment() {
        Point p1 = new Point(2, 4);
        Point a1 = new Point(1, 1);
        Point b1 = new Point(3, 3);
        assertFalse(ColliderPolygon.rayIntersectsSegment(p1, a1, b1));

        Point p2 = new Point(2, 0);
        Point a2 = new Point(1, 1);
        Point b2 = new Point(3, 3);
        assertFalse(ColliderPolygon.rayIntersectsSegment(p2, a2, b2));

        Point p3 = new Point(3, 2);
        Point a3 = new Point(2, 1);
        Point b3 = new Point(2, 3);
        assertFalse(ColliderPolygon.rayIntersectsSegment(p3, a3, b3));

        Point p5 = new Point(2, 2);
        Point a5 = new Point(1, 3);
        Point b5 = new Point(3, 3);
        assertFalse(ColliderPolygon.rayIntersectsSegment(p5, a5, b5));
    }
}