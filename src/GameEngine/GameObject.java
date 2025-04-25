package GameEngine;

import Figures.GeometricFigure;
import Figures.Point;

/**
 * Class that represents a game object.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.0 (12/04/25)
 **/
public class GameObject implements IGameObject {
    private final GeometricFigure originalFigure;
    private String name;
    private ITransform transform;
    private ICollider collider;
    private Movement movement;
    private IBehaviour behaviour;
    
    /**
     * Constructor for a game object
     * @param name name of the object
     * @param transform transformation of the object
     * @param figure geometric figure of the object
     * @param movement movement of the object
     */
    public GameObject(String name, ITransform transform, GeometricFigure figure, Movement movement, IBehaviour behaviour) {
        this.movement = movement;
        this.name = name;
        this.transform = transform;
        this.originalFigure = figure;
        this.collider = figure.colliderInit(transform);
        this.behaviour = behaviour;
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

    public Movement movement() {
        return movement;
    }

    public IBehaviour behaviour() {
        return behaviour;
    }
    
    public String toString() {
        return name + "\n" + transform.toString() + "\n" + collider.toString();
    }

    /**
     * Updates the game object
     */
    public void update() {
        System.out.println(transform.position());
        transform.move(new Point(movement.dx(), movement.dy()), movement.dLayer());
        transform.rotate(movement.dAngle());
        transform.scale(movement.dScale());
        updateCollider();
    }

    /**
     * Updates the collider of the game object
     */
    public void updateCollider() {
        collider = originalFigure.colliderInit(transform);
    }

}