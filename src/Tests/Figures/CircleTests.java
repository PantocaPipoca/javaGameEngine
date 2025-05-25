package Tests.Figures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figures.Circle;
import Figures.Point;

public class CircleTests {

    // Tests Circle constructor and valid fields
    @Test
    public void validCircleTests() {
        assertDoesNotThrow(() -> {
            Circle c = new Circle("5 5 2.0");
            assertEquals(5, c.center().x());
            assertEquals(5, c.center().y());
            assertEquals(2.0, c.radius());
            assertEquals("(5,00,5,00) 2,00", c.toString().replace('.', ','));
            assertEquals(2 * Math.PI * 2.0, c.perimeter(), 1e-9);
        });
    }

    // Tests Circle constructor with invalid radius
    @Test
    public void invalidCircleTests() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Circle("5 5 -1.0");
        });
    }

    // Tests Circle.translate()
    @Test
    public void testTranslateValid() {
        Circle c = new Circle("5 5 2.0");
        Circle translated = c.translate(3, 4);
        assertEquals(8, translated.center().x());
        assertEquals(9, translated.center().y());
        assertEquals(2.0, translated.radius(), 1e-9);
        assertEquals("(8,00,9,00) 2,00", translated.toString().replace('.', ','));
    }

    // Tests Circle.clone()
    @Test
    public void testClone() {
        Circle c = new Circle("5 5 2.0");
        assertDoesNotThrow(() -> {
            Circle clone = c.clone();
            assertEquals(c.center().x(), clone.center().x());
            assertEquals(c.center().y(), clone.center().y());
            assertEquals(c.radius(), clone.radius());
            assertNotSame(c, clone);
        });

        // Tests if the clone is independent of the original
        Circle clone = c.clone();
        Circle scaled = c.scale(2.0);
        assertNotEquals(clone.radius(), scaled.radius());
    }

    // Tests Circle.scale()
    @Test
    public void testScale() {
        Circle c = new Circle("5 5 2.0");

        // Valid scaling
        assertDoesNotThrow(() -> {
            Circle scaled = c.scale(2.0);
            assertEquals(4.0, scaled.radius());
            assertEquals(c.center().x(), scaled.center().x());
            assertEquals(c.center().y(), scaled.center().y());
        });

        // Scaling with factor 1 (should be equal to the original)
        Circle scaled = c.scale(1.0);
        assertEquals(c.radius(), scaled.radius());

        // Scaling with a negative factor (invalid radius)
        assertThrows(IllegalArgumentException.class, () -> {
            c.scale(-1.0);
        });
    }

    // Tests Circle.rotate()
    @Test
    public void testRotate() {
        Circle c = new Circle("5 5 2.0");
        Point rotationCenter = new Point(0, 0);

        // Valid rotation (should not throw and should be equal to original)
        assertDoesNotThrow(() -> {
            Circle rotated = c.rotate(90, rotationCenter);
            assertEquals(c.center().x(), rotated.center().x());
            assertEquals(c.center().y(), rotated.center().y());
            assertEquals(c.radius(), rotated.radius());
        });

        // Rotation with angle 0 (should be equal to the original)
        Circle rotated = c.rotate(0, rotationCenter);
        assertEquals(c.center().x(), rotated.center().x());
        assertEquals(c.center().y(), rotated.center().y());
        assertEquals(c.radius(), rotated.radius());

        // Rotation with a negative angle (should not throw)
        assertDoesNotThrow(() -> {
            Circle rotatedNegative = c.rotate(-45, rotationCenter);
            assertEquals(c.center().x(), rotatedNegative.center().x());
            assertEquals(c.center().y(), rotatedNegative.center().y());
            assertEquals(c.radius(), rotatedNegative.radius());
        });

        // Rotation with a different rotation point (should not throw)
        Point anotherCenter = new Point(10, 10);
        assertDoesNotThrow(() -> {
            Circle rotatedAnotherCenter = c.rotate(45, anotherCenter);
            assertEquals(c.center().x(), rotatedAnotherCenter.center().x());
            assertEquals(c.center().y(), rotatedAnotherCenter.center().y());
            assertEquals(c.radius(), rotatedAnotherCenter.radius());
        });
    }
}