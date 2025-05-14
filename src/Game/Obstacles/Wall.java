package Game.Obstacles;

import java.util.List;

import GameEngine.IGameObject;
import GameEngine.InputEvent;
import GameEngine.IBehaviour;

public class Wall implements IBehaviour {

    private IGameObject owner;

    public void onUpdate(double dT, InputEvent ie) {

    }
    public void onInit() {

    }
    public void onEnabled() {

    }
    public void onDisabled() {

    }
    public void onDestroy() {

    }
    public void onCollision(List<IGameObject> gol) {

    }
    public IGameObject gameObject() {
        return owner;
    }
    public void gameObject(IGameObject go) {
        this.owner = go;
    }
}
