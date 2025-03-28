package GameEngine;

import Figuras.FiguraGeometrica;
import Figuras.Ponto;

public class Collider implements ICollider{
    
    private FiguraGeometrica figuraDoCollider;
    public Collider(FiguraGeometrica f, ITransform t) {
        
        FiguraGeometrica preset = f.clone();

        Ponto presetPos = preset.centroide();
        preset = preset.scale(t.scale());

        preset = preset.translate(t.position().x() - presetPos.x(), t.position().y() - presetPos.y());

        preset = preset.rotate(t.angle(), t.position());

        figuraDoCollider = preset;

    }

    public Ponto centroid() {
        return figuraDoCollider.centroide();
    }

    public String toString() {
        return figuraDoCollider.toString();
    }

}
