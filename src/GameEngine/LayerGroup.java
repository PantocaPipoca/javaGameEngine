package GameEngine;

import java.util.ArrayList;
import java.util.List;

public class LayerGroup {
    private int layer;
    private List<IGameObject> objects;

    public LayerGroup(int layer) {
        this.layer = layer;
        objects = new ArrayList<>();
    }

    public int getLayer() {
        return layer;
    }

    public List<IGameObject> getObjects() {
        return objects;
    }

    public void add(IGameObject go) {
        if (!objects.contains(go)) {
            objects.add(go);
        }
    }

    public void remove(IGameObject go) {
        objects.remove(go);
    }

    public boolean contains(IGameObject go) {
        return objects.contains(go);
    }
}
