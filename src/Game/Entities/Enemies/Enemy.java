package Game.Entities.Enemies;

import java.util.List;

import Game.Entities.Health;
import Game.Entities.IEntity;
import Game.Entities.StateMachine;
import Game.Gun.Weapon;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public abstract class Enemy implements IEntity {
    
    protected StateMachine stateMachine;
    private final Health healthManager;
    private IGameObject go;
    private List<Weapon> guns;
    private Weapon currentGun;

    public Enemy(Health health) {
        this.healthManager = health;
        this.stateMachine = new StateMachine();

        currentGun = null;
    }

    public void setCurrentGun(Weapon gun) {
        if (gun != null) {
            this.currentGun = gun;
        }
    }

    public void equipGun(int index) {
        if (index >= 0 && index < guns.size()) {
            currentGun = guns.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid gun index: " + index);
        }
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update(); // Updates position and colliders essencially
        stateMachine.onUpdate(dT, ie); // Updates what the player does
    }

    @Override
    public void onCollision(List<IGameObject> gol) {
        for (IGameObject other : gol) {
            stateMachine.onCollision(other);
        }
    }

    @Override
    public void onInit() {
        stateMachine.resetToDefault();
    }

    @Override
    public void onEnabled() {

    }

    @Override
    public void onDisabled() {

    }

    @Override
    public void onDestroy() {

    }

    public Health getHealthManager() {
        return healthManager;
    }
    public StateMachine getStateMachine() {
        return stateMachine;
    }
    @Override
    public IGameObject gameObject() {
        return go;
    }
    @Override
    public void gameObject(IGameObject go) {
        this.go = go;
        this.stateMachine.setOwner((IEntity) go.behaviour());
    }

    public Weapon getCurrentGun() {
        return currentGun;
    }

}