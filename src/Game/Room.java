package Game;

import GameEngine.GameObject;
import Figures.Point;

import java.util.List;

public class Room {
    List<Enemy> enemies;
    List<GameObject> figures;
    List<Point> spawnPoints;
    Player player;

    public Room(List<Enemy> enemies, List<GameObject> figures, List<Point> spawnPoints, GameObject player) {
        this.enemies = enemies;
        this.figures = figures;
        this.spawnPoints = spawnPoints;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
    public List<GameObject> getFigures() {
        return figures;
    }
    public List<Point> getSpawnPoints() {
        return spawnPoints;
    }
    public Player getPlayer() {
        return player;
    }
}
