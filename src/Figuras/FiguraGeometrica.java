package Figuras;
/** 
 * Classe abstrata que representa uma figura geometrica.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (18/03/25)
 * @inv Todas as figuras geometricas sao nao imutaveis
 * **/

import GameEngine.ICollider;
import GameEngine.ITransform;

public abstract class FiguraGeometrica {
    
    public abstract FiguraGeometrica translate(double dx, double dy);

    @Override
    public abstract String toString();

    public abstract FiguraGeometrica scale(double factor);
    public abstract FiguraGeometrica rotate(double angle, Ponto centro);

    public abstract ICollider colliderInit(ITransform t);
}