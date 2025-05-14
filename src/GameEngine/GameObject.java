package GameEngine;

import Figures.GeometricFigure;

/**
 * Class that represents a game object.
 * Stores all the information about the object, including its name, transformation, collider and behaviour.
 * @author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version: 1.0 (12/04/25)
 * @inv name != null && name != "" && transform != null && figure != null && behaviour != null
 **/
public class GameObject implements IGameObject {
    private final GeometricFigure originalFigure;
    private String name;
    private ITransform transform;
    private ICollider collider;
    private IBehaviour behaviour;
    private Shape shape;

    
    /**
     * Constructor for a game object
     * @param name name of the object
     * @param transform transformation of the object
     * @param figure geometric figure of the object to be used as a collider when centered
     * @param movement movement of the object
     * @throws IllegalArgumentException if the name, transform, figure or movement is null
     */
    public GameObject(String name, ITransform transform, GeometricFigure figure, IBehaviour behaviour) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (transform == null) {
            throw new IllegalArgumentException("Transform cannot be null.");
        }
        if (figure == null) {
            throw new IllegalArgumentException("Figure cannot be null.");
        }
        if (behaviour == null) {
            throw new IllegalArgumentException("Behaviour cannot be null.");
        }
        this.name = name;
        this.transform = transform;
        this.originalFigure = figure;
        this.collider = figure.colliderInit(transform);
        this.behaviour = behaviour;
        this.shape = new Shape(name);
    }

    /**
     * Updates the game objects collider to follow the transform
     */
    public void update() {
        updateCollider();
    }

    /**
     * Updates the collider of the game object
     */
    public void updateCollider() {
        collider = originalFigure.colliderInit(transform);
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    public String name() {
        return name;
    }

    public ITransform transform() {
        return transform;
    }

    public ICollider collider() {
        return collider;
    }

    public Shape shape() { return shape; }

    public IBehaviour behaviour() {
        return behaviour;
    }
    
    public String toString() {
        return name + "\n" + transform.toString() + "\n" + collider.toString();
    }
}