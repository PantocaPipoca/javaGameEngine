package GameEngine;

import Figuras.FiguraGeometrica;
import Figuras.Ponto;

public interface ICollider {
    Ponto centroid();
    public String toString();
    public boolean colideComCirculo(ColliderCirculo cc);
    public boolean colideComPoligono(ColliderPoligono cp);
    public boolean colide(ICollider other);
    public FiguraGeometrica getFigura();
}