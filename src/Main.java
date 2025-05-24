
import java.util.List;

import Game.Game;
import Game.Loaders.GameConfigLoader;
import Game.Loaders.ConfigModels.LevelConfig;
import GameEngine.GameEngine;
import GameEngine.GUI;

public class Main {
    public static void main(String[] args) throws Exception {
        GameEngine.getInstance(new GUI());
        List<LevelConfig> levels = GameConfigLoader.load("src/Game/Loaders/Game.json");
        Game game = Game.getInstance(levels);
        game.start();
    }
}
