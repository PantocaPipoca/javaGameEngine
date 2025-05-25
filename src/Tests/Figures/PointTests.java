package Tests.Figures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figures.Point;

public class PointTests {

    // Tests Point constructor
    @Test
    public void testConstructor() {
        // Valid constructors
        assertDoesNotThrow(() -> new Point(0, 0));
        assertDoesNotThrow(() -> new Point(3, 4));
        assertDoesNotThrow(() -> new Point(-5, 7));
        // Extreme values
        assertDoesNotThrow(() -> new Point(Double.MAX_VALUE, Double.MAX_VALUE));
        assertDoesNotThrow(() -> new Point(Double.MIN_VALUE, Double.MIN_VALUE));
    }

    // Tests Point.distance()
    @Test
    public void testDistance() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(-3, -4);

        // Valid distances
        assertEquals(5.0, p1.distance(p2), 1e-9);
        assertEquals(5.0, p2.distance(p1), 1e-9);
        assertEquals(10.0, p2.distance(p3), 1e-9);

        // Invalid distances
        assertNotEquals(6.0, p1.distance(p2));
        assertNotEquals(4.0, p2.distance(p3));
    }

    // Tests Point.translate()
    @Test
    public void testTranslate() {
        Point p1 = new Point(1, 1);

        // Valid translation
        Point p2 = p1.translate(3, 4);
        assertEquals(4.0, p2.x(), 1e-9);
        assertEquals(5.0, p2.y(), 1e-9);

        // Negative translation
        Point p3 = p1.translate(-1, -1);
        assertEquals(0.0, p3.x(), 1e-9);
        assertEquals(0.0, p3.y(), 1e-9);

        // Zero translation
        Point p4 = p1.translate(0, 0);
        assertEquals(p1.x(), p4.x(), 1e-9);
        assertEquals(p1.y(), p4.y(), 1e-9);

        // Extreme translation
        Point p5 = p1.translate(Double.MAX_VALUE, Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE + 1, p5.x(), 1e-9);
        assertEquals(Double.MAX_VALUE + 1, p5.y(), 1e-9);
    }

    // Tests Point.toString()
    @Test
    public void testToString() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(-5, -7);

        // Valid representation
        assertEquals("(3,00,4,00)", p1.toString());
        assertEquals("(-5,00,-7,00)", p2.toString());

        // Representation with extreme values
        Point p3 = new Point(Double.MAX_VALUE, Double.MIN_VALUE);
        assertDoesNotThrow(() -> p3.toString());
    }

    // Tests Point.r() and Point.theta()
    @Test
    public void testPolarCoordinates() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(-3, -4);

        // r values
        assertEquals(5.0, p1.r());
        assertEquals(5.0, p2.r());

        // theta values
        assertEquals(Math.atan2(4, 3), p1.theta());
        assertEquals(Math.atan2(-4, -3), p2.theta());

        // Extreme values
        Point p3 = new Point(Double.MAX_VALUE, Double.MIN_VALUE);
        assertDoesNotThrow(() -> p3.r());
        assertDoesNotThrow(() -> p3.theta());
    }
}