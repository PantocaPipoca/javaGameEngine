package Game;

import GameEngine.IBehaviour;

public interface IEntity extends IBehaviour {

    public StateMachine getStateMachine();
    public Health getHealthManager();
    
}
