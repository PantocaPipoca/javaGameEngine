package Game;

import GameEngine.IGameObject;
import Game.Entities.Enemies.Enemy;
import Game.Entities.Player.Player;

import java.util.List;

public class Room {
    private Player player; // Separate field for the player
    private List<Enemy> enemies; // List of enemies
    private List<IGameObject> figures; // List of walls, obstacles, etc.

    public Room(Player player, List<Enemy> enemies, List<IGameObject> figures) {
        this.player = player;
        this.enemies = enemies;
        this.figures = figures;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<IGameObject> getFigures() {
        return figures;
    }
}