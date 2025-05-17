package Figures;

/**
 * Class with utility methods for solving geometric problems.
 * Provides methods for vector normalization, orientation checks, and segment intersection.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (12/04/25)
 * @inv All methods are static and stateless.
 */
public class GeometryUtils {

    /**
     * Normalizes a vector represented as a Point.
     * @param p the vector to normalize
     * @return a new Point representing the normalized vector
     */
    public static Point normalize(Point p) {
        double magnitude = Math.sqrt(p.x() * p.x() + p.y() * p.y());
        if (magnitude == 0) {
            return new Point(0, 0); // Return zero vector if magnitude is zero
        }
        return new Point(p.x() / magnitude, p.y() / magnitude);
    }

    /**
     * Checks the orientation of a point r relative to the line segment formed by p1 and p2.
     * @param p starting point of the segment
     * @param q ending point of the segment
     * @param r point to be checked
     * @return 0 if the points are collinear, 1 if r is to the left of the segment, and 2 if r is to the right of the segment
     * @see <a href="https://www.geeksforgeeks.org/orientation-3-ordered-points/">Orientation explanation</a>
     */
    public static int orientation(Point p, Point q, Point r) {
        double val = (q.y() - p.y()) * (r.x() - q.x()) -
            (q.x() - p.x()) * (r.y() - q.y());

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    /**
     * Used when line segments are collinear and we need to check if they overlap.
     * @param p start of a segment
     * @param q end of a segment
     * @param r point to be checked
     * @return true if the point r is on the segment, false otherwise
     */
    public static boolean onSegment(Point p, Point q, Point r) {
        return q.x() >= Math.min(p.x(), r.x()) && q.x() <= Math.max(p.x(), r.x()) &&
               q.y() >= Math.min(p.y(), r.y()) && q.y() <= Math.max(p.y(), r.y());
    }
}