package Game;

import Figures.*;
import GameEngine.GameObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class RoomLoader {

    public static Room loadRoom(String path) throws Exception {
        // Leitura do ficheiro JSON
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line.trim());
        }
        reader.close();

        String content = sb.toString();

        // Extrair e criar o player
        String playerBlock = JsonUtils.extractBlock(content, "\"player\"", "\"spawnPoints\"");
        Player player = PlayerLoader.parseFromBlock(playerBlock);

        // Extrair spawn points
        String spawnBlock = JsonUtils.extractBlock(content, "\"spawnPoints\"", "\"enemies\"");
        List<Point> spawnPoints = SpawnPointLoader.parseFromBlock(spawnBlock);

        // Extrair inimigos
        List<Enemy> enemies = EnemyLoader.parseEnemies(content);

        // Criar lista de GameObjects com o próprio player
        List<GameObject> figures = new ArrayList<>();
        figures.add((GameObject) player.gameObject());

        // Criar e devolver a Room
        Room room = new Room(enemies, figures, spawnPoints, (GameObject) player.gameObject());
        return room;
    }
}
