package Game.Entities.Commons;

import GameEngine.Animator;
import GameEngine.IBehaviour;
import GameEngine.IGameObject;
import Game.Gun.Weapon;
import Figures.Point;

/**
 * Interface for all entity types that can have state, health, and weapons.
 */
public interface IEntity extends IBehaviour {

    StateMachine getStateMachine();
    Health getHealthManager();
    Weapon getCurrentGun();
    void equipGun(int index);
    Animator getAnimator();
    void resolveAgainst(IGameObject go);
    void lastSafePos(Point p);
}