package Game.Entities.Enemies.EnemyStates;

import Game.Entities.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class KamikazeState extends State {
    
    public KamikazeState() {
        
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {

    }

    @Override
    public void onEnter() {
        super.onEnter();
        // To-do - Spawn a bomb
        // To-do - Destroy itself
    }

    @Override
    public void onExit() {

    }

    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);

    }

}
