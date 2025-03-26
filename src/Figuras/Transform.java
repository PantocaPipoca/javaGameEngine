package Figuras;
import java.text.DecimalFormat;

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
        angle += (angle + dTheta) % 360;
        if (angle < 0) angle += 360;
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
        return angle;
    }
    
    public double scale() {
        return scale;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "(" + df.format(pos.x()) + "," + df.format(pos.y()) + ") " + layer + " " + df.format(angle) + " " + df.format(scale);
    }
    
}
