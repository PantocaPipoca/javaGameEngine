package GameEngine;

import Figuras.Circulo;
import Figuras.Ponto;

/**
 * Classe que representa um collider de um objeto.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (23/03/25)
 **/
public class ColliderCirculo implements ICollider{
    
    private Circulo circuloCollider;
    public ColliderCirculo(Circulo c, ITransform t) {
        
        // Cria uma copia do preset da figura do collider para reuso futuro (Tambem pq e imutavel ne)
        Circulo preset = c.clone();

        // Pega o centroide do preset para sabermos o ponto partida
        Ponto presetPos = preset.centroide();

        // Aplica as transformacoes no preset
        preset = preset.scale(t.scale());
        // Move o preset de maneira a que o centroide fique na posicao do centroide do transform
        preset = preset.translate(t.position().x() - presetPos.x(), t.position().y() - presetPos.y());
        preset = preset.rotate(t.angle(), t.position());

        // Temos uma copia do nosso preset de acordo com o modelo gui
        circuloCollider = preset;

    }

    /**
     * Verifica se um circulo colide com uma figura geometrica
     * @param f figura geometrica a ser verificada
     * @return true se colidem, false caso contrario
     */
    @Override
    public boolean colide(ICollider other) {
        return other.colideComCirculo(this);
    }

    /**
     * Verifica se um circulo colide com um poligono verificando se o circulo esta dentro do poligono ou se algum segmento do poligono interseta o circulo ou se algum ponto do poligono esta dentro do circulo.
     * @param p Poligono a ser verificado
     * @return true se colidem, false caso contrario
     */
     @Override
     public boolean colideComPoligono(ColliderPoligono cp) {
         return cp.colideComCirculo(this);
     }

    /**
     * Verifica se um circulo colide com um outro circulo verificando se a distancia entre os centros dos circulos e menor ou igual a soma dos raios dos circulos.
     * @param c
     * @return true se colidem, false caso contrario
     */
    public boolean colideComCirculo(ColliderCirculo cc) {
        double distance = this.centroid().distancia(cc.centroid());
        double r1 = this.circuloCollider.raio();
        double r2 = cc.circuloCollider.raio();
        return distance <= (r1 + r2 + 1e-9);
    }

    /**
     * Devolve o centroide do collider
     * @return centroide do collider
     */
    public Ponto centroid() {
        return circuloCollider.centroide();
    }

    /**
     * Passa a informacao de colisao para uma string
     * @return string com a informacao de colisao
     */
    public String toString() {
        return circuloCollider.toString();
    }

    public Circulo getFigura() {
        return circuloCollider;
    }
}
