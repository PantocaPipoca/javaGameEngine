package Game.Entities.Commons;

import GameEngine.Animator;
import GameEngine.IBehaviour;
import GameEngine.IGameObject;
import Game.Gun.Weapon;

import java.util.List;

import Figures.Point;

/**
 * Interface for all entity types that can have state, health, and weapons.
 * Provides methods for managing state machines, health, weapons, animation, and collision resolution.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public interface IEntity extends IBehaviour {

    /**
     * Gets the state machine of the entity.
     * @return the state machine
     */
    StateMachine getStateMachine();

    /**
     * Gets the health manager of the entity.
     * @return the health manager
     */
    Health getHealthManager();

    /**
     * Gets the currently equipped gun.
     * @return the current gun
     */
    Weapon getCurrentGun();

    /**
     * Equips a gun by its index in the inventory.
     * @param index the index of the gun to equip
     */
    void equipGun(int index);

    /**
     * Equips the specified gun if it is in the inventory.
     * @param gun the weapon to equip
     */
    void equipGun(Weapon gun);

    /**
     * Gets the list of guns in the entity's inventory.
     * @return the list of guns
     */
    List<Weapon> getGuns();

    /**
     * Gets the animator for the entity.
     * @return the animator
     */
    Animator getAnimator();

    /**
     * Resolves collision against a wall by moving the entity back to the last safe position.
     * @param go the wall game object to resolve against
     */
    void resolveAgainst(IGameObject go);

    /**
     * Sets the last safe position of the entity.
     * @param p the last safe position
     */
    void lastSafePos(Point p);

    /**
     * Hides the current gun by destroying its game object.
     */
    void hideCurrentGun();
}