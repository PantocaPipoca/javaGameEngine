package GameEngine;

import Figuras.Ponto;

/**
 * Classe que representa um transformer de um objeto.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (23/03/25)
 **/
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
    
    /**
     * Move o objeto de acordo com o vetor dPos e a camada dlayer
     * @param dPos vetor de deslocamento
     * @param dlayer deslocamento de camada
     */
    public void move(Ponto dPos, int dlayer) {
        pos = pos.translate(dPos.x(), dPos.y());
        layer += dlayer;
    }
    
    /**
     * Rotaciona o objeto de acordo com dTheta
     * @param dTheta angulo de rotacao
     */
    public void rotate(double dTheta) {
        angle = (angle + dTheta) % 360;
        if (angle < 0) angle += 360;
    }
    
    /**
     * Escala o objeto de acordo com dScale
     * @param dScale escala
     */
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
