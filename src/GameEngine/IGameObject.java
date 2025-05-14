package GameEngine;

public interface IGameObject {
    String name();
    ITransform transform();
    Shape shape();
    ICollider collider();
    void update();
    IBehaviour behaviour();

}