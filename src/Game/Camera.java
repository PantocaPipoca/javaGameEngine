package Game;

import GameEngine.*;
import Figures.Point;
import java.util.List;

public class Camera implements IBehaviour {
    private static Camera instance;

    private IGameObject go;
    private ITransform target;
    private Point position = new Point(0, 0);
    private GUI gui;

    private Camera(GUI gui) {
        this.gui = gui;
    }

    public static Camera getInstance(GUI gui) {
        if (instance == null) {
            instance = new Camera(gui);
        }
        return instance;
    }
    public static Camera getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Camera instance not initialized. Use getInstance(GUI gui) first.");
        }
        return instance;
    }

    public void setTarget(ITransform target) {
        this.target = target;
    }

    public Point getPosition() {
        return position;
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update();
        if (target == null) return;

        double px = target.position().x();
        double py = target.position().y();

        double cx = gui.getWidth() * 0.5;
        double cy = gui.getHeight() * 0.5;

        java.awt.Point m = ie.getMousePosition();
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

    @Override
    public IGameObject gameObject() {
        return go;
    }

    @Override
    public void gameObject(IGameObject go) {
        this.go = go;
    }
}