package GameEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a collider for an object.
 * Author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * Version: 1.0 (12/04/25)
 **/
public class GameEngine {
    private List<IGameObject> gameObjects;
    private List<LayerGroup> layerGroups;

    /**
     * Constructor for the GameEngine
     */
    public GameEngine() {
        gameObjects = new ArrayList<>();
        layerGroups = new ArrayList<>();
    }

    /**
     * Adds an object to the GameEngine
     * @param go object to be added
     */
    public void add(GameObject go) {
        gameObjects.add(go);
        int layer = go.transform().layer();
        LayerGroup group = getOrCreateLayerGroup(layer);
        group.add(go);
    }

    /**
     * Removes an object from the GameEngine
     * @param go object to be removed
     */
    public void destroy(GameObject go) {
        gameObjects.remove(go);
        LayerGroup group = getLayerGroup(go.transform().layer());
        if (group != null) { // This check doesn't matter much
            group.remove(go);
        }
    }

    /**
     * Simulates the GameEngine for a number of frames
     * @param frames number of frames to simulate
     */
    public void simulateFrames(int frames) {
        for (int i = 0; i < frames; i++) {
            for (IGameObject go : gameObjects) {
                int oldLayer = go.transform().layer();
                go.update();
                int newLayer = go.transform().layer();
                if (oldLayer != newLayer) {
                    updateObjectLayer(go, oldLayer, newLayer);
                }
            }
        }
    }

    /**
     * Updates the layer of an object
     * @param go object to be updated
     * @param oldLayer the old layer
     * @param newLayer the new layer
     */
    private void updateObjectLayer(IGameObject go, int oldLayer, int newLayer) {
        LayerGroup oldGroup = getLayerGroup(oldLayer);
        if (oldGroup != null) {
            oldGroup.remove(go);
        }
        LayerGroup newGroup = getOrCreateLayerGroup(newLayer);
        newGroup.add(go);
    }

    /**
     * Returns the layer group corresponding to a layer
     * @param layer layer to be checked
     * @return the corresponding layer group
     */
    private LayerGroup getLayerGroup(int layer) {
        for (LayerGroup group : layerGroups) {
            if (group.getLayer() == layer) {
                return group;
            }
        }
        return null;
    }

    /**
     * Creates a layer group if it doesn't exist
     * @param layer layer to be checked
     * @return the corresponding layer group
     */
    private LayerGroup getOrCreateLayerGroup(int layer) {
        LayerGroup group = getLayerGroup(layer);
        if (group == null) {
            group = new LayerGroup(layer);
            layerGroups.add(group);
        }
        return group;
    }

    /**
     * Checks collisions between objects in the GameEngine
     * @return a list of strings with the collision results
     */
    public List<String> getCollisions() {
        List<String> results = new ArrayList<>();
        for (LayerGroup group : layerGroups) {
            results.addAll(checkCollisionsInGroup(group.getObjects()));
        }
        return results;
    }

    /**
     * Checks collisions between objects in a group
     * @param group group of objects to be checked
     * @return a list of strings with the collision results
     */
    private List<String> checkCollisionsInGroup(List<IGameObject> group) {
        List<String> groupResults = new ArrayList<>();
        int size = group.size();
        for (int i = 0; i < size; i++) {
            IGameObject go1 = group.get(i);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < size; j++) {
                if (i == j) continue;
                IGameObject go2 = group.get(j);
                if (go1.collider().colides(go2.collider())) {
                    sb.append(" ").append(go2.name());
                }
            }
            if (sb.length() > 0) {
                groupResults.add(go1.name() + sb.toString());
            }
        }
        return groupResults;
    }

    /**
     * Returns the list of objects in the GameEngine
     * @return list of objects in the GameEngine
     */
    public List<IGameObject> getGameObjects() {
        return gameObjects;
    }
}
