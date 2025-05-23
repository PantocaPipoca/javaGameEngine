package Game.UI;

import GameEngine.IBehaviour;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

import java.util.List;

/**
 * Comportamento passivo, usado para GameObjects de UI.
 * Não reage a colisões, nem precisa de lógica por frame.
 */
public class UIBehaviour implements IBehaviour {

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
