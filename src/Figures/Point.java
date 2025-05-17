package Figures;

/**
 * Class that defines a point in the Cartesian and polar planes.
 * Provides methods for translation and distance calculations.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.2 (26/03/25)
 * @inv A point is defined by its x and y coordinates.
 */
public class Point {
    private double r, theta;
    private double x, y;

    /**
     * Constructor for a point using Cartesian coordinates.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        r = Math.sqrt(x * x + y * y);
        theta = Math.atan2(y, x);
    }

    /**
     * Calculates the distance between two points.
     * @param that point to calculate the distance to
     * @return distance between the two points
     */
    public double distance(Point that) {
        return Math.sqrt(this.r() * this.r() + that.r() * that.r() - 2 * this.r() * that.r() * Math.cos(that.theta() - this.theta()));
    }

    /**
     * Moves the point by dx units on the x-axis and dy units on the y-axis.
     * @param dx units to move on the x-axis
     * @param dy units to move on the y-axis
     * @return new moved point
     */
    public Point translate(double dx, double dy) {
        return new Point(this.x() + dx, this.y() + dy);
    }

    /////////////////////////////////////////////////// Getters and Setters ///////////////////////////////////////////////////

    /**
     * Returns the string representation of the point.
     * @return string in the format "(x, y)"
     */
    @Override
    public String toString() {
        return String.format("(%.2f,%.2f)", x, y);
    }

    /**
     * Returns the radius (distance from origin) in polar coordinates.
     * @return the radius
     */
    public double r() {
        return r;
    }

    /**
     * Returns the angle (theta) in polar coordinates.
     * @return the angle in radians
     */
    public double theta() {
        return theta;
    }

    /**
     * Returns the x-coordinate of the point.
     * @return the x-coordinate
     */
    public double x() {
        return x;
    }

    /**
     * Returns the y-coordinate of the point.
     * @return the y-coordinate
     */
    public double y() {
        return y;
    }
}