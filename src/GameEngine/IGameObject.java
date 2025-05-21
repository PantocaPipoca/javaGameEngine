package GameEngine;

/**
 * Interface for game objects in the engine.
 * Provides methods for accessing and modifying object properties, shape, collider, and behavior.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
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
}