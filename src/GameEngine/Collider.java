package GameEngine;

import Figuras.FiguraGeometrica;
import Figuras.Ponto;

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

    public Ponto centroid() {
        return figuraDoCollider.centroide();
    }

    public String toString() {
        return figuraDoCollider.toString();
    }

}
