package GameEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents the GameEngine.
 * Responsible for managing game objects, layers, collisions, and the main game loop.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.4 (09/05/25)
 * @inv gui != null
 */
public class GameEngine {
    private static GameEngine instance;

    private List<IGameObject> gameObjects;
    private List<LayerGroup> layerGroups;
    private List<IGameObject> enabled = new ArrayList<>();
    private List<IGameObject> disabled = new ArrayList<>();
    private GUI gui;

    /**
     * Constructor for the GameEngine.
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

    ///////////////////////////// Essential methods /////////////////////////////

    /**
     * Starts the GameEngine, receives input events and updates all game objects.
     * @throws IllegalStateException if the GUI is not initialized
     */
    public void run() {
        long lastTime = System.nanoTime();
        final int targetFPS = 60;
        final long frameDuration = 1_000_000_000 / targetFPS;

        while (true) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastTime;
            float dt = elapsedTime / 1_000_000_000.0f;
            lastTime = currentTime;

            InputEvent ie = gui.ie();
            
            // Check collisions
            checkCollisions();

            // Update enabled objects
            for (IGameObject go : new ArrayList<>(enabled)) {
                int oldLayer = go.transform().layer();
                go.behaviour().onUpdate(dt, ie);
                int newLayer = go.transform().layer();

                // Handle layer changes
                if (oldLayer != newLayer) {
                    updateObjectLayer(go, oldLayer, newLayer);
                }
            }

            

            // Render the game objects
            gui.renderGameObjects(enabled);

            // Calculate the time taken for the frame
            long frameTime = System.nanoTime() - currentTime;

            // Sleep to maintain the target framerate
            if (frameTime < frameDuration) {
                try {
                    Thread.sleep((frameDuration - frameTime) / 1_000_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /**
     * Adds an object to the GameEngine, running onInit and onEnabled.
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
     * Adds an object to the GameEngine, running onInit and onDisabled.
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
     * Enables an object in the GameEngine.
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
     * Disables an object in the GameEngine.
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
     * Removes an object from the GameEngine.
     * @param go object to be removed
     */
    public void destroy(IGameObject go) {
        gameObjects.remove(go);
        LayerGroup group = getLayerGroup(go.transform().layer());
        if (group != null) {
            group.remove(go);
            go.behaviour().onDestroy();
            if (enabled.contains(go)) {
                enabled.remove(go);
            } else if (disabled.contains(go)) {
                disabled.remove(go);
            }
        }
    }

    ///////////////////////////// Internal methods /////////////////////////////

    /**
     * Checks collisions between all objects in the GameEngine using layer groups.
     */
    private void checkCollisions() {
        for (LayerGroup group : layerGroups) {
            checkCollisionsInGroup(group.objects());
        }
    }

    /**
     * Updates the layer of an object.
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
     * Returns the layer group corresponding to a layer.
     * @param layer layer to be checked
     * @return the corresponding layer group
     */
    private LayerGroup getLayerGroup(int layer) {
        for (LayerGroup group : layerGroups) {
            if (group.layer() == layer) {
                return group;
            }
        }
        return null;
    }

    /**
     * Creates a layer group if it doesn't exist.
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
     * Checks collisions between objects in a group.
     * @param group group of objects to be checked
     */
    private void checkCollisionsInGroup(List<IGameObject> group) {
        List<IGameObject> groupCopy = new ArrayList<>(group);
        int size = groupCopy.size();
        for (int i = 0; i < size; i++) {
            IGameObject go1 = groupCopy.get(i);
            for (int j = i + 1; j < size; j++) {
                IGameObject go2 = groupCopy.get(j);
                if (go1.collider().colides(go2.collider())) {
                    // Each object is notified of the other
                    List<IGameObject> go2List = new ArrayList<>();
                    go2List.add(go2);
                    go1.behaviour().onCollision(go2List);

                    List<IGameObject> go1List = new ArrayList<>();
                    go1List.add(go1);
                    go2.behaviour().onCollision(go1List);
                }
            }
        }
    }

    /**
     * Adds an object to the GameEngine.
     * @param go object to be added
     */
    public void add(IGameObject go) {
        gameObjects.add(go);
        int layer = go.transform().layer();
        LayerGroup group = getOrCreateLayerGroup(layer);
        group.add(go);
    }

    ///////////////////////////// Getters and Setters /////////////////////////////

    /**
     * Returns the list of objects in the GameEngine.
     * @return list of objects in the GameEngine
     */
    public List<IGameObject> gameObjects() {
        return gameObjects;
    }

    public boolean isEnabled(IGameObject go) {
        return enabled.contains(go);
    }

    public boolean isDisabled(IGameObject go) {
        return disabled.contains(go);
    }

    public List<IGameObject> enabled() {
        return new ArrayList<>(enabled);
    }

    public List<IGameObject> disabled() {
        return new ArrayList<>(disabled);
    }

    /**
     * Returns the singleton instance of the GameEngine.
     * @param gui GUI to be used (only required for the first call)
     * @return the singleton instance of GameEngine
     */
    public static GameEngine getInstance(GUI gui) {
        if (instance == null) {
            instance = new GameEngine(gui);
        }
        return instance;
    }

    /**
     * Returns the singleton instance of the GameEngine (without GUI).
     * @return the singleton instance of GameEngine
     * @throws IllegalStateException if the instance has not been initialized
     */
    public static GameEngine getInstance() {
        if (instance == null) {
            throw new IllegalStateException("GameEngine has not been initialized. Call getInstance(GUI) first.");
        }
        return instance;
    }

    /**
     * Gets the GUI associated with the GameEngine.
     * @return the GUI
     */
    public GUI getGui() {
        return gui;
    }
}