package Figuras;
/** 
 * Classe que representa um poligono
 * @author Daniel Pantyukhov a83896
 * @version 1.0 (18/03/25)
 * @inv O poligono deve ter no minimo 3 vertices
 *      Tres pontos consecutivos nao podem ser colineares
 *      Nenhma aresta pode intersectar outra
 * **/
public class Poligono extends FiguraGeometrica {
    
    protected Ponto[] pontos;
    protected Segmento[] segmentos;

    /**
     * Construtor para um poligono
     * @param s string no formato "n x1 y1 x2 y2 ... xn yn"
     */
    public Poligono(String s) {
        String[] tokens = s.split(" ");
        int n = Integer.parseInt(tokens[0]);

        if(n < 3) {
            System.out.println("Poligono:vi");
            throw new IllegalArgumentException();
        }
        if(tokens.length != 2 * n + 1) {
            System.out.println("Poligono:vi");
            throw new IllegalArgumentException();
        }

        pontos = new Ponto[n];
        segmentos = new Segmento[n];
        try {
            for(int i = 0; i < n; i++) {
                pontos[i] = new Ponto(Double.parseDouble(tokens[2 * i + 1]), Double.parseDouble(tokens[2 * i + 2]));
            }
        } catch (NumberFormatException e) {
            System.out.println("Poligono:vi");
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < n; i++) {
            segmentos[i] = new Segmento(pontos[i], pontos[(i + 1) % n]);
        }

        // Verifica se 3 pontos consecutivos sao colineares.
        // Usa GeometriaUtils.orientation: se retorna 0, os pontos sao colineares.
        for (int i = 0; i < n; i++) {
            Ponto a = pontos[i];
            Ponto b = pontos[(i + 1) % n];
            Ponto c = pontos[(i + 2) % n];
            if (GeometriaUtils.orientation(a, b, c) == 0) {
                System.out.println("Poligono:vi");
                throw new IllegalArgumentException();
            }                      
        }

        // Verifica se nenhuma aresta intersecta outra.
        for (int i = 0; i < n; i++) {
            Segmento s1 = segmentos[i];
            for (int j = i + 1; j < n; j++) {
                Segmento s2 = segmentos[j];
                if (s1.intersects(s2)) {
                    System.out.println("Poligono:vi");
                    throw new IllegalArgumentException();
                }
            }
        }

    }

    /**
     * Desloca todos os pontos do poligono em dx unidades no eixo x e dy unidades no eixo y
     * @param dx unidades a serem deslocadas no eixo x
     * @param dy unidades a serem deslocadas no eixo y
     * @return um array de todos os novos pontos com as posicoes deslocadas
     */
    protected Ponto[] translatePontos(double dx, double dy) {
        Ponto[] newPontos = new Ponto[pontos.length];
        for (int i = 0; i < pontos.length; i++) {
            newPontos[i] = pontos[i].translate(dx, dy);
        }
        return newPontos;
    }

    /**
     * Transforma um array de pontos em uma string
     * @param pontos array de pontos
     * @return string com os pontos no formato "x1 y1 x2 y2 ... xn yn"
     */
    protected String pontosToString(Ponto[] pontos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pontos.length; i++) {
            sb.append(pontos[i].x()).append(" ").append(pontos[i].y());
            if (i < pontos.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * Desloca o poligono em dx unidades no eixo x e dy unidades no eixo y
     * @param dx unidades a serem deslocadas no eixo x
     * @param dy unidades a serem deslocadas no eixo y
     * @return novo poligono deslocado
     */
    @Override
    public Poligono translate(double dx, double dy) {
        Ponto[] newPontos = translatePontos(dx, dy);
        return new Poligono(newPontos.length + " " + pontosToString(newPontos));
    }

    /**
     * Representacao cli do poligono
     * @return String do polinomio no formato "Poligono de n vertices: [(x1,y1), (x2,y2), ..., (xn,yn)]"
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Poligono de " + pontos.length + " vertices: [");
        for (int i = 0; i < pontos.length; i++) {
            sb.append("(" + pontos[i].x() + "," + pontos[i].y() + ")");
            if (i < pontos.length - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Verifica se um circulo colide com uma figura geometrica
     * @param f figura geometrica a ser verificada
     * @return true se colidem, false caso contrario
     */
    @Override
    public boolean colide(FiguraGeometrica f) {
        return f.colideComPoligono(this);
    }

    /**
     * Verifica se um poligono colide com um circulo
     * @param c circulo a ser verificado
     * @return true se colidem, false caso contrario
     */
    @Override
    public boolean colideComCirculo(Circulo c) {
        return c.colideComPoligono(this);
    }

    /**
     * Verifica se um poligono colide com outro poligono
     * @param p poligono a ser verificado
     * @return true se colidem, false caso contrario
     */
    @Override
    public boolean colideComPoligono(Poligono p) {
        for (Segmento s1 : this.segmentos) {
            for (Segmento s2 : p.segmentos) {
                if (s1.intersects(s2))
                    return true;
            }
        }
        if (pontoEstaDentroDoPoligono(this.pontos[0], p))
            return true;
        if (pontoEstaDentroDoPoligono(p.pontos[0], this))
            return true;
        return false;
    }

    /**
     * Verifica se um ponto esta dentro de um poligono
     * @param point ponto a ser verificado
     * @param poly poligono a ser verificado
     * @return true se o ponto esta dentro do poligono, false caso contrario
     */
    public static boolean pontoEstaDentroDoPoligono(Ponto point, Poligono poly) {
        int count = 0;
        int n = poly.pontos.length;
        for (int i = 0; i < n; i++) {
            Ponto a = poly.pontos[i];
            Ponto b = poly.pontos[(i + 1) % n];
            if (rayInterssetaSegmento(point, a, b)) {
                count++;
            }
        }
        return (count % 2 == 1);
    }
    
    /**
     * Verifica se um ponto interseta um segmento de reta
     * @param p ponto a ser verificado
     * @param a ponto inicial do segmento
     * @param b ponto final do segmento
     * @return true se o ponto interseta o segmento, false caso contrario
     */
    public static boolean rayInterssetaSegmento(Ponto p, Ponto a, Ponto b) {
        if(a.y() > b.y()){
            Ponto temp = a;
            a = b;
            b = temp;
        }
        if(p.y() < a.y() || p.y() > b.y()){
            return false;
        }
        if(a.x() >= p.x() && b.x() >= p.x()){
            return true;
        }
        if(a.x() < p.x() && b.x() < p.x()){
            return false;
        }
        double m = (double) (b.y() - a.y()) / (b.x() - a.x());
        double xi = a.x() + (p.y() - a.y()) / m;
        return p.x() <= xi;
    }
}
