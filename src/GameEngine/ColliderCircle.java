package GameEngine;

import Figures.Circle;
import Figures.Point;

/**
 * Class that represents a collider for a circle object.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.0 (12/04/25)
 **/
public class ColliderCircle implements ICollider {
    
    private Circle circleCollider;

    public ColliderCircle(Circle c, ITransform t) {
        
        // Creates a copy of the preset figure of the collider for future reuse (Also because it's immutable, right?)
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
     * Checks if a circle collides with a geometric figure
     * @param other geometric figure to be checked
     * @return true if they collide, false otherwise
     */
    @Override
    public boolean colides(ICollider other) {
        return other.colidesWithCircle(this);
    }

    /**
     * Checks if a polygon collides with a circle
     * @param cp polygon to be checked
     * @return true if they collide, false otherwise
     */
    @Override
    public boolean colidesWithPolygon(ColliderPolygon cp) {
        return cp.colidesWithCircle(this);
    }

    /**
     * Checks if a circle collides with another circle
     * @param cc circle to be checked
     * @return true if they collide, false otherwise
     */
    public boolean colidesWithCircle(ColliderCircle cc) {
        double distance = this.centroid().distance(cc.centroid());
        double r1 = this.circleCollider.radius();
        double r2 = cc.circleCollider.radius();
        return distance < r1 + r2;
    }

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
