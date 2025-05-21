package GameEngine;

import java.util.List;

/**
 * Interface for game object behaviors.
 * Provides lifecycle methods and collision handling for game entities.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public interface IBehaviour {
    void onUpdate(double dT, InputEvent ie);
    void onInit();
    void onEnabled();
    void onDisabled();
    void onDestroy();
    void onCollision(List<IGameObject> gol);
    IGameObject gameObject();
    void gameObject(IGameObject go);
}