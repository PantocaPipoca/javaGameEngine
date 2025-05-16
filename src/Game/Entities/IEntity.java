package Game.Entities;

import GameEngine.IBehaviour;
import Game.Gun.Weapon;

public interface IEntity extends IBehaviour {

    public StateMachine getStateMachine();
    public Health getHealthManager();
    public Weapon getCurrentGun();
    public void equipGun(int index);
    
}
