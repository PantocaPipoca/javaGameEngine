package Game;

import GameEngine.*;
import Figures.Point;
import java.util.List;

/**
 * Represents the main camera in the game, following a target and providing view offset based on mouse position.
 * Handles camera movement, singleton access, and target following logic.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv The camera must be initialized with a GUI before use.
 */
public class Camera implements IBehaviour {

    private static Camera instance;
    private IGameObject go;
    private ITransform target;
    private Point position = new Point(0, 0);
    private GUI gui;

    /**
     * Private constructor to enforce singleton pattern.
     * @param gui the GUI instance (must not be null)
     */
    private Camera(GUI gui) {
        this.gui = gui;
    }

    ////////////////////// IBehaviour Methods //////////////////////

    /**
     * Updates the camera position based on the target and mouse offset.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update();
        if (target == null) return;

        double px = target.position().x();
        double py = target.position().y();

        double cx = gui.getWidth() * 0.5;
        double cy = gui.getHeight() * 0.5;

        java.awt.Point m = ie.mousePosition();
        double mx = m.x;
        double my = m.y;

        double offsetX = mx - cx;
        double offsetY = my - cy;

        double camX = px + offsetX / 3.0;
        double camY = py + offsetY / 3.0;

        position = new Point(camX, camY);
    }

    @Override public void onInit() {}
    @Override public void onEnabled() {}
    @Override public void onDisabled() {}
    @Override public void onDestroy() {}
    @Override public void onCollision(List<IGameObject> gol) {}

    ////////////////////// Getters //////////////////////

    /**
     * Gets the singleton instance of the camera, initializing it if necessary.
     * @param gui the GUI instance (must not be null)
     * @return the camera instance
     */
    public static Camera getInstance(GUI gui) {
        if (instance == null) {
            instance = new Camera(gui);
        }
        return instance;
    }

    /**
     * Gets the singleton instance of the camera.
     * @return the camera instance
     * @throws IllegalStateException if the camera has not been initialized with a GUI
     */
    public static Camera getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Camera instance not initialized. Use getInstance(GUI gui) first.");
        }
        return instance;
    }

    /**
     * Gets the current position of the camera.
     * @return the camera position
     */
    public Point position() {
        return position;
    }

    /**
     * Gets the camera's game object.
     * @return the game object
     */
    @Override
    public IGameObject gameObject() {
        return go;
    }

    ////////////////////// Setters //////////////////////

    /**
     * Sets the transform that the camera should follow.
     * @param target the target transform
     */
    public void target(ITransform target) {
        this.target = target;
    }

    /**
     * Sets the camera's game object.
     * @param go the game object
     */
    @Override
    public void gameObject(IGameObject go) {
        this.go = go;
    }
}