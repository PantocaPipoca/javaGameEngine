package GameEngine;

import Figures.*;

/**
 * Class that represents a collider for a polygon object.
 * Provides the logic to check for collisions with other colliders and centers the figure according to the transform.
 * @author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version: 1.4 (09/05/25)
 * @inv transform != null && polygonCollider != null
 **/
public class ColliderPolygon implements ICollider {
    
    private Polygon polygonCollider;

    public ColliderPolygon(Polygon p, ITransform t) {

        if (p == null || t == null) {
            throw new IllegalArgumentException("Nor circle nor transform can be null");
        }

        // Creates a copy of the preset figure of the collider for future reuse (Also because it's immutable, right?)
        Polygon preset = (Polygon) p.clone();

        // Gets the centroid of the preset to know the starting point
        Point presetPos = preset.centroid();

        // Applies the transformations to the preset
        preset = (Polygon) preset.scale(t.scale());
        // Moves the preset so that the centroid is at the position of the transform's centroid
        preset = preset.translate(t.position().x() - presetPos.x(), t.position().y() - presetPos.y());
        preset = (Polygon) preset.rotate(t.angle(), t.position());

        // We now have a copy of our preset according to the GUI model
        polygonCollider = preset;

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
        return other.colidesWithPolygon(this);
    }

    /**
     * Checks if a polygon collides with a circle
     * @param cc circle to be checked
     * @return true if they collide, false otherwise
     * @throws IllegalArgumentException if the circle is null
     */
    @Override
    public boolean colidesWithCircle(ColliderCircle cc) {
        if (cc == null) {
            throw new IllegalArgumentException("Collider cannot be null");
        }
        Point[] points = polygonCollider.getPoints();
        // Checks if the center of the circle is inside the polygon
        if (pointIsInsidePolygon(cc.getFigure().centroid(), polygonCollider))
            return true;
        
        // For each segment of the polygon, checks if it intersects the circle
        for (Segment s : polygonCollider.getSegments()) {
            if (s.intersects(cc.getFigure()))
                return true;
        }
        
        // Checks if any vertex of the polygon is inside the circle
        for (Point p : points) {
            double dx = cc.getFigure().centroid().x() - p.x();
            double dy = cc.getFigure().centroid().y() - p.y();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < cc.getFigure().radius())
                return true;
        }
        return false;
    }

    /**
     * Checks if a polygon collides with another polygon
     * @param cp polygon to be checked
     * @return true if they collide, false otherwise
     * @throws IllegalArgumentException if the polygon collider is null
     */
    @Override
    public boolean colidesWithPolygon(ColliderPolygon cp) {
        if (cp == null) {
            throw new IllegalArgumentException("Collider cannot be null");
        }
        Segment[] segments1 = this.polygonCollider.getSegments();
        Segment[] segments2 = cp.polygonCollider.getSegments();
        Point[] points1 = this.polygonCollider.getPoints();
        Point[] points2 = cp.polygonCollider.getPoints();
        for (Segment s1 : segments1) {
            for (Segment s2 : segments2) {
                if (s1.intersects(s2))
                    return true;
            }
        }
        if (pointIsInsidePolygon(points1[0], cp.getFigure()))
            return true;
        if (pointIsInsidePolygon(points2[0], polygonCollider))
            return true;
        return false;
    }

    /**
     * Checks if a point is inside a polygon
     * @param point point to be checked
     * @param poly polygon to be checked
     * @return true if the point is inside the polygon, false otherwise
     */
    public static boolean pointIsInsidePolygon(Point point, Polygon poly) {
        int count = 0;
        Point[] points = poly.getPoints();
        int n = points.length;

        for (int i = 0; i < n; i++) {
            Point a = points[i];
            Point b = points[(i + 1) % n];
            if (rayIntersectsSegment(point, a, b)) {
                count++;
            }
        }
        return (count % 2 == 1);
    }

    /**
     * Checks if a ray intersects a line segment
     * @param p point to be checked
     * @param a starting point of the segment
     * @param b ending point of the segment
     * @return true if the ray intersects the segment, false otherwise
     */
    public static boolean rayIntersectsSegment(Point p, Point a, Point b) {
        if (a.y() > b.y()) {
            Point temp = a;
            a = b;
            b = temp;
        }
        if (p.y() < a.y() || p.y() > b.y()) {
            return false;
        }
        if (a.x() >= p.x() && b.x() >= p.x()) {
            return true;
        }
        if (a.x() < p.x() && b.x() < p.x()) {
            return false;
        }
        double m = (b.y() - a.y()) / (b.x() - a.x());
        double xi = a.x() + (p.y() - a.y()) / m;
        return p.x() <= xi;
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    /**
     * Returns the centroid of the collider
     * @return centroid of the collider
     */
    public Point centroid() {
        return polygonCollider.centroid();
    }

    /**
     * Converts the collision information to a string
     * @return string with the collision information
     */
    public String toString() {
        return polygonCollider.toString();
    }

    /**
     * Returns the polygon of the collider
     * @return polygon of the collider
     */
    public Polygon getFigure() {
        return polygonCollider;
    }

}
