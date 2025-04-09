package GameEngine;

/**
 * Classe que representa um collider de um objeto.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (23/03/25)
 **/
public class Movement {
    private double dx, dy;
    private int dLayer;
    private double dAngle, dScale;

    /**
     * Construtor para o movimento
     * @param dx quantidade de movimento no eixo x
     * @param dy quantidade de movimento no eixo y
     * @param dLayer quantidade de movimento na camada
     * @param dAngle angulo de movimento
     * @param dScale escala de movimento
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
