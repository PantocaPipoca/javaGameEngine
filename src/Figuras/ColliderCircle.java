package Figuras;
import java.text.DecimalFormat;

public class ColliderCircle implements ICollider {
    private Ponto centroid;
    private double radius;
    
    public ColliderCircle(Ponto centroid, double radius) {
        this.centroid = centroid;
        this.radius = radius;
    }
    
    public Ponto centroid() {
        return centroid;
    }
    
    public double radius() {
        return radius;
    }
    
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return centroid.toString() + " " + df.format(radius);
    }
    
}
