package Game;

import java.util.ArrayList;
import java.util.List;

public class EnemyLoader {

    public static List<Enemy> parseEnemies(String content) {
        List<Enemy> enemies = new ArrayList<>();

        // Apenas exemplo vazio para testar integração
        enemies.add(new Enemy("e1"));
        enemies.add(new Enemy("e2"));

        return enemies;
    }
}