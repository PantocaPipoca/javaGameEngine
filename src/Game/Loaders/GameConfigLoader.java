package Game.Loaders;

import Game.Loaders.ConfigModels.*;
import Figures.Point;
import java.util.*;

public class GameConfigLoader {
    @SuppressWarnings("unchecked")
    public static List<LevelConfig> load(String path) {
        Map<String,Object> root = (Map<String,Object>) JsonParser.parseFile(path);
        List<Object> lvls = (List<Object>) root.get("levels");
        List<LevelConfig> out = new ArrayList<>();

        for (Object o : lvls) {
            Map<String,Object> l = (Map<String,Object>) o;
            LevelConfig L = new LevelConfig();
            // basic level info
            L.name = (String) l.get("levelName");
            L.diff = (String) l.get("difficulty");

            // player
            Map<String,Object> p = (Map<String,Object>) l.get("player");
            L.player = new PlayerConfig();
            Map<String,Object> sp = (Map<String,Object>) p.get("startPosition");
            L.player.pos = new Point(((Number)sp.get("x")).intValue(), ((Number)sp.get("y")).intValue());
            L.player.layer = ((Number)p.get("layer")).intValue();
            L.player.angle = ((Number)p.get("angle")).doubleValue();
            L.player.scale = ((Number)p.get("scale")).doubleValue();
            L.player.health = ((Number)p.get("maxHealth")).intValue();
            L.player.speed = ((Number)p.get("movingSpeed")).doubleValue();
            L.player.roll = ((Number)p.get("rollingSpeed")).doubleValue();

            // enemyBlueprints
            L.blueprints = new ArrayList<>();
            List<Object> bps = (List<Object>) l.get("enemyBlueprints");
            for (Object eb : bps) {
                Map<String,Object> m = (Map<String,Object>) eb;
                EnemyBlueprint bp = new EnemyBlueprint();
                bp.type = (String) m.get("type");
                bp.health = ((Number) m.get("maxHealth")).intValue();
                bp.patrol = ((Number) m.get("patrolSpeed")).doubleValue();
                bp.chase = ((Number) m.get("chaseSpeed")).doubleValue();
                bp.drops = (Map<String,Object>) m.get("dropChance");
                L.blueprints.add(bp);
            }

            // enemySpawns
            L.spawns = new ArrayList<>();
            List<Object> sps = (List<Object>) l.get("enemySpawns");
            for (Object es : sps) {
                Map<String,Object> m = (Map<String,Object>) es;
                EnemySpawn spn = new EnemySpawn();
                Map<String,Object> spm = (Map<String,Object>) m.get("spawnPoint");
                spn.spawn = new Point(((Number)spm.get("x")).intValue(), ((Number)spm.get("y")).intValue());
                spn.patrols = new ArrayList<>();
                List<Object> pts = (List<Object>) m.get("patrolPoints");
                for (Object pp : pts) {
                    Map<String,Object> pm = (Map<String,Object>) pp;
                    spn.patrols.add(new Point(((Number)pm.get("x")).intValue(), ((Number)pm.get("y")).intValue()));
                }
                L.spawns.add(spn);
            }

            // count and chances
            L.count = ((Number) l.get("enemyCount")).intValue();
            L.chances = new LinkedHashMap<>();
            Map<String,Object> cms = (Map<String,Object>) l.get("enemyChances");
            for (Map.Entry<String,Object> en : cms.entrySet()) {
                L.chances.put(en.getKey(), ((Number)en.getValue()).doubleValue());
            }

            out.add(L);
        }
        return out;
    }
}