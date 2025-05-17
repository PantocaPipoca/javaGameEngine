package GameEngine;

import Figures.GeometricFigure;

/**
 * Class that represents a game object.
 * Stores all the information about the object, including its name, transformation, collider and behaviour.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (12/04/25)
 * @inv name != null && name != "" && transform != null && figure != null && behaviour != null
 */
public class GameObject implements IGameObject {
    private final GeometricFigure originalFigure;
    private String name;
    private ITransform transform;
    private ICollider collider;
    private IBehaviour behaviour;
    private Shape shape;
    private boolean flip = false;
    private boolean usaAngle = false;

    /**
     * Constructor for a game object.
     * @param name name of the object
     * @param transform transformation of the object
     * @param figure geometric figure of the object to be used as a collider when centered
     * @param behaviour behaviour of the object
     * @throws IllegalArgumentException if the name, transform, figure or behaviour is null
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
        this.shape = ShapeFactory.createShape(name, (int) transform.scale());
    }

    /**
     * Updates the game object's collider to follow the transform.
     */
    public void update() {
        updateCollider();
    }

    /**
     * Updates the collider of the game object.
     */
    public void updateCollider() {
        collider = originalFigure.colliderInit(transform);
    }

    /////////////////////////////////////////////////// Getters and Setters ///////////////////////////////////////////////////

    /**
     * Gets the name of the game object.
     * @return the name
     */
    public String name() {
        return name;
    }

    /**
     * Gets the transform of the game object.
     * @return the transform
     */
    public ITransform transform() {
        return transform;
    }

    /**
     * Gets the collider of the game object.
     * @return the collider
     */
    public ICollider collider() {
        return collider;
    }

    /**
     * Gets the shape of the game object.
     * @return the shape
     */
    public Shape shape() {
        return shape;
    }

    /**
     * Sets the shape of the game object.
     * @param shape the shape to set
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Gets the behaviour of the game object.
     * @return the behaviour
     */
    public IBehaviour behaviour() {
        return behaviour;
    }

    /**
     * Checks if the object is flipped horizontally.
     * @return true if flipped, false otherwise
     */
    public boolean isFlipped() {
        return flip;
    }

    /**
     * Sets the flipped state of the object.
     * @param f true to flip, false otherwise
     */
    public void setFlip(boolean f) {
        flip = f;
    }

    /**
     * Checks if the object uses angle for rendering.
     * @return true if uses angle, false otherwise
     */
    public boolean usaAngle() {
        return usaAngle;
    }

    /**
     * Sets whether the object uses angle for rendering.
     * @param usaAngle true to use angle, false otherwise
     */
    public void setUsaAngle(boolean usaAngle) {
        this.usaAngle = usaAngle;
    }

    /**
     * Returns a string representation of the game object.
     * @return string with name, transform, and collider info
     */
    @Override
    public String toString() {
        return name + "\n" + transform.toString() + "\n" + collider.toString();
    }
}