package Game.Entities.Enemies.EnemyStates;

import Game.Entities.State;
import Game.Entities.Player.Player;
import GameEngine.IGameObject;
import GameEngine.InputEvent;


public class AttackState extends State {
    
    public AttackState() {

    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {

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
        if (other.name().startsWith("player")) {
            Player player = (Player) other.behaviour();
            player.getHealthManager().takeDamage(10);
        }
    }

}
