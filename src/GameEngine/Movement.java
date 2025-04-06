package GameEngine;

public class Movement {
    private double dx, dy;
    private int dLayer;
    private double dAngle, dScale;

    public Movement(double dx, double dy, int dLayer, double dAngle, double dScale) {
        this.dx = dx;
        this.dy = dy;
        this.dLayer = dLayer;
        this.dAngle = dAngle;
        this.dScale = dScale;
    }

    public double dx() {
        return dx;
    }

    public double dy() {
        return dy;
    }

    public int dLayer() {
        return dLayer;
    }

    public double dAngle() {
        return dAngle;
    }

    public double dScale() {
        return dScale;
    }
}
