package Game.Obstacles;

import java.util.List;

import GameEngine.IGameObject;
import GameEngine.InputEvent;
import GameEngine.IBehaviour;

/**
 * Behaviour for wall obstacles in the game.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv The camera must be initialized with a GUI before use.
 */
public class Wall implements IBehaviour {

    private IGameObject go;

    ////////////////////// IBehaviour Methods //////////////////////

    public void onUpdate(double dT, InputEvent ie) {}

    public void onInit() {}

    public void onEnabled() {}

    public void onDisabled() {}

    public void onDestroy() {}

    public void onCollision(List<IGameObject> gol) {}

    ////////////////////// Getters //////////////////////

    public IGameObject gameObject() {
        return go;
    }

    ////////////////////// Setters //////////////////////

    public void gameObject(IGameObject go) {
        this.go = go;
    }
}