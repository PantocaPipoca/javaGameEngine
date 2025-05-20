
import java.util.ArrayList;
import java.util.List;

import Game.Game;
import Game.Room;
import Game.Loaders.GameConfigLoader;
import Game.Loaders.RoomFactory;
import Game.Loaders.ConfigModels.LevelConfig;
import GameEngine.GameEngine;
import GameEngine.GUI;

public class Main {
    
    public static void main(String[] args) throws Exception {
        GameEngine.getInstance(new GUI());
        List<LevelConfig> levels = GameConfigLoader.load("src/Game/Loaders/Game.json");
        List<Room> rooms = new ArrayList<>();
        for (LevelConfig level : levels) {
            rooms.add(RoomFactory.make(level));
        }
        Game game = new Game(rooms);
        game.start();
    }

}
