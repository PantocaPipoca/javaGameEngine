package Game.Entities;

import GameEngine.IBehaviour;
import Game.Gun.Weapon;

/**
 * Interface for all entity types that can have state, health, and weapons.
 */
public interface IEntity extends IBehaviour {

    StateMachine getStateMachine();
    Health getHealthManager();
    Weapon getCurrentGun();
    void equipGun(int index);
    // void playAnimation(String name);
}