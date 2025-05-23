package Game.Obstacles;

import GameEngine.IGameObject;
import GameEngine.IBehaviour;
import GameEngine.InputEvent;
import Game.Game;

import java.util.List;

/**
 * Behaviour for Door in the game.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv The camera must be initialized with a GUI before use.
 */
public class Door implements IBehaviour {
    private IGameObject go;

    ////////////////////// IBehaviour Methods //////////////////////

    @Override
    public void onInit() {}
    @Override
    public void onEnabled() {}
    @Override
    public void onDisabled() {}
    @Override
    public void onDestroy() {}
    @Override
    public void onUpdate(double dT, InputEvent ie) {}

    @Override
    public void onCollision(List<IGameObject> others) {
        for (IGameObject other : others) {
            if (other.name().equals("player") && Game.getInstance().currentEnemyCount() <= 0) {
                Game game = Game.getInstance();
                game.loadRoom(game.currentRoomIndex() + 1);
            }
        }
    }

    ////////////////////// Getters //////////////////////

    public IGameObject gameObject() {
        return go;
    }

    ////////////////////// Setters //////////////////////

    public void gameObject(IGameObject go) {
        this.go = go;
    }

}