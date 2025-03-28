package GameEngine;

import Figuras.Ponto;

public class Transform implements ITransform {
    private Ponto pos;
    private int layer;
    private double angle;
    private double scale;
    
    public Transform(Ponto pos, int layer, double angle, double scale) {
        this.pos = pos;
        this.layer = layer;
        this.angle = angle;
        this.scale = scale;
    }
    
    public void move(Ponto dPos, int dlayer) {
        pos = pos.translate(dPos.x(), dPos.y());
        layer += dlayer;
    }
    
    public void rotate(double dTheta) {
        angle = (angle + dTheta) % 360;
        if (angle < 0) angle += 360;
    }
    
    public void scale(double dScale) {
        scale += dScale;
        if (scale < 0) scale = 0;
    }
    
    public Ponto position() {
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
