package GameEngine;

import Figures.Point;

/**
 * Class that represents a transformer for an object.
 * Is responsible for moving, rotating and scaling the object.
 * @author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version: 1.0 (23/03/25)
 * @inv pos != null && layer >= 0 && scale >= 0
 **/
public class Transform implements ITransform {
    private Point pos;
    private int layer;
    private double angle;
    private double scale;
    
    /**
     * Constructor for a transform
     * @param pos position of the object
     * @param layer layer of the object
     * @param angle angle of the object
     * @param scale scale of the object
     * @throws IllegalArgumentException if the position is null or the layer is negative
     */
    public Transform(Point pos, int layer, double angle, double scale) {
        if (pos == null || layer < 0) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        if (scale < 0) {
            throw new IllegalArgumentException("Scale cannot be negative.");
        }
        this.pos = pos;
        this.layer = layer;
        this.angle = angle % 360;
        this.scale = scale;
    }
    
    /**
     * Moves the object according to the vector dPos and the layer dLayer
     * @param dPos displacement vector
     * @param dLayer layer displacement
     */
    public void move(Point dPos, int dLayer) {
        pos = pos.translate(dPos.x(), dPos.y());
        layer += dLayer;
    }

    /**
     * Sets the position of the object
     * @param pos position to set
     */
    public void setPosition(Point pos) {
        if (pos == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        this.pos = pos;
    }
    
    /**
     * Rotates the object according to dTheta
     * @param dTheta rotation angle
     */
    public void rotate(double dTheta) {
        setAngle(dTheta + angle);
    }

    /**
     * Sets the angle of the object
     * @param angle angle to set
     */
    public void setAngle(double angle) {
        this.angle = angle % 360;
        if (this.angle < 0) this.angle += 360;
    }
    
    /**
     * Scales the object according to dScale
     * @param dScale scale factor
     */
    public void scale(double dScale) {
        scale += dScale;
        if (scale < 0) scale = 0;
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    public Point position() {
        return pos;
    }
    
    public int layer() {
        return layer;
    }
    
    public double angle() {
        return angle;
    }
    
    public double scale() {
        return scale;
    }

    public String toString() {
        return String.format("%s %d %.2f %.2f", pos.toString(), layer, angle, scale);
    }
}
