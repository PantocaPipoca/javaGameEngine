
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
        GameEngine engine = GameEngine.getInstance(new GUI());
        List<LevelConfig> levels = GameConfigLoader.load("src/Game/Loaders/Game.json");
        List<Room> rooms = new ArrayList<>();
        rooms.add(RoomFactory.make(levels.get(0)));
        Game game = new Game(rooms);
        game.start();
    }

}
