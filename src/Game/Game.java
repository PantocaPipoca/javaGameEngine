package Game;

import java.util.List;

import GameEngine.GameEngine;
import GameEngine.IGameObject;

public class Game {
    List<Room> rooms;
    Room currentRoom;
    Camera camera;
    GameEngine engine;

    public Game(List<Room> rooms) {
        this.rooms = rooms;
        engine = new GameEngine();
        camera = new Camera();
    }

    public void loadRoom(int roomIndex) {

        // Clear out any old unecessary objects
        for (IGameObject go : engine.getGameObjects()) {
            engine.destroy(go);
        }
        
        // Choose a room to load
        currentRoom = rooms.get(roomIndex);


    }

    public void start() {
        loadRoom(0);
        engine.run();
    }
}
