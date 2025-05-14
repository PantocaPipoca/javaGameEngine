package Game.Entities.Player;

import java.util.List;

import Figures.Circle;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Game.Entities.Health;
import Game.Entities.IEntity;
import Game.Entities.StateMachine;
import Game.Entities.Player.PlayerStates.*;
import Game.Gun.Gun;
import Game.Gun.Pistol;
import GameEngine.GameEngine;
import GameEngine.GameObject;
import GameEngine.IGameObject;
import GameEngine.InputEvent;
import GameEngine.Transform;

public class Player implements IEntity {
    private final Health healthManager;
    private final StateMachine stateMachine;
    private float score;
    private IGameObject go;
    private List<Gun> guns;
    private Gun currentGun;

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

        if (ie.isKeyPressed(KeyEvent.VK_1)) {
            setCurrentGun(currentGun);
        }

    }

    public void addGun(Gun gun) {
        if (gun != null) {
            guns.add(gun);
            currentGun = gun;
        }
    }

    public void initializePistol() {
        Gun pistol = new Pistol(go);
        addGun(pistol);
    }

    public void setCurrentGun(Gun gun) {
        if (gun != null) {
            
            this.currentGun = gun;

            // Create the GameObject for the gun
            IGameObject gunObject = new GameObject(
                "gun",
                new Transform(go.transform().position(), go.transform().layer(), 0, 1),
                new Circle("0 0 1"),
                gun
            );

            // Set the gun's GameObject and add it to the engine
            gun.gameObject(gunObject);
            GameEngine.getInstance().addEnabled(gunObject);
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
        this.stateMachine.setOwner(go);
    }
    public List<Gun> getGuns() {
        return guns;
    }
    public Gun getCurrentGun() {
        return currentGun;
    }
}
