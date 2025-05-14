package Game.Entities.Enemies.EnemyStates;

import Game.Entities.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class EnemyDeadState extends State {
    
    public EnemyDeadState() {
        
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {

    }

    @Override
    public void onEnter() {
        super.onEnter();

    }

    @Override
    public void onExit() {

    }


    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);

    }

}
