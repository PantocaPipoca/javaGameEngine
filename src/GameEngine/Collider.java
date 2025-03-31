package GameEngine;

import Figuras.FiguraGeometrica;
import Figuras.Ponto;

/**
 * Classe que representa um collider de um objeto.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (23/03/25)
 **/
public class Collider implements ICollider{
    
    private FiguraGeometrica figuraDoCollider;
    public Collider(FiguraGeometrica f, ITransform t) {
        
        // Cria uma copia do preset da figura do collider para reuso futuro (Tambem pq e imutavel ne)
        FiguraGeometrica preset = f.clone();

        // Pega o centroide do preset para sabermos o ponto partida
        Ponto presetPos = preset.centroide();

        // Aplica as transformacoes no preset
        preset = preset.scale(t.scale());
        // Move o preset de maneira a que o centroide fique na posicao do centroide do transform
        preset = preset.translate(t.position().x() - presetPos.x(), t.position().y() - presetPos.y());
        preset = preset.rotate(t.angle(), t.position());

        // Temos uma copia do nosso preset de acordo com o modelo gui
        figuraDoCollider = preset;

    }

    /**
     * Devolve o centroide do collider
     * @return centroide do collider
     */
    public Ponto centroid() {
        return figuraDoCollider.centroide();
    }

    /**
     * Passa a informacao de colisao para uma string
     * @return string com a informacao de colisao
     */
    public String toString() {
        return figuraDoCollider.toString();
    }

}
