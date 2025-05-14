package Figures;

import GameEngine.ITransform;
import GameEngine.ICollider;
import GameEngine.ColliderCircle;

/**
 * Class that creates and manipulates circles.
 * Provides methods for translation, scaling, and collision creation.
 * @author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version: 1.1 (12/04/25)
 * @inv The circle needs to have a radius > 0
 **/
public class Circle extends GeometricFigure {
    private Point center;
    private double radius;

    /**
     * Constructor for a circle
     * @param s string in the format "cx cy r"
     * @throws IllegalArgumentException if the string is invalid or the radius is negative
     */
    public Circle(String s) {
        String[] tokens = s.split(" ");
        if (tokens.length != 3) {
            System.out.println("Circle:vi");
            throw new IllegalArgumentException();
        }
        if (Double.parseDouble(tokens[2]) < 0) {
            System.out.println("Circle:vi");
            throw new IllegalArgumentException();
        }

        try {
            double cx = Double.parseDouble(tokens[0]);
            double cy = Double.parseDouble(tokens[1]);
            double r = Double.parseDouble(tokens[2]);
            center = new Point(cx, cy);
            radius = r;
        } catch (NumberFormatException e) {
            System.out.println("Circle:vi");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Creates a new instance of the circle moved by dx units on the x-axis and dy units on the y-axis
     * @param dx units to be moved on the x-axis
     * @param dy units to be moved on the y-axis
     * @return new moved circle
     */
    @Override
    public Circle translate(double dx, double dy) {
        Point newCenter = center.translate(dx, dy);
        return new Circle(newCenter.x() + " " + newCenter.y() + " " + radius);
    }

    /**
     * Scales the circle by the given factor.
     * @param factor the scaling factor
     * @return a new circle scaled by the factor
     * @throws IllegalArgumentException if the factor is negative
     */
    @Override
    public Circle scale(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Scaling factor must be greater than 0.");
        }
        return new Circle(center.x() + " " + center.y() + " " + radius * factor);
    }

    /**
     * Creates a new instance of the circle rotated by an angle (In the end, it does nothing)
     * @param angle rotation angle
     * @param center point around which the circle will rotate
     * @return new circle equal to the previous one but rotated
     */
    public Circle rotate(double angle, Point center) {
        return this;
    }

    /**
     * Checks if the circle collides with a geometric figure
     * @param t geometric figure to be checked
     * @return true if they collide, false otherwise
     */
    public ICollider colliderInit(ITransform t) {
        return new ColliderCircle(this, t);
    }

    /**
     * Returns an exact copy of the circle
     * @return copy of the circle
     */
    public Circle clone() {
        return new Circle(center.x() + " " + center.y() + " " + radius);
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    /**
     * Calculates the perimeter of the circle
     * @return perimeter of the circle
     */
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    /**
     * @return String in the format "Circle: (x,y) r"
     */
    @Override
    public String toString() {
        return String.format("%s %.2f", center.toString(), radius);
    }

    // Getters and Setters
    public Point center() {
        return center;
    }

    public double radius() {
        return radius;
    }

    public Point centroid() {
        return center;
    }
}