package Tests.GameEngine;

import static org.junit.jupiter.api.Assertions.*;

import GameEngine.*;
import Figures.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransformTest {

    private Transform t;

    // ======= SETUP =======
    @BeforeEach
    void setUp() {
        t = new Transform(new Point(1, 2), 3, 45, 2.0);
    }

    // ======= TESTS =======

    // Tests Transform constructor validation
    @Test
    void constructor_throwsOnNullPosition() {
        assertThrows(IllegalArgumentException.class, () ->
            new Transform(null, 0, 0, 1));
    }

    @Test
    void constructor_throwsOnNegativeLayer() {
        assertThrows(IllegalArgumentException.class, () ->
            new Transform(new Point(0, 0), -1, 0, 1));
    }

    @Test
    void constructor_throwsOnNegativeScale() {
        assertThrows(IllegalArgumentException.class, () ->
            new Transform(new Point(0, 0), 0, 0, -1));
    }

    // Tests Transform.move()
    @Test
    void move_changesPositionAndLayer() {
        t.move(new Point(5, -2), 2);
        assertEquals(6, t.position().x(), 0.001);
        assertEquals(0, t.position().y(), 0.001);
        assertEquals(5, t.layer());
    }

    // Tests Transform.rotate()
    @Test
    void rotate_addsAngleAndWraps() {
        t.rotate(30);
        assertEquals(75, t.angle(), 0.001);
        t.rotate(300);
        assertEquals(15, t.angle(), 0.001);
        t.rotate(-30);
        assertEquals(345, t.angle(), 0.001);
    }

    // Tests Transform.scale()
    @Test
    void scale_addsScaleAndClampsToZero() {
        t.scale(3.5);
        assertEquals(5.5, t.scale(), 0.001);
        t.scale(-10);
        assertEquals(0, t.scale(), 0.001);
    }

    // Tests Transform.position(Point)
    @Test
    void position_setterThrowsOnNull() {
        assertThrows(IllegalArgumentException.class, () -> t.position(null));
    }

    // Tests Transform.layer(int)
    @Test
    void layer_setterThrowsOnNegative() {
        assertThrows(IllegalArgumentException.class, () -> t.layer(-5));
    }

    // Tests Transform.angle(double)
    @Test
    void angle_setterWrapsCorrectly() {
        t.angle(370);
        assertEquals(10, t.angle(), 0.001);
        t.angle(-30);
        assertEquals(330, t.angle(), 0.001);
    }
}