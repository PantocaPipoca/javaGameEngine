package Game.Entities.Enemies.EnemyStates;

import Game.Game;
import Game.Entities.Commons.State;
import Game.Entities.Enemies.Enemy;
import Game.Entities.Player.Player;
import GameEngine.Animator;
import GameEngine.GameEngine;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

/**
 * Class that represents the dead state for an enemy.
 * Handles logic when the enemy is dead (no actions possible).
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class EnemyDeadState extends State {

    private float timer = 0f;
    private final float delayBeforeDestroy = 0.5f;
    private Player player;

    /**
     * Constructs an EnemyDeadState.
     */
    public EnemyDeadState(IGameObject player) {
        this.player = (Player) player.behaviour();
    }

    /////////////////////////////////////////////////// State Methods ///////////////////////////////////////////////////

    /**
     * Called when entering the dead state.
     */
    @Override
    public void onEnter() {
        super.onEnter();

        Enemy e = (Enemy) owner;
        Animator animator = e.getAnimator();
        animator.frameDuration(0.05f);
        e.playAnimation("death");

        // Remove a arma
        if (e.getCurrentGun() != null) {
            GameEngine.getInstance().destroy(e.getCurrentGun().gameObject());
        }
    }

   /**
     * Updates the dead state. No actions are performed while dead.
     * @param dT delta time since last update
     * @param ie the current input event
     */
    @Override
    public void onUpdate(double dT, InputEvent ie) {
        timer += (float) dT;
        if (timer >= delayBeforeDestroy) {
            
            Game.getInstance().currentEnemyCount(Game.getInstance().currentEnemyCount() - 1);
            player.onEnemyKilled(owner.gameObject().name());
            GameEngine.getInstance().destroy(owner.gameObject());
        }
    }

    /**
     * Called when exiting the dead state.
     */
    @Override
    public void onExit() {
    }

    /**
     * Handles collision while dead.
     * @param other the other game object collided with
     */
    @Override
    public void onCollision(IGameObject other) {
    }
}