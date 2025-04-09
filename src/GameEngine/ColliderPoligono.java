package GameEngine;

import Figuras.*;

/**
 * Classe que representa um collider de um objeto.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (23/03/25)
 **/
public class ColliderPoligono implements ICollider{
    
    private Poligono poligonoCollider;
    public ColliderPoligono(Poligono p, ITransform t) {
        
        // Cria uma copia do preset da figura do collider para reuso futuro (Tambem pq e imutavel ne)
        Poligono preset = (Poligono) p.clone();

        // Pega o centroide do preset para sabermos o ponto partida
        Ponto presetPos = preset.centroide();

        // Aplica as transformacoes no preset
        preset = (Poligono) preset.scale(t.scale());
        // Move o preset de maneira a que o centroide fique na posicao do centroide do transform
        preset = preset.translate(t.position().x() - presetPos.x(), t.position().y() - presetPos.y());
        preset = (Poligono) preset.rotate(t.angle(), t.position());

        // Temos uma copia do nosso preset de acordo com o modelo gui
        poligonoCollider = preset;

    }

    /**
     * Verifica se um circulo colide com uma figura geometrica
     * @param f figura geometrica a ser verificada
     * @return true se colidem, false caso contrario
     */
    @Override
    public boolean colide(ICollider other) {
        return other.colideComPoligono(this);
    }

    /**
     * Verifica se um poligono colide com um circulo
     * @param c circulo a ser verificado
     * @return true se colidem, false caso contrario
     */
    @Override
    public boolean colideComCirculo(ColliderCirculo cc) {
        Ponto[] pontos = poligonoCollider.getPontos();
        // Verifica se o centro do círculo está dentro do polígono
        if (pontoEstaDentroDoPoligono(cc.getFigura().centroide(), poligonoCollider))
            return true;
        
        // Para cada segmento do polígono, verifica se interseta o círculo
        for (Segmento s : poligonoCollider.getSegmentos()) {
            if (s.interseta(cc.getFigura()))
                return true;
        }
        
        // Verifica se algum vértice do polígono está dentro do círculo
        for (Ponto p : pontos) {
            double dx = cc.getFigura().centroide().x() - p.x();
            double dy = cc.getFigura().centroide().y() - p.y();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < cc.getFigura().raio())
                return true;
        }
        return false;
    }

    /**
     * Verifica se um poligono colide com outro poligono
     * @param cp poligono a ser verificado
     * @return true se colidem, false caso contrario
     */
    @Override
    public boolean colideComPoligono(ColliderPoligono cp) {
        Segmento[] segmentos1 = this.poligonoCollider.getSegmentos();
        Segmento[] segmentos2 = cp.poligonoCollider.getSegmentos();
        Ponto[] pontos1 = this.poligonoCollider.getPontos();
        Ponto[] pontos2 = cp.poligonoCollider.getPontos();
        for (Segmento s1 : segmentos1) {
            for (Segmento s2 : segmentos2) {
                if (s1.intersects(s2))
                    return true;
            }
        }
        if (pontoEstaDentroDoPoligono(pontos1[0], cp.getFigura()))
            return true;
        if (pontoEstaDentroDoPoligono(pontos2[0], poligonoCollider))
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
        Ponto[] pontos = poly.getPontos();
        int n = pontos.length;

        for (int i = 0; i < n; i++) {
            Ponto a = pontos[i];
            Ponto b = pontos[(i + 1) % n];
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
        double m = (b.y() - a.y()) / (b.x() - a.x());
        double xi = a.x() + (p.y() - a.y()) / m;
        return p.x() <= xi;
    }

    /**
     * Devolve o centroide do collider
     * @return centroide do collider
     */
    public Ponto centroid() {
        return poligonoCollider.centroide();
    }

    /**
     * Passa a informacao de colisao para uma string
     * @return string com a informacao de colisao
     */
    public String toString() {
        return poligonoCollider.toString();
    }

    /**
     * Devolve o poligono do collider
     * @return poligono do collider
     */
    public Poligono getFigura() {
        return poligonoCollider;
    }

}
