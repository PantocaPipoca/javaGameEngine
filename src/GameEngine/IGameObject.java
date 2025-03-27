package GameEngine;
public interface IGameObject {
    String name();
    ITransform transform();
    ICollider collider();
}