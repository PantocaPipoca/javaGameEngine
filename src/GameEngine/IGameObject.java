package GameEngine;

public interface IGameObject {
    String name();
    ITransform transform();
    Shape shape();
    void setShape(Shape shape);
    ICollider collider();
    void update();
    IBehaviour behaviour();
    boolean isFlipped();
    void setFlip(boolean flip);
    boolean usaAngle();
}