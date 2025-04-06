package GameEngine;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private List<IGameObject> gameObjects;

    public GameEngine() {
        gameObjects = new ArrayList<>();
    }

    public void add(GameObject go) {
        gameObjects.add(go);
    }

    public void destroy(GameObject go) {
        gameObjects.remove(go);
    }
}
