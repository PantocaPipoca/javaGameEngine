package Game;

import java.util.List;

import Game.Entities.Enemies.Enemy;
import GameEngine.IGameObject;
import GameEngine.GameObject;
import GameEngine.GameEngine;
import GameEngine.Transform;
import Figures.Circle;
import Figures.Point;

public class Game {
    private final List<Room> rooms;
    private Room currentRoom;
    private final Camera camera;
    private final GameEngine engine;

    public Game(List<Room> rooms) {
        this.rooms = rooms;
        engine = GameEngine.getInstance();
        camera = Camera.getInstance(engine.getGui());
    }

    public void loadRoom(int roomIndex) {

        // Clear out any old unecessary objects
        for (IGameObject go : engine.gameObjects()) {
            engine.destroy(go);
        }
        
        // Choose a room to load
        currentRoom = rooms.get(roomIndex);

        // Load Figures
        for (IGameObject figure : currentRoom.getFigures()) {
            engine.addEnabled(figure);
        }

        // Load Enemies
        for (Enemy enemy : currentRoom.getEnemies()) {
            engine.addEnabled(enemy.gameObject());
        }

        engine.addEnabled(currentRoom.getPlayer().gameObject());

        // Load Camera
        GameObject cameraObject = new GameObject(
            "camera",
            new Transform(new Point(0, 0), 0, 0, 1),
            new Circle("0 0 1"),
            camera
        );
        camera.gameObject(cameraObject);
        camera.setTarget(currentRoom.getPlayer().gameObject().transform());
        engine.addEnabled(cameraObject);
        engine.getGui().setCamera(camera);
    }

    public void start() {
        loadRoom(0);
        engine.run();
    }

    public void startSimulate(int frames) {
        loadRoom(0);
        engine.simulateFrames(frames);
    }
}
