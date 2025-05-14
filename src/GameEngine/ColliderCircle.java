package GameEngine;

import Figures.Circle;
import Figures.Point;

/**
 * Class that represents a collider for a circle object.
 * Provides the logic to check for collisions with other colliders and centers the figure according to the transform.
 * @author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version: 1.4 (09/05/25)
 * @inv transform != null && circleCollider != null
 **/
public class ColliderCircle implements ICollider {
    
    private Circle circleCollider;

    /**
     * Constructor for ColliderCircle.
     * Initializes the collider and centers the figure according to the transform.
     * @param c the circle to be used for the collider
     * @param t the transform to apply to the circle
     * @throws IllegalArgumentException if the circle or transform is null
     */
    public ColliderCircle(Circle c, ITransform t) {

        if (c == null || t == null) {
            throw new IllegalArgumentException("Nor circle nor transform can be null");
        }

        // Creates a copy of the preset figure of the collider for future reuse
        Circle preset = c.clone();

        // Gets the centroid of the preset to know the starting point
        Point presetPos = preset.centroid();

        // Applies the transformations to the preset
        preset = preset.scale(t.scale());
        // Moves the preset so that the centroid is at the position of the transform's centroid
        preset = preset.translate(t.position().x() - presetPos.x(), t.position().y() - presetPos.y());
        preset = preset.rotate(t.angle(), t.position());

        // We now have a copy of our preset according to the GUI model
        circleCollider = preset;
    }

    /**
     * Checks if a circle collides with a geometric figure by delegating the call to the appropriate method
     * @param other geometric figure to be checked
     * @return true if they collide, false otherwise
     * @throws IllegalArgumentException if the other collider is null
     */
    @Override
    public boolean colides(ICollider other) {
        if (other == null) {
            throw new IllegalArgumentException("Collider cannot be null");
        }
        return other.colidesWithCircle(this);
    }

    /**
     * Checks if a polygon collides with a circle
     * @param cp polygon to be checked
     * @return true if they collide, false otherwise
     * @throws IllegalArgumentException if the polygon collider is null
     */
    @Override
    public boolean colidesWithPolygon(ColliderPolygon cp) {
        if (cp == null) {
            throw new IllegalArgumentException("Collider cannot be null");
        }
        return cp.colidesWithCircle(this);
    }

    /**
     * Checks if a circle collides with another circle
     * @param cc circle to be checked
     * @return true if they collide, false otherwise
     * @throws IllegalArgumentException if the circle collider is null
     */
    public boolean colidesWithCircle(ColliderCircle cc) {
        if (cc == null) {
            throw new IllegalArgumentException("Circle collider cannot be null");
        }
        double distance = this.centroid().distance(cc.centroid());
        double r1 = this.circleCollider.radius();
        double r2 = cc.circleCollider.radius();
        return distance < r1 + r2;
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    /**
     * Returns the centroid of the collider
     * @return centroid of the collider
     */
    public Point centroid() {
        return circleCollider.centroid();
    }

    /**
     * Converts the collision information to a string
     * @return string with the collision information
     */
    public String toString() {
        return circleCollider.toString();
    }

    /**
     * Returns the circle of the collider
     * @return circle of the collider
     */
    public Circle getFigure() {
        return circleCollider;
    }
}
