package Figuras;
/**
 * Classe principal que apenas le os dados de entrada e cria os objetos necessarios neste caso recebe os dados para verificar se um segmento de reta intersseta um circulo
 * @author Daniel Pantyukhov a83896
 * @version 1.0 (01/03/25)
 * @inv O retangulo deve ser formado por 4 pontos que formam angulo de 90 graus
 * @see https://www.dcs.gla.ac.uk/~pat/52233/slides/Geometry1x1.pdf
 **/
public class Retangulo extends Poligono {
    
    private static final double ERR = 1e-9;

    /**
     * Construtor para um retangulo
     * @param s string no formato "x1 y1 x2 y2 x3 y3 x4 y4"
     */
    public Retangulo(String s) {
        super("4 " + s);

        if(pontos.length != 4) {
            System.out.println("Retangulo:vi");
            throw new IllegalArgumentException();
        }

        if (!canBeARectangle(pontos[0], pontos[1], pontos[2], pontos[3])) {
            System.out.println("Retangulo:vi");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Representacao textual do retangulo
     * @return string com os pontos do retangulo
     */
    public String toString() {
        return "Retangulo: [(" + pontos[0].x() + "," + pontos[0].y() + "), (" + pontos[1].x() + "," + pontos[1].y() + "), (" + pontos[2].x() + "," + pontos[2].y() + "), (" + pontos[3].x() + "," + pontos[3].y() + ")]";
    }

    /**
     * Verifica a possibilidade de formar um retangulo com os pontos dados
     * @param p1 ponto 1
     * @param p2 ponto 2
     * @param p3 ponto 3
     * @param p4 ponto 4
     * @return true se for possivel formar um retangulo com os pontos dados e false caso contrario
     * @see https://stackoverflow.com/questions/2303278/find-if-4-points-on-a-plane-form-a-rectangle
     */
    public static boolean canBeARectangle(Ponto p1, Ponto p2, Ponto p3, Ponto p4) {
        double cx = (p1.x() + p2.x() + p3.x() + p4.x()) / 4.0;
        double cy = (p1.y() + p2.y() + p3.y() + p4.y()) / 4.0;

        double d1 = Math.sqrt(Math.pow(cx - p1.x(), 2) + Math.pow(cy - p1.y(), 2));
        double d2 = Math.sqrt(Math.pow(cx - p2.x(), 2) + Math.pow(cy - p2.y(), 2));
        double d3 = Math.sqrt(Math.pow(cx - p3.x(), 2) + Math.pow(cy - p3.y(), 2));
        double d4 = Math.sqrt(Math.pow(cx - p4.x(), 2) + Math.pow(cy - p4.y(), 2));

        return Math.abs(d1 - d2) < ERR && Math.abs(d1 - d3) < ERR && Math.abs(d1 - d4) < ERR;
    }

    /**
     * Verifica se um segmento de reta intersecta o retangulo, faz isso vendo se algum dos lados e intersectado pela reta
     * @param s segmento de reta a ser verificado
     * @return true se o segmento intersecta o retangulo e false caso contrario
     */
    public boolean intersects(Segmento s) {

        Segmento[] lados = new Segmento[4];
        lados[0] = new Segmento(pontos[0], pontos[1]);
        lados[1] = new Segmento(pontos[1], pontos[2]);
        lados[2] = new Segmento(pontos[2], pontos[3]);
        lados[3] = new Segmento(pontos[3], pontos[0]);

        for(int i = 0; i < 4; i++) {
            if(lados[i].intersects(s)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Desloca o retangulo em dx unidades no eixo x e dy unidades no eixo y
     * @param dx unidades a serem deslocadas no eixo x
     * @param dy unidades a serem deslocadas no eixo y
     * @return novo retangulo deslocado
     */
    public Retangulo translate(int dx, int dy) {
        Ponto[] newPontos = translatePontos(dx, dy);
        return new Retangulo(super.pontosToString(newPontos));
    }

}
