package GameEngine;

import java.util.List;

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
