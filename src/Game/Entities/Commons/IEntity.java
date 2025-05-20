package Game.Entities.Commons;

import GameEngine.Animator;
import GameEngine.IBehaviour;
import GameEngine.IGameObject;
import Game.Gun.Weapon;

import java.util.List;

import Figures.Point;

/**
 * Interface for all entity types that can have state, health, and weapons.
 */
public interface IEntity extends IBehaviour {

    StateMachine getStateMachine();
    Health getHealthManager();
    Weapon getCurrentGun();
    void equipGun(int index);
    List<Weapon> getGuns();
    Animator getAnimator();
    void resolveAgainst(IGameObject go);
    void lastSafePos(Point p);
}