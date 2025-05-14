package Game;

import java.util.List;

import Game.Entities.Enemies.Enemy;
import GameEngine.IGameObject;
import GameEngine.GameEngine;
import GameEngine.GUI;

public class Game {
    private final List<Room> rooms;
    private Room currentRoom;
    @SuppressWarnings("unused")
    private final Camera camera;
    private final GameEngine engine;

    public Game(List<Room> rooms) {
        this.rooms = rooms;
        engine = GameEngine.getInstance();
        camera = new Camera();
    }

    public void loadRoom(int roomIndex) {

        // Clear out any old unecessary objects
        for (IGameObject go : engine.getGameObjects()) {
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

        // TO-DO camera.setTarget(player.gameObject());
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
