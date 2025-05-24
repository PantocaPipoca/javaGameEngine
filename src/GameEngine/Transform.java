package GameEngine;

import Figures.Point;

/**
 * Class that represents a transformer for an object.
 * Responsible for moving, rotating, and scaling the object.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (23/03/25)
 * @inv pos != null && layer >= 0 && scale >= 0
 */
public class Transform implements ITransform {

    private Point pos;
    private int layer;
    private double angle;
    private double scale;

    /**
     * Constructor for a transform, gives the inicial values for it.
     * @param pos position of the object
     * @param layer layer of the object
     * @param angle angle of the object
     * @param scale scale of the object
     * @throws IllegalArgumentException if the position is null, the layer is negative, or the scale is negative
     */
    public Transform(Point pos, int layer, double angle, double scale) {
        if (pos == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        if (layer < 0) {
            throw new IllegalArgumentException("Layer cannot be negative.");
        }
        if (scale < 0) {
            throw new IllegalArgumentException("Scale cannot be negative.");
        }
        this.pos = pos;
        this.layer = layer;
        this.angle = angle % 360;
        if (this.angle < 0) this.angle += 360;
        this.scale = scale;
    }

    /////////////////////////////////////////////////// Transform Methods ///////////////////////////////////////////////////

    /**
     * Moves the object according to the vector dPos and the layer dLayer.
     * @param dPos displacement vector
     * @param dLayer layer displacement
     */
    public void move(Point dPos, int dLayer) {
        pos = pos.translate(dPos.x(), dPos.y());
        layer += dLayer;
    }

    /**
     * Rotates the object by dTheta degrees.
     * @param dTheta rotation angle
     */
    public void rotate(double dTheta) {
        angle(angle + dTheta);
    }

    /**
     * Scales the object by dScale.
     * @param dScale scale factor
     */
    public void scale(double dScale) {
        scale += dScale;
        if (scale < 0) scale = 0;
    }

    /////////////////////////////////////////////////// Getters ///////////////////////////////////////////////////

    /**
     * Gets the position of the object.
     * @return the position
     */
    public Point position() {
        return pos;
    }

    /**
     * Gets the layer of the object.
     * @return the layer
     */
    public int layer() {
        return layer;
    }

    /**
     * Gets the angle of the object.
     * @return the angle in degrees
     */
    public double angle() {
        return angle;
    }

    /**
     * Gets the scale of the object.
     * @return the scale
     */
    public double scale() {
        return scale;
    }

    /**
     * Returns a string representation of the transform.
     * @return string in the format "(x, y) layer angle scale"
     */
    @Override
    public String toString() {
        return String.format("%s %d %.2f %.2f", pos.toString(), layer, angle, scale);
    }

    /////////////////////////////////////////////////// Setters ///////////////////////////////////////////////////

    /**
     * Sets the position of the object.
     * @param pos position to set
     * @throws IllegalArgumentException if the position is null
     */
    public void position(Point pos) {
        if (pos == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        this.pos = pos;
    }

    /**
     * Sets the layer of the object.
     * @param layer layer to set
     */
    public void layer(int layer) {
        if (layer < 0) {
            throw new IllegalArgumentException("Layer cannot be negative.");
        }
        this.layer = layer;
    }

    /**
     * Sets the angle of the object.
     * @param angle angle to set
     */
    public void angle(double angle) {
        this.angle = angle % 360;
        if (this.angle < 0) this.angle += 360;
    }
}