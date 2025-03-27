package GameEngine;

import Figuras.Ponto;

public class Transform implements ITransform {
    private Ponto pos;
    private int layer;
    private double rotation;
    private double scale;
    
    public Transform(Ponto pos, int layer, double rotation, double scale) {
        this.pos = pos;
        this.layer = layer;
        this.rotation = rotation;
        this.scale = scale;
    }
    
    public void move(Ponto dPos, int dlayer) {
        pos = pos.translate(dPos.x(), dPos.y());
        layer += dlayer;
    }
    
    public void rotate(double dTheta) {
        rotation += dTheta;
    }
    
    public void scale(double dScale) {
        scale *= dScale;
    }
    
    public Ponto position() {
        return pos;
    }
    
    public int layer() {
        return layer;
    }
    
    public double angle() {
        return rotation;
    }
    
    public double scale() {
        return scale;
    }

    public String toString() {
        return String.format("%s %d %.2f %.2f", pos.toString(), layer, rotation, scale);
    }
    
}
