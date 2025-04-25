package GameEngine;

import java.util.List;

public interface IBehaviour {

    IGameObject gameObject();
    void gameObject(IGameObject go);
    void onInit();
    void onEnabled();
    void onDisabled();
    void onDestroy();
    void onUpdate(double dT, InputEvent ie);
    void onCollision(List<IGameObject> gol);
    
}
