package GameEngine;

import Figures.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents a collider for a polygon object.
 * Provides the logic to check for collisions with other colliders and centers the figure according to the transform.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.4 (09/05/25)
 * @inv transform != null && polygonCollider != null
 **/
public class ColliderPolygon implements ICollider {
    
    private Polygon polygonCollider;

    /////////////////////////////////////////////////// Constructors ///////////////////////////////////////////////////

    /**
     * Constructs a ColliderPolygon with a polygon and a transform.
     * Applies scaling, translation, and rotation to the polygon according to the transform.
     * @param p the polygon to use as the base
     * @param t the transform to apply
     * @throws IllegalArgumentException if the polygon or transform is null
     */
    public ColliderPolygon(Polygon p, ITransform t) {
        if (p == null || t == null) {
            throw new IllegalArgumentException("Polygon and transform cannot be null");
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

    /////////////////////////////////////////////////// Collision Logic ///////////////////////////////////////////////////

    /**
     * Checks if a circle collides with a geometric figure by delegating the call to the appropriate method.
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
     * Checks if a polygon collides with a circle.
     * @param cc circle to be checked
     * @return true if they collide, false otherwise
     * @throws IllegalArgumentException if the circle is null
     */
    @Override
    public boolean colidesWithCircle(ColliderCircle cc) {
        if (cc == null) {
            throw new IllegalArgumentException("Collider cannot be null");
        }
        Point[] points = polygonCollider.points();
        // Checks if the center of the circle is inside the polygon
        if (pointIsInsidePolygon(cc.figure().centroid(), polygonCollider))
            return true;
        
        // For each segment of the polygon, checks if it intersects the circle
        for (Segment s : polygonCollider.segments()) {
            if (s.intersects(cc.figure()))
                return true;
        }
        
        // Checks if any vertex of the polygon is inside the circle
        for (Point p : points) {
            double dx = cc.figure().centroid().x() - p.x();
            double dy = cc.figure().centroid().y() - p.y();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < cc.figure().radius())
                return true;
        }
        return false;
    }

    /**
     * Checks if a polygon collides with another polygon.
     * @param cp polygon to be checked
     * @return true if they collide, false otherwise
     * @throws IllegalArgumentException if the polygon collider is null
     */
    @Override
    public boolean colidesWithPolygon(ColliderPolygon cp) {
        if (cp == null) {
            throw new IllegalArgumentException("Collider cannot be null");
        }
        Segment[] segments1 = this.polygonCollider.segments();
        Segment[] segments2 = cp.polygonCollider.segments();
        Point[] points1 = this.polygonCollider.points();
        Point[] points2 = cp.polygonCollider.points();
        for (Segment s1 : segments1) {
            for (Segment s2 : segments2) {
                if (s1.intersects(s2))
                    return true;
            }
        }
        if (pointIsInsidePolygon(points1[0], cp.figure()))
            return true;
        if (pointIsInsidePolygon(points2[0], polygonCollider))
            return true;
        return false;
    }

    /**
     * Checks if a point is inside a polygon.
     * @param point point to be checked
     * @param poly polygon to be checked
     * @return true if the point is inside the polygon, false otherwise
     */
    public static boolean pointIsInsidePolygon(Point point, Polygon poly) {
        int count = 0;
        Point[] points = poly.points();
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
     * Checks if a ray intersects a line segment.
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
        double m = (b.x() - a.x()) == 0 ? Double.POSITIVE_INFINITY : (b.y() - a.y()) / (b.x() - a.x());
        double xi = (m == Double.POSITIVE_INFINITY) ? a.x() : a.x() + (p.y() - a.y()) / m;
        return p.x() <= xi;
    }

    /////////////////////////////////////////////////// Getters and Setters ///////////////////////////////////////////////////

    /**
     * Returns the centroid of the collider.
     * @return centroid of the collider
     */
    public Point centroid() {
        return polygonCollider.centroid();
    }

    /**
     * Converts the collision information to a string.
     * @return string with the collision information
     */
    public String toString() {
        return polygonCollider.toString();
    }

    /**
     * Returns the polygon of the collider.
     * @return polygon of the collider
     */
    public Polygon figure() {
        return polygonCollider;
    }

    /**
     * Returns the list of points of the polygon collider.
     * @return list of points
     */
    public List<Point> points() {
        Point[] array = polygonCollider.points();
        List<Point> lista = new ArrayList<>();
        Collections.addAll(lista, array);
        return lista;
    }

    ///////////////////////////////////////////////////// Drawing ///////////////////////////////////////////////////

    /**
     * Draws the outline of the polygon collider.
     * @param g the Graphics context
     */
    public void drawOutline(Graphics g) {
        g.setColor(Color.ORANGE);
        Point[] pts = polygonCollider.points();
        int[] xs = new int[pts.length];
        int[] ys = new int[pts.length];
        for (int i = 0; i < pts.length; i++) {
            xs[i] = (int) pts[i].x();
            ys[i] = (int) pts[i].y();
        }
        g.drawPolygon(xs, ys, pts.length);
    }
}