package Figures;
import GameEngine.ICollider;
import GameEngine.ITransform;
import GameEngine.ColliderPolygon;

/** 
 * Class that creates and manipulates polygons.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.0 (12/04/25)
 * @inv The polygon must have at least 3 vertices.
 *      Three consecutive points cannot be collinear.
 *      No edge can intersect another.
 **/
public class Polygon extends GeometricFigure {
    
    private Point[] points;
    private Segment[] segments;

    /**
     * Constructor for a polygon
     * @param s string in the format "n x1 y1 x2 y2 ... xn yn"
     * @throws IllegalArgumentException if the string is invalid or the polygon has less than 3 vertices
     */
    public Polygon(String s) {
        String[] tokens = s.split(" ");
        int n = Integer.parseInt(tokens[0]);

        if(n < 3) {
            System.out.println("Polygon:vi");
            throw new IllegalArgumentException();
        }
        if(tokens.length != 2 * n + 1) {
            System.out.println("Polygon:vi");
            throw new IllegalArgumentException();
        }

        points = new Point[n];
        segments = new Segment[n];
        try {
            for(int i = 0; i < n; i++) {
                points[i] = new Point(Double.parseDouble(tokens[2 * i + 1]), Double.parseDouble(tokens[2 * i + 2]));
            }
        } catch (NumberFormatException e) {
            System.out.println("Polygon:vi");
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < n; i++) {
            segments[i] = new Segment(points[i], points[(i + 1) % n]);
        }

        // Checks if 3 consecutive points are collinear.
        // Uses GeometryUtils.orientation: if it returns 0, the points are collinear.
        for (int i = 0; i < n; i++) {
            Point a = points[i];
            Point b = points[(i + 1) % n];
            Point c = points[(i + 2) % n];
            if (GeometryUtils.orientation(a, b, c) == 0) {
                System.out.println("Polygon:vi");
                throw new IllegalArgumentException();
            }                      
        }

        // Checks if no edge intersects another.
        for (int i = 0; i < n; i++) {
            Segment s1 = segments[i];
            for (int j = i + 1; j < n; j++) {
                Segment s2 = segments[j];
                if (s1.intersects(s2)) {
                    System.out.println("Polygon:vi");
                    throw new IllegalArgumentException();
                }
            }
        }

    }

    /**
     * Moves all points of the polygon by dx units on the x-axis and dy units on the y-axis
     * @param dx units to be moved on the x-axis
     * @param dy units to be moved on the y-axis
     * @return an array of all new points with the shifted positions
     */
    protected Point[] translatePoints(double dx, double dy) {
        Point[] newPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            newPoints[i] = points[i].translate(dx, dy);
        }
        return newPoints;
    }

    /**
     * Converts an array of points into a string
     * @param points array of points
     * @return string with the points in the format "x1 y1 x2 y2 ... xn yn"
     */
    protected String pointsToString(Point[] points) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < points.length; i++) {
            sb.append(points[i].x()).append(" ").append(points[i].y());
            if (i < points.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * Moves the polygon by dx units on the x-axis and dy units on the y-axis
     * @param dx units to be moved on the x-axis
     * @param dy units to be moved on the y-axis
     * @return new moved polygon
     */
    @Override
    public Polygon translate(double dx, double dy) {
        Point[] newPoints = translatePoints(dx, dy);
        return new Polygon(newPoints.length + " " + pointsToString(newPoints));
    }

    /**
     * Calculates the centroid of the polygon
     * @return centroid of the polygon
     */
    public Point centroid() {
        double x = 0;
        double y = 0;
        double area = 0;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            Point _i = points[i];
            Point _j = points[(i + 1) % n]; // i + 1 Point and % n closes the loop
            double cross = _i.x() * _j.y() - _j.x() * _i.y();

            x += (_i.x() + _j.x()) * cross;
            y += (_i.y() + _j.y()) * cross;
            area += cross;
        }

        area /= 2;
        x /= 6 * area;
        y /= 6 * area;

        return new Point(x, y);
    }

    /**
     * Scales the polygon by a factor
     * @param factor scaling factor
     * @return new scaled polygon
     * @see The assignment PDF :)
     */
    public GeometricFigure scale(double factor) {
        Point centroid = centroid();
        int n = points.length;
        Point[] newPoints = new Point[n];
        for (int i = 0; i < n; i++) {
            double dx = points[i].x() - centroid.x();
            double dy = points[i].y() - centroid.y();
            newPoints[i] = new Point(centroid.x() + dx * factor, centroid.y() + dy * factor);
        }
        return new Polygon(n + " " + pointsToString(newPoints));
    }

    /**
     * Rotates the polygon by an angle around a point
     * @param angle rotation angle
     * @param center point around which the polygon will rotate
     * @return new rotated polygon
     * @see The assignment PDF :)
     */
    public GeometricFigure rotate(double angle, Point center) {
        int n = points.length;
        Point[] newPoints = new Point[n];
        double rad = Math.toRadians(angle);

        for (int i = 0; i < n; i++) {
            double dx = points[i].x() - center.x();
            double dy = points[i].y() - center.y();
            double x = dx * Math.cos(rad) - dy * Math.sin(rad) + center.x();
            double y = dx * Math.sin(rad) + dy * Math.cos(rad) + center.y();
            newPoints[i] = new Point(x, y);
        }
        return new Polygon(n + " " + pointsToString(newPoints));
    }

    /**
     * Checks if the polygon collides with another polygon
     * @param t figure to be checked
     * @return true if it collides, false otherwise
     */
    public ICollider colliderInit(ITransform t) {
        return new ColliderPolygon(this, t);
    }

    /**
     * Creates a copy of the polygon
     * @return copy of the polygon
     */
    public GeometricFigure clone() {
        return new Polygon(points.length + " " + pointsToString(points));
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < points.length; i++) {
            sb.append(points[i] + " ");
        }
        return sb.toString().trim();
    }

    public Point[] getPoints() {
        return points;
    }

    public Segment[] getSegments() {
        return segments;
    }
}
