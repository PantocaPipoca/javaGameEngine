package Figuras;
import GameEngine.ICollider;
import GameEngine.ITransform;

/** 
 * Classe que representa um poligono
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (18/03/25)
 * @inv O poligono deve ter no minimo 3 vertices
 *      Tres pontos consecutivos nao podem ser colineares
 *      Nenhma aresta pode intersectar outra
 * **/
public abstract class FiguraGeometrica {
    
    /**
     * Move a figura geometrica de acordo com o vetor dPos
     * @param dx quantidade de movimento no eixo x
     * @param dy quantidade de movimento no eixo y
     * @return a figura geometrica movida
     */
    public abstract FiguraGeometrica translate(double dx, double dy);

    @Override
    public abstract String toString();

    /**
     * Escala a figura geometrica de acordo com dScale
     * @param factor fator de escala
     * @return a figura geometrica escalada
     */
    public abstract FiguraGeometrica scale(double factor);
    /**
     * Rotaciona a figura geometrica de acordo com dTheta
     * @param angle angulo de rotacao
     * @param centro centro de rotacao
     * @return a figura geometrica rotacionada
     */
    public abstract FiguraGeometrica rotate(double angle, Ponto centro);

    /**
     * Cria um collider para a figura geometrica
     * @param t transformacao da figura
     * @return a figura geometrica com o centro
     */
    public abstract ICollider colliderInit(ITransform t);
}