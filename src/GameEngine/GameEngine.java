package GameEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the GameEngine.
 * It is responsible for managing the game objects, layers and collisions.
 * @author: Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version: 1.4 (09/05/25)
 * @inv gui != null
 **/
public class GameEngine {
    private static GameEngine instance;

    private List<IGameObject> gameObjects;
    private List<LayerGroup> layerGroups;
    private List<IGameObject> enabled = new ArrayList<>();
    private List<IGameObject> disabled = new ArrayList<>();
    private GUI gui;

    /**
     * Constructor for the GameEngine
     * @param gui GUI to be used
     * @throws IllegalArgumentException if the GUI is null
     */
    private GameEngine(GUI gui) {
        if (gui == null) {
            throw new IllegalArgumentException("GUI cannot be null.");
        }
        this.gui = gui;
        gameObjects = new ArrayList<>();
        layerGroups = new ArrayList<>();
    }

    /////////////////////////////Essential methods/////////////////////////////

    /**
     * Starts the GameEngine, receives input events and updates all game objects
     * @throws IllegalStateException if the GUI is not initialized
     */
    public void run() {
        long lastTime = System.nanoTime();
        final int targetFPS = 30; // Target framerate
        final long frameDuration = 1_000_000_000 / targetFPS; // Duration of each frame in nanoseconds
    
        while (true) { // Run indefinitely until stopped
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastTime;
            float dt = elapsedTime / 1_000_000_000.0f; // Delta time in seconds
            lastTime = currentTime;
    
            InputEvent ie = gui.getIe();
    
            // Update enabled objects
            for (IGameObject go : new ArrayList<>(enabled)) { // Avoid concurrent modification
                int oldLayer = go.transform().layer();
                go.behaviour().onUpdate(dt, ie);
                int newLayer = go.transform().layer();
    
                // Handle layer changes
                if (oldLayer != newLayer) {
                    updateObjectLayer(go, oldLayer, newLayer);
                }
            }
    
            // Check collisions
            checkCollisions();
    
            // Render the game objects
            gui.renderGameObjects(enabled);
    
            // Calculate the time taken for the frame
            long frameTime = System.nanoTime() - currentTime;
    
            // Sleep to maintain the target framerate
            if (frameTime < frameDuration) {
                try {
                    Thread.sleep((frameDuration - frameTime) / 1_000_000); // Convert nanoseconds to milliseconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /**
     * Adds an object to the GameEngine consequently running the onInit and onEnabled methods
     * @param go object to be added
     */
    public void addEnabled(IGameObject go) {
        if (isDisabled(go) || isEnabled(go)) return;

        enabled.add(go);
        this.add(go);
        go.behaviour().onInit();
        go.behaviour().onEnabled();
    }

    /**
     * Adds an object to the GameEngine consequently running the onInit and onDisabled methods
     * @param go object to be added
     */
    public void addDisabled(IGameObject go) {
        if (isDisabled(go) || isEnabled(go)) return;

        disabled.add(go);
        this.add(go);
        go.behaviour().onInit();
        go.behaviour().onDisabled();
    }

    /**
     * Enables an object in the GameEngine
     * @param go object to be enabled
     */
    public void enable(IGameObject go) {
        if (disabled.contains(go)) {
            disabled.remove(go);
            addEnabled(go);
            go.behaviour().onEnabled();
        }
    }

    /**
     * Disables an object in the GameEngine
     * @param go object to be disabled
     */
    public void disable(IGameObject go) {
        if (enabled.contains(go)) {
            enabled.remove(go);
            addDisabled(go);
            go.behaviour().onDisabled();
        }
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
            go.behaviour().onDestroy();
            if (enabled.contains(go)) {
                enabled.remove(go);
            }
            else if (disabled.contains(go)) {
                disabled.remove(go);
            }
        }
    }

    ///////////////////////////////////////////////////Internaly used///////////////////////////////////////////////////
    
    /**
     * Checks collisions between all objects in the GameEngine
     * Using the layer groups to optimize the process
     */
    private void checkCollisions() {
        for (LayerGroup group : layerGroups) {
            checkCollisionsInGroup(group.getObjects());
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
     * Checks collisions between objects in a group
     * @param group group of objects to be checked
     * @return a list of strings with the collision results
     */
    private void checkCollisionsInGroup(List<IGameObject> group) {
        int size = group.size();
        for (int i = 0; i < size; i++) {
            IGameObject go1 = group.get(i);
            List<IGameObject> collidingObjects = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (i == j) continue;
                IGameObject go2 = group.get(j);
                if (go1.collider().colides(go2.collider())) {
                    collidingObjects.add(go2);
                }
            }
            if (!collidingObjects.isEmpty()) {
                go1.behaviour().onCollision(collidingObjects);
            }
        }
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

    ///////////////////////////////////////////////////Debug Methods///////////////////////////////////////////////////

    /**
     * Simulates the GameEngine for a number of frames
     * @param frames number of frames to simulate
     */
    public void simulateFrames(int frames) {
        long lastTime = System.nanoTime();
    
        for (int i = 0; i < frames; i++) { // Run indefinitely until stopped
            long currentTime = System.nanoTime();
            float dt = (currentTime - lastTime) / 1_000_000_000.0f;
            lastTime = currentTime;

            InputEvent ie = gui.getIe();
    
            // Update enabled objects
            for (IGameObject go : new ArrayList<>(enabled)) { // Avoid concurrent modification
                int oldLayer = go.transform().layer();
                go.behaviour().onUpdate(dt, ie);
                int newLayer = go.transform().layer();
    
                // Handle layer changes
                if (oldLayer != newLayer) {
                    updateObjectLayer(go, oldLayer, newLayer);
                }
            }
    
            // Check collisions
            checkCollisions();

            gui.renderGameObjects(enabled);
        }
    }

    ///////////////////////////////////////////////////Getters and Setters///////////////////////////////////////////////////

    /**
     * Returns the list of objects in the GameEngine
     * @return list of objects in the GameEngine
     */
    public List<IGameObject> getGameObjects() {
        return gameObjects;
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

    /**
     * Returns the singleton instance of the GameEngine
     * @param gui GUI to be used (only required for the first call)
     * @return the singleton instance of GameEngine
     */
    public static GameEngine getInstance(GUI gui) {
        if (instance == null) {
            instance = new GameEngine(gui); // Create the instance if it doesn't exist
        }
        return instance;
    }

    /**
     * Returns the singleton instance of the GameEngine (without GUI)
     * @return the singleton instance of GameEngine
     * @throws IllegalStateException if the instance has not been initialized
     */
    public static GameEngine getInstance() {
        if (instance == null) {
            throw new IllegalStateException("GameEngine has not been initialized. Call getInstance(GUI) first.");
        }
        return instance;
    }
}