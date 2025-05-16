package Game.Entities.Player;

import java.util.List;

import java.util.ArrayList;

import Game.Entities.Health;
import Game.Entities.IEntity;
import Game.Entities.StateMachine;
import Game.Entities.Player.PlayerStates.*;
import Game.Gun.Weapon;
import GameEngine.GameEngine;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public class Player implements IEntity {
    private final Health healthManager;
    private final StateMachine stateMachine;
    private float score;
    private IGameObject go;
    private List<Weapon> guns;
    private Weapon currentGun;

    public Player(Health health, double movingSpeed, double rollingSpeed) {
        this.healthManager = health;
        this.score = 0;
        this.stateMachine = new StateMachine();
        this.guns = new ArrayList<>();

        stateMachine.addState("Idle", new IdleState());
        stateMachine.addState("Moving", new MovingState(movingSpeed));
        stateMachine.addState("Rolling", new RollingState());
        stateMachine.addState("Stunned", new StunnedState());
        stateMachine.addState("Dead", new DeadState());

        stateMachine.setDefaultState("Idle");
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        go.update(); // Updates position and colliders essencially
        stateMachine.onUpdate(dT, ie); // Updates what the player does

    }

    public void addGun(Weapon gun) {
        if (gun != null) {
            guns.add(gun);
            currentGun = gun;
        }
    }

    public void setCurrentGun(Weapon gun) {
        if (gun != null) {
            
            this.currentGun = gun;

            if(gun.gameObject() != null) {
                GameEngine.getInstance().destroy(gun.gameObject());
            }

            GameEngine.getInstance().addEnabled(gun.gameObject());
        }
    }

    public void equipGun(int index) {
        if (index >= 0 && index < guns.size()) {
            currentGun = guns.get(index);
            setCurrentGun(currentGun);
        }
    }

    @Override
    public void onCollision(List<IGameObject> gol) {
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

    public void addScore(float score) {
        this.score += score;
    }
    public float getScore() {
        return score;
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
    public List<Weapon> getGuns() {
        return guns;
    }
    public Weapon getCurrentGun() {
        return currentGun;
    }
}
