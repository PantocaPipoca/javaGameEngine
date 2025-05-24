package Game;

import GameEngine.IGameObject;
import Game.Entities.Enemies.Enemy;
import Game.Entities.Player.Player;

import java.util.List;

/**
 * Represents a room in the game, containing the player, enemies, and static figures (walls, obstacles, etc) ready to load.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv The player must not be null.
 *      The enemies and figures lists must not be null (can be empty).
 */
public class Room {

    private final Player player; // The player in this room
    private final List<Enemy> enemies; // List of enemies present in the room
    private final List<IGameObject> figures; // List of static figures (walls, obstacles, etc.)

    /**
     * Constructs a Room with the specified player, enemies, and figures.
     * @param player the player in the room (must not be null)
     * @param enemies the list of enemies (must not be null)
     * @param figures the list of static figures (must not be null)
     * @throws IllegalArgumentException if player, enemies, or figures is null
     */
    public Room(Player player, List<Enemy> enemies, List<IGameObject> figures) {
        if (player == null) {
            throw new IllegalArgumentException("Room: player must not be null");
        }
        if (enemies == null) {
            throw new IllegalArgumentException("Room: enemies list must not be null");
        }
        if (figures == null) {
            throw new IllegalArgumentException("Room: figures list must not be null");
        }
        this.player = player;
        this.enemies = enemies;
        this.figures = figures;
    }

    ////////////////////// Getters //////////////////////

    /**
     * Gets the player in this room.
     * @return the player
     */
    public Player player(){
        return player;
    }

    /**
     * Gets the list of enemies in this room.
     * @return list of enemies
     */
    public List<Enemy> enemies() {
        return enemies;
    }

    /**
     * Gets the list of static figures (walls, obstacles, etc.) in this room.
     * @return list of figures
     */
    public List<IGameObject> figures() {
        return figures;
    }
}