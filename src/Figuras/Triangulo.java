package Figuras;
/** 
 * Classe que representa um triangulo
 * @author Daue representa um poligonoiel Pantyukhov a83896
 * @version 1.0 (18/03/25)
 * @inv Os 3 pontos do triangulo nao podem ser colineares
 * **/
public class Triangulo extends Poligono{

    /**
     * Construtor para um triangulo
     * @param s string no formato "x1 y1 x2 y2 x3 y3"
     */
    public Triangulo(String s) {
        super("3 " + s);

        if(pontos.length != 3) {
            System.out.println("Triangulo:vi");
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return String no formato "Triangulo: [(x1,y1), (x2,y2), (x3,y3)]"
     */
    @Override
    public String toString() {
        return "Triangulo: [(" + pontos[0].x() + "," + pontos[0].y() + "), (" + pontos[1].x() + "," + pontos[1].y() + "), (" + pontos[2].x() + "," + pontos[2].y() + ")]";
    }

    /**
     * Desloca o triangulo mais dx unidades no eixo x e dy unidades no eixo y
     * @param dx unidades a serem deslocadas no eixo x
     * @param dy unidades a serem deslocadas no eixo y
     * @return novo triangulo deslocado
     */
    @Override
    public Triangulo translate(double dx, double dy) {
        Ponto[] newPontos = translatePontos(dx, dy);
        return new Triangulo(super.pontosToString(newPontos));
    }
}
