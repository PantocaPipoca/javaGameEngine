package GameEngine;

import Figures.GeometricFigure;
import Figures.Point;

/**
 * Interface that represents a collider for a geometric object.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.0 (12/04/25)
 **/
public interface ICollider {
    /**
     * Returns the centroid of the collider
     * @return centroid of the collider
     */
    Point centroid();

    /**
     * Converts the collision information to a string
     * @return string with the collision information
     */
    public String toString();

    /**
     * Checks if the collider collides with a circle
     * @param cc circle collider to be checked
     * @return true if they collide, false otherwise
     */
    public boolean colidesWithCircle(ColliderCircle cc);

    /**
     * Checks if the collider collides with a polygon
     * @param cp polygon collider to be checked
     * @return true if they collide, false otherwise
     */
    public boolean colidesWithPolygon(ColliderPolygon cp);

    /**
     * Checks if the collider collides with another collider
     * @param other collider to be checked
     * @return true if they collide, false otherwise
     */
    public boolean colides(ICollider other);

    /**
     * Returns the geometric figure of the collider
     * @return geometric figure of the collider
     */
    public GeometricFigure getFigure();
}