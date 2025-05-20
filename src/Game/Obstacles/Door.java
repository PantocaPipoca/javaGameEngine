package Game.Obstacles;

import GameEngine.IGameObject;
import GameEngine.IBehaviour;
import GameEngine.InputEvent;
import Game.Game;

import java.util.List;

public class Door implements IBehaviour {
    private IGameObject go;

    @Override
    public void gameObject(IGameObject go) { this.go = go; }
    @Override
    public IGameObject gameObject() { return go; }
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
            if (other.name().equals("player") && Game.getInstance().getCurrentEnemyCount() <= 0) {
                Game game = Game.getInstance();
                game.loadRoom(game.getCurrentRoomIndex() + 1);
            }
        }
    }
}