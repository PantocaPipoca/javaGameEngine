package GameEngine;

public class GameObject implements IGameObject {
    private String name;
    private ITransform transform;
    private ICollider collider;
    
    public GameObject(String name, ITransform transform, ICollider collider) {
        this.name = name;
        this.transform = transform;
        this.collider = collider;
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