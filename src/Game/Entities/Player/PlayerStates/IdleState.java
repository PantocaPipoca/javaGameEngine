package Game.Entities.Player.PlayerStates;

import Game.Entities.State;
import GameEngine.IGameObject;
import GameEngine.InputEvent;
import Game.Gun.Gun;

import java.awt.event.KeyEvent;

public class IdleState extends State {

    public IdleState() {
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        // Verificar teclas pressionadas
        if (ie.isKeyPressed(KeyEvent.VK_W) || ie.isKeyPressed(KeyEvent.VK_S) ||
            ie.isKeyPressed(KeyEvent.VK_A) || ie.isKeyPressed(KeyEvent.VK_D)) {
            stateMachine.setState("Moving");
        }
        if (ie.isKeyPressed(KeyEvent.VK_SPACE)) {
            stateMachine.setState("Rolling");
        }
        if (ie.isMouseButtonPressed(1)) {
            if(owner.getCurrentGun() != null) {
                currentGun.shoot();
            }
        }

    }

    public void setGun(Gun gun) {
        currentGun = gun;
    }

    @Override
    public void onEnter() {
        super.onEnter();
    }

    @Override
    public void onExit() {

    }

    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
        
    }
}
