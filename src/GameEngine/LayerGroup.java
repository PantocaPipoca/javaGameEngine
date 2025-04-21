package GameEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a collider for an object.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.0 (12/04/25)
 **/
public class LayerGroup {
    private int layer;
    private List<IGameObject> objects;

    /**
     * Constructor for the LayerGroup
     * @param layer layer of the group
     */
    public LayerGroup(int layer) {
        this.layer = layer;
        objects = new ArrayList<>();
    }

    /**
     * Returns the layer of the group
     * @return layer of the group
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Returns the list of objects in the group
     * @return list of objects in the group
     */
    public List<IGameObject> getObjects() {
        return objects;
    }

    /**
     * Adds an object to the group
     * @param go object to be added
     */
    public void add(IGameObject go) {
        if (!objects.contains(go)) {
            objects.add(go);
        }
    }

    /**
     * Removes an object from the group
     * @param go object to be removed
     */
    public void remove(IGameObject go) {
        objects.remove(go);
    }

    /**
     * Checks if the group contains an object
     * @param go object to be checked
     * @return true if the group contains the object, false otherwise
     */
    public boolean contains(IGameObject go) {
        return objects.contains(go);
    }
}
