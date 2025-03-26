package Figuras;
import java.text.DecimalFormat;

/** 
 * Define o ponto no plano cartesiano e no plano polar.
 * @author Daniel Pantyukhov a83896
 * @version 1.2 (26/03/25)
 * @inv As cooredenadas x e y sao sempre positivas
 *      r deve ser nao negativo e theta deve estar entre 0 e 90 graus.
 * **/
public class Ponto {
    private double r, theta;
    private double x, y;

    /**
    *  Construtor para um ponto recebendo coordenadas cartesianas
    *  @param x coordenada x
    *  @param y coordenada y
    **/
    public Ponto(double x, double y) {

        if (x < 0 || y < 0) {
            System.out.println("Ponto:vi");
            throw new IllegalArgumentException();
        }

        this.x = x;
        this.y = y;
        r = Math.sqrt(x * x + y * y);
        theta = Math.atan2(y, x);
    }

    /**
     * Calcula a distancia entre dois pontos
     * @param that ponto a ser calculado a distancia
     * @return distancia entre os dois pontos
     */
    public double distancia(Ponto that) {
        return Math.sqrt(this.r() * this.r() + that.r() * that.r() - 2 * this.r() * that.r() * Math.cos(that.theta() - this.theta()));
    }

    /**
     * Desloca o ponto mais dx unidades no eixo x e dy unidades no eixo y
     * @param dx unidades a serem deslocadas no eixo x
     * @param dy unidades a serem deslocadas no eixo y
     * @return novo ponto deslocado
     */
    public Ponto translate(double dx, double dy) {
        return new Ponto(this.x() + dx, this.y() + dy);
    }

    /**
     * Representacao cli do ponto
     * @return String do ponto no formato "(x,y)"
     */
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "(" + df.format(x) + "," + df.format(y) + ")";
    }

    /**
     * Getters para os dados dos pontos
     * @return o dado pedido
     */
    public double r() {
        return r;
    }
    public double theta() {
        return theta;
    }
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
}