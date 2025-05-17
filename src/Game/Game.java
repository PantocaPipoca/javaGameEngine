package Game;

import java.util.List;

import Game.Entities.Enemies.Enemy;
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
    private final List<Room> rooms; // List of all rooms in the game
    private Room currentRoom; // The currently loaded room
    private final Camera camera; // The main camera
    private final GameEngine engine; // The game engine instance

    /**
     * Constructs the Game with a list of rooms.
     * @param rooms the list of rooms
     * @throws IllegalArgumentException if rooms is null or empty
     */
    public Game(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            throw new IllegalArgumentException("Game: rooms list must not be null or empty");
        }
        this.rooms = rooms;
        engine = GameEngine.getInstance();
        camera = Camera.getInstance(engine.getGui());
    }

    /**
     * Loads a room by its index, clearing previous objects and setting up all entities and camera.
     * @param roomIndex the index of the room to load
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void loadRoom(int roomIndex) {
        // Clear out any old unnecessary objects
        for (IGameObject go : engine.gameObjects()) {
            engine.destroy(go);
        }

        // Choose a room to load
        currentRoom = rooms.get(roomIndex);

        // Load Figures
        for (IGameObject figure : currentRoom.figures()) {
            engine.addEnabled(figure);
        }

        // Load Enemies
        for (Enemy enemy : currentRoom.enemies()) {
            engine.addEnabled(enemy.gameObject());
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
        camera.setTarget(currentRoom.player().gameObject().transform());
        engine.addEnabled(cameraObject);
        engine.getGui().setCamera(camera);
    }

    /**
     * Starts the game loop, loading the first room.
     */
    public void start() {
        loadRoom(0);
        engine.run();
    }

    /**
     * Starts the game in simulation mode for a fixed number of frames.
     * @param frames number of frames to simulate
     */
    public void startSimulate(int frames) {
        loadRoom(0);
        engine.simulateFrames(frames);
    }
}