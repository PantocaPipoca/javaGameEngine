package GameEngine;

/**
 * Class that represents the movement of an object.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.0 (12/04/25)
 **/
public class Movement {
    private double dx, dy;
    private int dLayer;
    private double dAngle, dScale;

    /**
     * Constructor for the movement
     * @param dx amount of movement on the x-axis
     * @param dy amount of movement on the y-axis
     * @param dLayer amount of movement in the layer
     * @param dAngle angle of movement
     * @param dScale scale of movement
     */
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
