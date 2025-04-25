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
    public void add(IGameObject go) {
        gameObjects.add(go);
        int layer = go.transform().layer();
        LayerGroup group = getOrCreateLayerGroup(layer);
        group.add(go);
    }

    /**
     * Removes an object from the GameEngine
     * @param go object to be removed
     */
    public void destroy(IGameObject go) {
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

    public void run() {
        long lastTime = System.nanoTime();
        InputEvent ie = new InputEvent();

        for (int i = 0; i < 10; i++) {
            long currentTime = System.nanoTime();
            float dt = (currentTime - lastTime) / 1_000_000_000.0f;
            lastTime = currentTime;
            for (IGameObject go : enabled) {
                go.behaviour().onUpdate(dt, ie);
            }
        }
    }

    private List<IGameObject> enabled = new ArrayList<>();
    private List<IGameObject> disabled = new ArrayList<>();

    public void addEnabled(IGameObject go) {
        if (!isDisabled(go) && !isEnabled(go)) {
            enabled.add(go);
            this.add(go);
            go.behaviour().onInit();
            go.behaviour().onEnabled();
        }
    }

    public void addDisabled(IGameObject go) {
        if (!isDisabled(go) && !isEnabled(go)) {
            disabled.add(go);
            this.add(go);
            go.behaviour().onInit();
            go.behaviour().onDisabled();
        }
    }

    public void enable(IGameObject go) {
        if (disabled.contains(go)) {
            disabled.remove(go);
            addEnabled(go);
        }
    }

    public void disable(IGameObject go) {
        if (enabled.contains(go)) {
            enabled.remove(go);
            addDisabled(go);
        }
    }

    public boolean isEnabled(IGameObject go) {
        return enabled.contains(go);
    }

    public boolean isDisabled(IGameObject go) {
        return disabled.contains(go);
    }

    public List<IGameObject> getEnabled() {
        return new ArrayList<>(enabled);
    }

    public List<IGameObject> getDisabled() {
        return new ArrayList<>(disabled);
    }
}

//...GameEngine.java
// no addEnabled precisamos de correr on enabled ou so init? e no disabled? precisamos de verificar se existe noutra lista tambem?
// o mm pro addDisabled
// no run de onde sacamos os deltas e os inputs
// o get Collisions esta no sitio certo ou devia estar no colider?

//...Main.java
// Corro o run ja como esta? Se sim quando adiciono os objetos e como faco a porra das salas?

//...StateMachine.java
// isso esta fixe? Especialmente a parte da inicializacao e sera que preciso de passar como argumento a referencia pro player,
// ou posso fazer tipo super().owner.

//...Tests.java
// Como testar sem inputs?

// Posso usar instance of quando o player e o inimigo colidem?