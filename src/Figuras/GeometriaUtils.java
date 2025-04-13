package Figuras;
/**
 * Classe com metodos uteis para a resolucao de problemas geometricos
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (12/04/25)
 */
public class GeometriaUtils {

    /**
     * Verifica a orientacao dum ponto r em relacao ao segmento de reta formado por p1 e p2
     * @param p ponto inicial do segmento
     * @param q ponto final do segmento
     * @param r ponto a ser verificado
     * @return 0 se os pontos sao colineares, 1 se r esta a esquerda do segmento e 2 se r esta a direita do segmento
     * @see https://www.geeksforgeeks.org/orientation-3-ordered-points/
     */
    public static int orientation(Ponto p, Ponto q, Ponto r) {
        double val = (q.y() - p.y()) * (r.x() - q.x()) - 
            (q.x() - p.x()) * (r.y() - q.y()); 

        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }

    /**
     * Serve para quando os segmentos de reta sao colineares e temos de verificar se estao sobrepostos
     * @param p Inicio dum segmento
     * @param q Fim dum segmento
     * @param r Ponto a ser verificado
     * @return true se o ponto r esta sobre o segmento e false caso contrario
     */
    public static boolean onSegment(Ponto p, Ponto q, Ponto r) {
        return q.x() >= Math.min(p.x(), r.x()) && q.x() <= Math.max(p.x(), r.x()) &&
               q.y() >= Math.min(p.y(), r.y()) && q.y() <= Math.max(p.y(), r.y());
    }

}
