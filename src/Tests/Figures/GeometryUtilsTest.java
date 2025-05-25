package Tests.Figures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figures.Point;
import Figures.GeometryUtils;

public class GeometryUtilsTest {

    // Tests GeometryUtils.normalize()
    @Test
    public void testNormalize() {
        // Normal vector
        Point p = new Point(3, 4);
        Point norm = GeometryUtils.normalize(p);
        assertEquals(0.6, norm.x(), 1e-9);
        assertEquals(0.8, norm.y(), 1e-9);

        // Zero vector
        Point zero = new Point(0, 0);
        Point normZero = GeometryUtils.normalize(zero);
        assertEquals(0.0, normZero.x(), 1e-9);
        assertEquals(0.0, normZero.y(), 1e-9);

        // Negative vector
        Point neg = new Point(-3, -4);
        Point normNeg = GeometryUtils.normalize(neg);
        assertEquals(-0.6, normNeg.x(), 1e-9);
        assertEquals(-0.8, normNeg.y(), 1e-9);
    }

    // Tests GeometryUtils.orientation()
    @Test
    public void testOrientation() {
        Point p = new Point(0, 0);
        Point q = new Point(4, 4);
        Point r = new Point(1, 2);

        // Collinear
        assertEquals(0, GeometryUtils.orientation(p, q, new Point(2, 2)));

        // Left of segment
        int leftOrRight = GeometryUtils.orientation(p, q, r);
        assertTrue(leftOrRight == 1 || leftOrRight == 2);
    }

    // Tests GeometryUtils.onSegment()
    @Test
    public void testOnSegment() {
        Point p = new Point(0, 0);
        Point q = new Point(2, 2);
        Point r = new Point(4, 4);

        // q is on segment pr
        assertTrue(GeometryUtils.onSegment(p, q, r));

        // q is not on segment pr
        Point notOn = new Point(5, 5);
        assertFalse(GeometryUtils.onSegment(p, notOn, r));

        // q is at the start
        assertTrue(GeometryUtils.onSegment(p, p, r));

        // q is at the end
        assertTrue(GeometryUtils.onSegment(p, r, r));
    }
}