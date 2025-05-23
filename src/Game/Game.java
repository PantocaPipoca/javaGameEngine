package Game;

import java.util.ArrayList;
import java.util.List;

import Game.Entities.Enemies.Enemy;
import Game.UI.GameUI;
import GameEngine.IGameObject;
import GameEngine.GameObject;
import GameEngine.GameEngine;
import GameEngine.Transform;
import Figures.Circle;
import Figures.Point;

/**
 * Main game controller. Handles room management, entity loading, and game loop startup.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv The rooms list must not be null or empty.
 */
public class Game {

    private static Game instance; // Singleton instance of the game
    private final List<Room> rooms; // List of all rooms in the game
    private Room currentRoom; // The currently loaded room
    private final Camera camera; // The main camera
    private final GameEngine engine; // The game engine instance
    private int currentRoomIndex; // Index of the current room
    private double currentEnemyCount = 0;

    /**
     * Constructs the Game with a list of rooms.
     * @param rooms the list of rooms
     * @throws IllegalArgumentException if rooms is null or empty
     */
    private Game(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            throw new IllegalArgumentException("Game: rooms list must not be null or empty");
        }
        this.rooms = rooms;
        engine = GameEngine.getInstance();
        camera = Camera.getInstance(engine.getGui());
    }

    ////////////////////// Core Methods //////////////////////

    /**
     * Loads a room by its index, clearing previous objects and setting up all entities and camera.
     * @param roomIndex the index of the room to load
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void loadRoom(int roomIndex) {
        if (roomIndex < 0 || roomIndex >= rooms.size()) {
            System.out.println("You Win");
            return;
        }

        // Clear out any old unnecessary objects
        List<IGameObject> objectsToDestroy = new ArrayList<>(engine.gameObjects());
        for (IGameObject go : objectsToDestroy) {
            engine.destroy(go);
        }

        // Choose a room to load
        currentRoom = rooms.get(roomIndex);
        currentRoomIndex = roomIndex;

        GameUI ui = GameUI.getInstance();
        ui.initUI(engine, camera.position());

        // Load Enemies
        for (Enemy enemy : currentRoom.enemies()) {
            engine.addEnabled(enemy.gameObject());
            currentEnemyCount++;
        }

        // Load Player
        engine.addEnabled(currentRoom.player().gameObject());

        // Load Camera
        GameObject cameraObject = new GameObject(
            "camera",
            new Transform(new Point(0, 0), 0, 0, 1),
            new Circle("0 0 1"),
            camera
        );
        camera.gameObject(cameraObject);
        camera.target(currentRoom.player().gameObject().transform());
        engine.addEnabled(cameraObject);
        engine.getGui().setCamera(camera);

        // Load Figures
        for (IGameObject figure : currentRoom.figures()) {
            engine.addEnabled(figure);
        }
    }

    /**
     * Starts the game loop, loading the first room.
     */
    public void start() {
        loadRoom(0);
        engine.run();
    }

    ////////////////////// Getters //////////////////////

    /**
     * Returns the singleton instance of Game, creating it if necessary.
     * @param rooms the list of rooms (only used on first call)
     * @return the singleton Game instance
     */
    public static Game getInstance(List<Room> rooms) {
        if (instance == null) {
            instance = new Game(rooms);
        }
        return instance;
    }

    /**
     * Returns the singleton instance of Game.
     * @return the singleton Game instance
     * @throws IllegalStateException if the instance has not been initialized
     */
    public static Game getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Game has not been initialized. Call getInstance(List<Room>) first.");
        }
        return instance;
    }

    /**
     * Returns the index of the currently loaded room.
     * @return the current room index
     */
    public int currentRoomIndex() {
        return currentRoomIndex;
    }

    /**
     * Get current enemy count.
     * @return current enemy count
     */
    public double currentEnemyCount() {
        return currentEnemyCount;
    }

    ////////////////////// Setters //////////////////////

    /**
     * Set current enemy count.
     * @param currentEnemyCount the new enemy count
     */
    public void currentEnemyCount(double currentEnemyCount) {
        this.currentEnemyCount = currentEnemyCount;
    }
}