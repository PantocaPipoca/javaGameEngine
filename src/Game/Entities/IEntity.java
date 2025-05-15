package Game.Entities;

import GameEngine.IBehaviour;
import Game.Gun.Gun;

public interface IEntity extends IBehaviour {

    public StateMachine getStateMachine();
    public Health getHealthManager();
    public Gun getCurrentGun();
    public void equipGun(int index);
    
}
