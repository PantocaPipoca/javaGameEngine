package Game.Entities.Player.PlayerStates;

import Game.Entities.Player.Player;
import Game.Entities.State;
import Game.Gun.Gun;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

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
        if (ie.isMouseButtonPressed(1)) {
            if(owner.getCurrentGun() != null) {
                owner.getCurrentGun().shoot();
            }
        }
        if (ie.isKeyPressed(KeyEvent.VK_1)) {
            owner.equipGun(0);
        }
        if(ie.isKeyPressed(KeyEvent.VK_R)) {
            Gun gun = (Gun) owner.getCurrentGun();
            if(gun != null) {
                gun.reload();
            }
        }

    }

    @Override
    public void onEnter() {
        super.onEnter();
        if (owner instanceof Player player) {
            player.playAnimation("idle");
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public void onCollision(IGameObject other) {
        super.onCollision(other);
        
    }
}
