package Game;

import Figures.Point;
import GameEngine.GameObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Lê o ficheiro JSON inteiro
            BufferedReader reader = new BufferedReader(new FileReader("src/Game/level1.json"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line.trim());
            }
            reader.close();

            String content = sb.toString();

            // Extrai blocos
            String playerBlock = JsonUtils.extractObject(content, "\"player\"");
            String spawnBlock = JsonUtils.extractBlock(content, "\"spawnPoints\"", "\"enemies\"");
            String enemiesBlock = JsonUtils.extractBlock(content, "\"enemies\"", "\"camera\"");

            // Testa PlayerLoader
            System.out.println("=== ✅ PLAYER ===");
            Player player = PlayerLoader.parseFromBlock(playerBlock);
            if (player != null) {
                GameObject go = (GameObject) player.gameObject();
                System.out.println("Name: " + go.name());
                System.out.println("Transform: " + go.transform());
                System.out.println("Collider: " + go.collider());
            } else {
                System.out.println("❌ Player é null!");
            }

            // Testa SpawnPointLoader
            System.out.println("\n=== ✅ SPAWN POINTS ===");
            List<Point> spawnPoints = SpawnPointLoader.parseFromBlock(spawnBlock);
            for (Point p : spawnPoints) {
                System.out.println("→ (" + p.x() + ", " + p.y() + ")");
            }

            // Testa EnemyLoader (mock)
            System.out.println("\n=== ✅ ENEMIES ===");
            List<Enemy> enemies = EnemyLoader.parseEnemies(enemiesBlock);
            for (Enemy e : enemies) {
                System.out.println("→ " + e);
            }

        } catch (Exception e) {
            System.err.println("❌ Erro ao testar carregamento:");
            e.printStackTrace();
        }
    }
}
