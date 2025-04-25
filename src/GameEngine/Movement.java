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

    /**
     * Default constructor for the movement
     */
    public Movement() {
        this.dx = 0;
        this.dy = 0;
        this.dLayer = 0;
        this.dAngle = 0;
        this.dScale = 0;
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

    public void setDx(double dx) {
        this.dx = dx;
    }
    public void setDy(double dy) {
        this.dy = dy;
    }
    public void setDLayer(int dLayer) {
        this.dLayer = dLayer;
    }
    public void setDAngle(double dAngle) {
        this.dAngle = dAngle;
    }
    public void setDScale(double dScale) {
        this.dScale = dScale;
    }
}
