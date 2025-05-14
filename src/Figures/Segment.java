package Figures;

/**
 * Represents a line segment defined by two points.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.1 (01/03/25)
 * @inv the points must be different
 * @see https://www.youtube.com/watch?v=bbTqI0oqL5U
 */
public class Segment {
    private Point p1;
    private Point p2;

    /**
     * Constructor for a line segment
     * @param p1 starting point of the segment
     * @param p2 ending point of the segment
     * @throws IllegalArgumentException if the points are the same
     */
    public Segment(Point p1, Point p2) {
        if (p1.x() == p2.x() && p1.y() == p2.y()) {
            System.out.println("Segment:vi");
            throw new IllegalArgumentException();
        }

        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Checks if two line segments intersect
     * @param s segment to be checked
     * @return true if the segments intersect, false otherwise
     * @see https://www.youtube.com/watch?v=bbTqI0oqL5U
     */
    public boolean intersects(Segment s) {
        int o1 = GeometryUtils.orientation(this.p1, this.p2, s.p1);
        int o2 = GeometryUtils.orientation(this.p1, this.p2, s.p2);
        int o3 = GeometryUtils.orientation(s.p1, s.p2, this.p1);
        int o4 = GeometryUtils.orientation(s.p1, s.p2, this.p2);

        if (o1 == 0 && GeometryUtils.onSegment(this.p1, s.p1, this.p2)) return false;
        if (o2 == 0 && GeometryUtils.onSegment(this.p1, s.p2, this.p2)) return false;
        if (o3 == 0 && GeometryUtils.onSegment(s.p1, this.p1, s.p2)) return false;
        if (o4 == 0 && GeometryUtils.onSegment(s.p1, this.p2, s.p2)) return false;

        if (o1 != o2 && o3 != o4) return true;

        return false;
    }

    /**
     * Checks if the line segment intersects a circle
     * @param c circle to be checked
     * @return true if the line segment intersects the circle, false otherwise
     * @see https://www.youtube.com/watch?v=X0an_UEgE7Y for a detailed explanation of the logic used.
     */
    public boolean intersects(Circle c) {
        double x1 = p1.x();
        double y1 = p1.y();

        double x2 = p2.x();
        double y2 = p2.y();

        double cx = c.center().x();
        double cy = c.center().y();

        double r = c.radius();

        double dx = x2 - x1;
        double dy = y2 - y1;

        double t = ((cx - x1) * dx + (cy - y1) * dy) / (dx * dx + dy * dy);

        if (t < 0) t = 0;
        else if (t > 1) t = 1;

        double px = x1 + t * dx;
        double py = y1 + t * dy;

        double dq = (cx - px) * (cx - px) + (cy - py) * (cy - py);
        double rq = r * r;

        return dq < rq;
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    public Point p1() {
        return p1;
    }

    public Point p2() {
        return p2;
    }
}
