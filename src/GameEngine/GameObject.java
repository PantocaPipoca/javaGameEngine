package GameEngine;

import Figuras.FiguraGeometrica;

public class GameObject implements IGameObject {
    private String name;
    private ITransform transform;
    private ICollider collider;
    
    public GameObject(String name, ITransform transform, FiguraGeometrica figura) {
        this.name = name;
        this.transform = transform;
        this.collider = new Collider(figura, transform);
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