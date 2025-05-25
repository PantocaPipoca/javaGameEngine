package Tests.Figures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figures.Circle;
import Figures.Point;
import Figures.Segment;

public class SegmentTests {

    // Tests Segment constructor validation
    @Test
    public void testConstructor() {
        assertDoesNotThrow(() -> new Segment(new Point(1, 1), new Point(2, 2)));
        assertDoesNotThrow(() -> new Segment(new Point(0, 0), new Point(5, 5)));

        assertThrows(IllegalArgumentException.class, () -> new Segment(new Point(1, 1), new Point(1, 1)));
        assertThrows(IllegalArgumentException.class, () -> new Segment(new Point(2, 2), new Point(2, 2)));
    }

    // Tests Segment.intersects(Circle)
    @Test
    public void testIntersectsValid() {
        Circle c = new Circle("5 5 3.0");
        Segment s = new Segment(new Point(2, 5), new Point(8, 5));
        assertTrue(s.intersects(c));
    }

    // Tests Segment.intersects(Circle)
    @Test
    public void testIntersectsInvalid() {
        Circle c = new Circle("5 5 3.0");
        Segment s = new Segment(new Point(0, 0), new Point(1, 1));
        assertFalse(s.intersects(c));
    }
}