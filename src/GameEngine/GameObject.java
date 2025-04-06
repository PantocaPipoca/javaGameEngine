package GameEngine;

import Figuras.FiguraGeometrica;

/**
 * Classe que representa um objeto de jogo.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (23/03/25)
 **/
public class GameObject implements IGameObject {
    private String name;
    private ITransform transform;
    private ICollider collider;
    
    public GameObject(String name, ITransform transform, FiguraGeometrica figura) {
        this.name = name;
        this.transform = transform;
        this.collider = figura.colliderInit(transform);
    }
    
    public String name() {
        return name;
    }
    
    public ITransform transform() {
        return transform;
    }
    
    public ICollider collider() {
        return collider;
    }
    
    public String toString() {
        return name + "\n" + transform.toString() + "\n" + collider.toString();
    }

}