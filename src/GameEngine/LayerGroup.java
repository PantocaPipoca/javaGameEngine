package GameEngine;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class that represents a group of game objects on a specific layer.
 * Used to simplify the management of objects and collisions in the game.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (12/04/25)
 */
public class LayerGroup {
    private int layer;
    private final CopyOnWriteArrayList<IGameObject> objects = new CopyOnWriteArrayList<>();

    /////////////////////////////////////////////////// Constructors ///////////////////////////////////////////////////

    /**
     * Constructor for the LayerGroup.
     * @param layer layer of the group
     */
    public LayerGroup(int layer) {
        this.layer = layer;
    }

    /////////////////////////////////////////////////// Logic ///////////////////////////////////////////////////

    /**
     * Adds an object to the group.
     * @param go object to be added
     */
    public void add(IGameObject go) {
        if (!objects.contains(go)) {
            objects.add(go);
        }
    }

    /**
     * Removes an object from the group.
     * @param go object to be removed
     */
    public void remove(IGameObject go) {
        objects.remove(go);
    }

    /**
     * Checks if the group contains an object.
     * @param go object to be checked
     * @return true if the group contains the object, false otherwise
     */
    public boolean contains(IGameObject go) {
        return objects.contains(go);
    }

    /////////////////////////////////////////////////// Getters ///////////////////////////////////////////////////

    /**
     * Returns the layer of the group.
     * @return layer of the group
     */
    public int layer() {
        return layer;
    }

    /**
     * Returns the list of objects in the group.
     * @return list of objects in the group
     */
    public List<IGameObject> objects() {
        return objects;
    }

    /////////////////////////////////////////////////// Setters ///////////////////////////////////////////////////

    /**
     * Sets the layer of the group.
     * @param layer the layer to set
     */
    public void layer(int layer) {
        this.layer = layer;
    }
}