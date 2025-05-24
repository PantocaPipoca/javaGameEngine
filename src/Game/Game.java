package Game;

import java.util.ArrayList;
import java.util.List;

import Game.Audio.SoundPlayer;
import Game.Entities.Enemies.Enemy;
import Game.Loaders.ConfigModels.LevelConfig;
import Game.Loaders.RoomFactory;
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
    private final List<LevelConfig> levelConfigs;
    private Room currentRoom; // The currently loaded room
    private final Camera camera; // The main camera
    private final GameEngine engine; // The game engine instance
    private int currentRoomIndex; // Index of the current room
    private double currentEnemyCount = 0;
    private float previousScore;

    /**
     * Constructs the Game with a list of level configs.
     * @param levelConfigs the list of level configs
     * @throws IllegalArgumentException if levelConfigs is null or empty
     */
    private Game(List<LevelConfig> levelConfigs) {
        if (levelConfigs == null || levelConfigs.isEmpty()) {
            throw new IllegalArgumentException("Game: levelConfigs must not be null or empty");
        }
        this.levelConfigs = levelConfigs;
        engine = GameEngine.getInstance();
        camera = Camera.getInstance(engine.getGui());
        previousScore = 0;
    }

    ////////////////////// Core Methods //////////////////////

    /**
     * Loads a room by its index, clearing previous objects and setting up all entities and camera.
     * @param roomIndex the index of the room to load
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void loadRoom(int roomIndex) {
        if (roomIndex < 0 || roomIndex >= levelConfigs.size()) {
            System.out.println("You Win");
            return;
        }
        if (currentRoom != null && currentRoom.player() != null) {
            previousScore = currentRoom.player().getScore();
        }
        // Clear out any old unnecessary objects
        List<IGameObject> objectsToDestroy = new ArrayList<>(engine.gameObjects());
        for (IGameObject go : objectsToDestroy) {
            engine.destroy(go);
        }

        currentEnemyCount = 0; // Always reset enemy count

        // Build a fresh Room from config
        currentRoom = RoomFactory.make(levelConfigs.get(roomIndex));
        currentRoomIndex = roomIndex;

        if(roomIndex > 0) {
            SoundPlayer.stopBackgroundMusic();
            SoundPlayer.playSound("songs/newlevel.wav");
        }
        SoundPlayer.stopBackgroundMusic();
        String musicPath;
        switch (roomIndex) {
            case 0:
                musicPath = "songs/level1.wav";
                break;
            case 1:
                musicPath = "songs/level2.wav";
                break;
            default:
                musicPath = "songs/default.wav";
                break;
        }
        SoundPlayer.playBackgroundMusic(musicPath);

        GameUI ui = GameUI.getInstance();
        ui.initUI(engine, camera.position());

        // Load Enemies
        for (Enemy enemy : currentRoom.enemies()) {
            engine.addEnabled(enemy.gameObject());
            currentEnemyCount++;
        }

        // Load Player
        engine.addEnabled(currentRoom.player().gameObject());
        currentRoom.player().setScore(previousScore);

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
     * @param levelConfigs the list of level configurations to initialize the game
     * @return the singleton Game instance
     */
    public static Game getInstance(List<LevelConfig> levelConfigs) {
        if (instance == null) {
            instance = new Game(levelConfigs);
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