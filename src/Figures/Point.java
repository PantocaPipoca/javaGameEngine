package Figures;

/** 
 * Defines a point in the Cartesian and polar planes.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.2 (26/03/25)
 **/
public class Point {
    private double r, theta;
    private double x, y;

    /**
     * Constructor for a point using Cartesian coordinates
     * @param x x-coordinate
     * @param y y-coordinate
     **/
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        r = Math.sqrt(x * x + y * y);
        theta = Math.atan2(y, x);
    }

    /**
     * Calculates the distance between two points
     * @param that point to calculate the distance to
     * @return distance between the two points
     */
    public double distance(Point that) {
        return Math.sqrt(this.r() * this.r() + that.r() * that.r() - 2 * this.r() * that.r() * Math.cos(that.theta() - this.theta()));
    }

    /**
     * Moves the point by dx units on the x-axis and dy units on the y-axis
     * @param dx units to move on the x-axis
     * @param dy units to move on the y-axis
     * @return new moved point
     */
    public Point translate(double dx, double dy) {
        return new Point(this.x() + dx, this.y() + dy);
    }


    // Getters and Setters
    public String toString() {
        return String.format("(%.2f,%.2f)", x, y);
    }
    public double r() {
        return r;
    }
    public double theta() {
        return theta;
    }
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
}