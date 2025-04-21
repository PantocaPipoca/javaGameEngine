package GameEngine;

import Figures.Point;

/**
 * Class that represents a transformer for an object.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.0 (23/03/25)
 **/
public class Transform implements ITransform {
    private Point pos;
    private int layer;
    private double angle;
    private double scale;
    
    public Transform(Point pos, int layer, double angle, double scale) {
        this.pos = pos;
        this.layer = layer;
        this.angle = angle;
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
     * Rotates the object according to dTheta
     * @param dTheta rotation angle
     */
    public void rotate(double dTheta) {
        angle = (angle + dTheta) % 360;
        if (angle < 0) angle += 360;
    }
    
    /**
     * Scales the object according to dScale
     * @param dScale scale factor
     */
    public void scale(double dScale) {
        scale += dScale;
        if (scale < 0) scale = 0;
    }
    
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
