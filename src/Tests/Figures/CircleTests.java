package Tests.Figures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figures.Circle;
import Figures.Point;

public class CircleTests {

    @Test
    public void validCircleTests() {
        // Tests valid constructor
        assertDoesNotThrow(() -> {
            Circle c = new Circle("5 5 2.0");
            assertEquals(5, c.center().x());
            assertEquals(5, c.center().y());
            assertEquals(2.0, c.radius());
            assertEquals("(5.00,5.00) 2.00", c.toString());
            assertEquals(2 * Math.PI * 2.0, c.perimeter(), 1e-9);
        });
    }

    @Test
    public void invalidCircleTests() {
        // Tests error for negative radius
        assertThrows(IllegalArgumentException.class, () -> {
            new Circle("5 5 -1.0");
        });
    }

    @Test
    public void testTranslateValid() {
        Circle c = new Circle("5 5 2.0");
        Circle translated = c.translate(3, 4);
        assertEquals(8, translated.center().x());
        assertEquals(9, translated.center().y());
        assertEquals(2.0, translated.radius(), 1e-9);
        assertEquals("(8.00,9.00) 2.00", translated.toString());
    }

    @Test
    public void testClone() {
        Circle c = new Circle("5 5 2.0");
        
        // Tests if the clone does not throw exceptions
        assertDoesNotThrow(() -> {
            Circle clone = c.clone();
            assertEquals(c.center().x(), clone.center().x());
            assertEquals(c.center().y(), clone.center().y());
            assertEquals(c.radius(), clone.radius());
            assertNotSame(c, clone); // Verifies that it is not the same object
        });

        // Tests if the clone is independent of the original
        Circle clone = c.clone();
        Circle scaled = c.scale(2.0);
        assertNotEquals(clone.radius(), scaled.radius());
    }

    @Test
    public void testScale() {
        Circle c = new Circle("5 5 2.0");

        // Tests valid scaling
        assertDoesNotThrow(() -> {
            Circle scaled = c.scale(2.0);
            assertEquals(4.0, scaled.radius());
            assertEquals(c.center().x(), scaled.center().x());
            assertEquals(c.center().y(), scaled.center().y());
        });

        // Tests scaling with factor 1 (should be equal to the original)
        Circle scaled = c.scale(1.0);
        assertEquals(c.radius(), scaled.radius());

        // Tests scaling with a negative factor (invalid radius)
        assertThrows(IllegalArgumentException.class, () -> {
            c.scale(-1.0);
        });
    }

    @Test
    public void testRotate() {
        Circle c = new Circle("5 5 2.0");
        Point rotationCenter = new Point(0, 0);

        // Tests valid rotation (does nothing but should not throw exceptions)
        assertDoesNotThrow(() -> {
            Circle rotated = c.rotate(90, rotationCenter);
            assertEquals(c.center().x(), rotated.center().x());
            assertEquals(c.center().y(), rotated.center().y());
            assertEquals(c.radius(), rotated.radius());
        });

        // Tests rotation with angle 0 (should be equal to the original)
        Circle rotated = c.rotate(0, rotationCenter);
        assertEquals(c.center().x(), rotated.center().x());
        assertEquals(c.center().y(), rotated.center().y());
        assertEquals(c.radius(), rotated.radius());

        // Tests rotation with a negative angle (should not throw exceptions)
        assertDoesNotThrow(() -> {
            Circle rotatedNegative = c.rotate(-45, rotationCenter);
            assertEquals(c.center().x(), rotatedNegative.center().x());
            assertEquals(c.center().y(), rotatedNegative.center().y());
            assertEquals(c.radius(), rotatedNegative.radius());
        });

        // Tests rotation with a different rotation point (should not throw exceptions)
        Point anotherCenter = new Point(10, 10);
        assertDoesNotThrow(() -> {
            Circle rotatedAnotherCenter = c.rotate(45, anotherCenter);
            assertEquals(c.center().x(), rotatedAnotherCenter.center().x());
            assertEquals(c.center().y(), rotatedAnotherCenter.center().y());
            assertEquals(c.radius(), rotatedAnotherCenter.radius());
        });
    }
}