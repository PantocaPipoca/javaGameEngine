package Tests.Figures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figures.Circle;
import Figures.GeometryUtils;
import Figures.Point;
import Figures.Segment;

public class SegmentTests {

    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Segment(new Point(1, 1), new Point(2, 2)));
        assertDoesNotThrow(() -> new Segment(new Point(0, 0), new Point(5, 5)));

        assertThrows(IllegalArgumentException.class, () -> new Segment(new Point(1, 1), new Point(1, 1)));
        assertThrows(IllegalArgumentException.class, () -> new Segment(new Point(2, 2), new Point(2, 2)));
    }

    @Test
    public void testOrientation() {
        assertEquals(0, GeometryUtils.orientation(new Point(2, 2), new Point(4, 4), new Point(6, 6)));

        assertNotEquals(0, GeometryUtils.orientation(new Point(0, 0), new Point(4, 4), new Point(1, 2)));
        assertNotEquals(2, GeometryUtils.orientation(new Point(2, 2), new Point(4, 4), new Point(6, 6)));
    }

    @Test
    public void testOnSegment() {
        assertTrue(GeometryUtils.onSegment(new Point(0, 0), new Point(2, 2), new Point(4, 4)));
        assertTrue(GeometryUtils.onSegment(new Point(0, 0), new Point(1, 1), new Point(4, 4)));
        assertTrue(GeometryUtils.onSegment(new Point(0, 0), new Point(3, 3), new Point(4, 4)));

        assertFalse(GeometryUtils.onSegment(new Point(0, 0), new Point(5, 5), new Point(4, 4)));
    }

    @Test
    public void testIntersectsValid() {
        Circle c = new Circle("5 5 3.0");
        Segment s = new Segment(new Point(2, 5), new Point(8, 5));
        assertTrue(s.intersects(c));
    }

    @Test
    public void testIntersectsInvalid() {
        Circle c = new Circle("5 5 3.0");
        Segment s = new Segment(new Point(0, 0), new Point(1, 1));
        assertFalse(s.intersects(c));
    }
}