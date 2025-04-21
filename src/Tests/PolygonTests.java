package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Figures.GeometricFigure;
import Figures.Polygon;
import Figures.Point;

public class PolygonTests {

    @Test
    public void testValidPolygon() {
        assertDoesNotThrow(() -> {
            // Square
            Polygon p = new Polygon("4 5 5 8 6 8 7 5 7");
            assertEquals("(5.00,5.00) (8.00,6.00) (8.00,7.00) (5.00,7.00)", p.toString());
        });
        assertDoesNotThrow(() -> {
            // Triangle
            Polygon p = new Polygon("3 1 1 3 1 2 4");
            assertEquals("(1.00,1.00) (3.00,1.00) (2.00,4.00)", p.toString());
        });
    }

    @Test
    public void testInvalidPolygon() {
        // Too few vertices
        assertThrows(IllegalArgumentException.class, () -> {
            new Polygon("2 1 1 2 2");
        });

        // Incorrect number of tokens
        assertThrows(IllegalArgumentException.class, () -> {
            new Polygon("4 5 5 8 6 8 7 5");
        });

        // Collinear points
        assertThrows(IllegalArgumentException.class, () -> {
            new Polygon("4 0 0 2 2 4 4 0 4");
        });

        // Self-intersecting polygon
        assertThrows(IllegalArgumentException.class, () -> {
            new Polygon("4 0 0 4 4 0 4 4 0");
        });
    }

    @Test
    public void testTranslate() {
        Polygon p = new Polygon("6 5 5 8 6 8 7 5 7 11 12 1 5");
        Polygon res = p.translate(1, 3);
        assertEquals("(6.00,8.00) (9.00,9.00) (9.00,10.00) (6.00,10.00) (12.00,15.00) (2.00,8.00)", res.toString());
        assertEquals("(5.00,5.00) (8.00,6.00) (8.00,7.00) (5.00,7.00) (11.00,12.00) (1.00,5.00)", p.toString()); // p should not be modified

        p = new Polygon("6 5 5 8 6 8 7 5 7 11 12 1 5");
        res = p.translate(0, 0);
        assertEquals("(5.00,5.00) (8.00,6.00) (8.00,7.00) (5.00,7.00) (11.00,12.00) (1.00,5.00)", res.toString());
        assertEquals("(5.00,5.00) (8.00,6.00) (8.00,7.00) (5.00,7.00) (11.00,12.00) (1.00,5.00)", p.toString()); // p should not be modified
    }

    @Test
    public void testCentroid() {
        // Square
        Polygon p1 = new Polygon("4 0 0 4 0 4 4 0 4");
        Point c1 = p1.centroid();
        assertEquals(2.0, c1.x(), 1e-9);
        assertEquals(2.0, c1.y(), 1e-9);

        // Rectangle
        Polygon p2 = new Polygon("4 1 1 5 1 5 3 1 3");
        Point c2 = p2.centroid();
        assertEquals(3.0, c2.x(), 1e-9);
        assertEquals(2.0, c2.y(), 1e-9);

        // Triangle
        Polygon p3 = new Polygon("3 0 0 6 0 3 6");
        Point c3 = p3.centroid();
        assertEquals(3.0, c3.x(), 1e-9);
        assertEquals(2.0, c3.y(), 1e-9);
    }

    @Test
    public void testClone() {
        // Square
        Polygon original = new Polygon("4 0 0 4 0 4 4 0 4");
        GeometricFigure cloneFigura = original.clone();
        assertNotSame(original, cloneFigura);
        assertEquals(original.toString(), cloneFigura.toString());

        // Triangle
        Polygon tri = new Polygon("3 0 0 3 0 2 5");
        Polygon triClone = (Polygon) tri.clone();
        assertNotSame(tri, triClone);
        assertEquals(tri.toString(), triClone.toString());

        // Pentagon
        Polygon penta = new Polygon("5 0 0 4 0 6 2 4 4 2 4");
        Polygon pentaClone = (Polygon) penta.clone();
        assertEquals("(0.00,0.00) (4.00,0.00) (6.00,2.00) (4.00,4.00) (2.00,4.00)", penta.toString());
        assertEquals(penta.toString(), pentaClone.toString());

        // Verify that changes to the original do not affect the clone
        Polygon altered = penta.translate(1, 1);
        assertNotEquals(altered.toString(), pentaClone.toString());

        // Creating another clone
        Polygon anotherClone = (Polygon) penta.clone();
        assertEquals(pentaClone.toString(), anotherClone.toString());
        assertNotSame(pentaClone, anotherClone);
    }

    @Test
    public void testScale() {
        // Scaling by factor 2
        Polygon square = new Polygon("4 0 0 4 0 4 4 0 4");
        Polygon scaledSquare = (Polygon) square.scale(2.0);
        assertEquals("(-2.00,-2.00) (6.00,-2.00) (6.00,6.00) (-2.00,6.00)", scaledSquare.toString());

        // Verify that the original was not modified
        assertEquals("(0.00,0.00) (4.00,0.00) (4.00,4.00) (0.00,4.00)", square.toString());

        // Centroid scaled by 0.5
        Polygon triangle = new Polygon("3 0 0 6 0 3 6");
        Polygon scaledTriangle = (Polygon) triangle.scale(0.5);
        assertEquals("(1.50,1.00) (4.50,1.00) (3.00,4.00)", scaledTriangle.toString());

        // Scale with factor 1 (no changes)
        Polygon sameSize = (Polygon) square.scale(1.0);
        assertEquals(square.toString(), sameSize.toString());

        // Null or negative factor
        assertThrows(IllegalArgumentException.class, () -> square.scale(0));
    }

    @Test
    public void testRotate() {
        Polygon square = new Polygon("4 0 0 4 0 4 4 0 4");
        Point center = new Point(2, 2);

        // Rotation of 90 degrees around the center (2,2)
        Polygon rotatedSquare90 = (Polygon) square.rotate(90, center);
        assertEquals("(4.00,0.00) (4.00,4.00) (0.00,4.00) (0.00,0.00)", rotatedSquare90.toString());
        // Verify that the original was not modified
        assertEquals("(0.00,0.00) (4.00,0.00) (4.00,4.00) (0.00,4.00)", square.toString());

        // Rotation of 0 degrees (no changes)
        Polygon notRotated = (Polygon) square.rotate(0, center);
        assertEquals(square.toString(), notRotated.toString());

        // Rotation of 180 degrees around the same center
        Polygon rotatedSquare180 = (Polygon) square.rotate(180, center);
        assertEquals("(4.00,4.00) (0.00,4.00) (-0.00,0.00) (4.00,-0.00)", rotatedSquare180.toString());

        // Rotation of -90 degrees
        Polygon rotatedSquareNeg90 = (Polygon) square.rotate(-90, center);
        assertEquals("(0.00,4.00) (0.00,0.00) (4.00,0.00) (4.00,4.00)", rotatedSquareNeg90.toString());

        // Rotation around the origin (0,0)
        Polygon tri = new Polygon("3 1 1 3 1 2 5");
        Polygon rotatedTri = (Polygon) tri.rotate(90, new Point(0, 0));
        assertNotEquals(tri.toString(), rotatedTri.toString()); // Just verifies that it changed
    }
}