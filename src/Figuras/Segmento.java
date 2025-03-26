package Figuras;
/**
 * Representa um segmento de reta definido por dois pontos.
 * @author Daniel Pantyukhov a83896
 * @version 1.1 (01/03/25)
 * @inv os pontos devem ser diferentes
 * @see https://www.youtube.com/watch?v=bbTqI0oqL5U
 */
public class Segmento {
    private Ponto p1;
    private Ponto p2;

    /**
     * Construtor para um segmento de reta
     * @param p1 ponto inicial do segmento
     * @param p2 ponto final do segmento
     */
    public Segmento(Ponto p1, Ponto p2) {
        if (p1.x() == p2.x() && p1.y() == p2.y()) {
            System.out.println("Segmento:vi");
            throw new IllegalArgumentException();
        }

        this.p1 = p1;
        this.p2 = p2;
    }
    

    /**
     * Verifica se dois segmentos de reta se intersectam
     * @param s segmento a ser verificado
     * @return true se os segmentos se intersectam e false caso contrario
     * @see https://www.youtube.com/watch?v=bbTqI0oqL5U
     */
    public boolean intersects(Segmento s) {
        int o1 = GeometriaUtils.orientation(this.p1, this.p2, s.p1);
        int o2 = GeometriaUtils.orientation(this.p1, this.p2, s.p2);
        int o3 = GeometriaUtils.orientation(s.p1, s.p2, this.p1);
        int o4 = GeometriaUtils.orientation(s.p1, s.p2, this.p2);

        if (o1 == 0 && GeometriaUtils.onSegment(this.p1, s.p1, this.p2)) return false;
        if (o2 == 0 && GeometriaUtils.onSegment(this.p1, s.p2, this.p2)) return false;
        if (o3 == 0 && GeometriaUtils.onSegment(s.p1, this.p1, s.p2)) return false;
        if (o4 == 0 && GeometriaUtils.onSegment(s.p1, this.p2, s.p2)) return false;
    
        if (o1 != o2 && o3 != o4) return true;
        
        return false;
    }

    /**
     * Verifica se o segmento de reta interseta um circulo
     * @param c circulo a ser verificado
     * @return true se o segmento de reta interseta o circulo, false caso contrario
     * @see https://www.youtube.com/watch?v=X0an_UEgE7Y para uma explicacao detalhada da logica utilizada.
     */
    public boolean interseta(Circulo c) {
        double x1 = p1.x();
        double y1 = p1.y();

        double x2 = p2.x();
        double y2 = p2.y();

        double cx = c.centro().x();
        double cy = c.centro().y();

        double r = c.raio();
        
        double dx = x2 - x1;
        double dy = y2 - y1;

        double t = ((cx - x1) * dx + (cy - y1) * dy) / (dx * dx + dy * dy);

        if (t < 0) t = 0;
        else if (t > 1) t = 1;

        double px = x1 + t * dx;
        double py = y1 + t * dy;

        double dq = (cx - px) * (cx - px) + (cy - py) * (cy - py);
        double rq = r * r;

        return dq <= rq;

    }
    

    public Ponto p1() {
        return p1;
    }

    public Ponto p2() {
        return p2;
    }
    

}
