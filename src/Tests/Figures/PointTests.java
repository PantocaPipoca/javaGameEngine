package Tests.Figures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figures.Point;

public class PointTests {

    @Test
    public void testConstructor() {
        // Tests valid constructors
        assertDoesNotThrow(() -> new Point(0, 0));
        assertDoesNotThrow(() -> new Point(3, 4));
        assertDoesNotThrow(() -> new Point(-5, 7));

        // Tests extreme values
        assertDoesNotThrow(() -> new Point(Double.MAX_VALUE, Double.MAX_VALUE));
        assertDoesNotThrow(() -> new Point(Double.MIN_VALUE, Double.MIN_VALUE));
    }

    @Test
    public void testDistance() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(-3, -4);

        // Tests valid distances
        assertEquals(5.0, p1.distance(p2), 1e-9);
        assertEquals(5.0, p2.distance(p1), 1e-9);
        assertEquals(10.0, p2.distance(p3), 1e-9);

        // Tests invalid distances
        assertNotEquals(6.0, p1.distance(p2));
        assertNotEquals(4.0, p2.distance(p3));
    }

    @Test
    public void testTranslate() {
        Point p1 = new Point(1, 1);

        // Tests valid translation
        Point p2 = p1.translate(3, 4);
        assertEquals(4.0, p2.x(), 1e-9);
        assertEquals(5.0, p2.y(), 1e-9);

        // Tests negative translation
        Point p3 = p1.translate(-1, -1);
        assertEquals(0.0, p3.x(), 1e-9);
        assertEquals(0.0, p3.y(), 1e-9);

        // Tests zero translation
        Point p4 = p1.translate(0, 0);
        assertEquals(p1.x(), p4.x(), 1e-9);
        assertEquals(p1.y(), p4.y(), 1e-9);

        // Tests extreme translation
        Point p5 = p1.translate(Double.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE + 1, p5.x(), 1e-9);
        assertEquals(Double.MAX_VALUE + 1, p5.y(), 1e-9);
    }

    @Test
    public void testToString() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(-5, -7);

        // Tests valid representation
        assertEquals("(3.00,4.00)", p1.toString());
        assertEquals("(-5.00,-7.00)", p2.toString());

        // Tests representation with extreme values
        Point p3 = new Point(Double.MAX_VALUE, Double.MIN_VALUE);
        assertDoesNotThrow(() -> p3.toString());
    }

    @Test
    public void testPolarCoordinates() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(-3, -4);

        // Tests r values
        assertEquals(5.0, p1.r());
        assertEquals(5.0, p2.r());

        // Tests theta values
        assertEquals(Math.atan2(4, 3), p1.theta());
        assertEquals(Math.atan2(-4, -3), p2.theta());

        // Tests extreme values
        Point p3 = new Point(Double.MAX_VALUE, Double.MIN_VALUE);
        assertDoesNotThrow(() -> p3.r());
        assertDoesNotThrow(() -> p3.theta());
    }
}