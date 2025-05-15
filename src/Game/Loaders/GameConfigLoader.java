package Game.Loaders;

import Game.Loaders.ConfigModels.*;
import Figures.Point;
import java.util.*;

public class GameConfigLoader {
    @SuppressWarnings("unchecked")
    public static List<LevelConfig> load(String path) {
        // Parse entire JSON
        Map<String,Object> root = (Map<String,Object>) JsonParser.parseFile(path);

        // 1) Build global caches
        Map<String,EnemyBlueprint> enemyCache   = new HashMap<>();
        Map<String,WeaponBlueprint> weaponCache = new HashMap<>();

        // Enemy blueprints (global)
        for (Object ebObj : (List<Object>) root.get("enemyBlueprints")) {
            Map<String,Object> m = (Map<String,Object>) ebObj;
            String type = (String) m.get("type");

            EnemyBlueprint bp = new EnemyBlueprint();
            bp.type            = type;
            bp.health          = ((Number) m.get("maxHealth")).intValue();
            bp.patrol          = ((Number) m.get("patrolSpeed")).doubleValue();
            bp.chase           = ((Number) m.get("chaseSpeed")).doubleValue();
            bp.detectionRadius = ((Number) m.get("detectionRadius")).doubleValue();
            bp.attackRadius    = ((Number) m.get("attackRadius")).doubleValue();
            bp.forgetfulRadius = ((Number) m.get("forgetfullRadius")).doubleValue();
            bp.drops           = (Map<String,Object>) m.get("dropChance");

            enemyCache.put(type, bp);
        }

        // Weapon blueprints (global)
        for (Object wbObj : (List<Object>) root.get("weaponBlueprints")) {
            Map<String,Object> m = (Map<String,Object>) wbObj;
            String type = (String) m.get("type");

            WeaponBlueprint wp = new WeaponBlueprint();
            wp.type        = type;
            wp.bulletSpeed = ((Number) m.get("bulletSpeed")).doubleValue();
            wp.damage      = ((Number) m.get("damage")).doubleValue();
            wp.fireRate    = ((Number) m.get("fireRate")).doubleValue();
            wp.reloadTime  = ((Number) m.get("reloadTime")).doubleValue();
            wp.magazineSize = ((Number) m.get("magazineSize")).intValue();
            wp.maxAmmo     = ((Number) m.get("maxAmmo")).intValue();

            weaponCache.put(type, wp);
        }

        // 2) Load each level by referencing the caches
        List<LevelConfig> levels = new ArrayList<>();
        for (Object lvlObj : (List<Object>) root.get("levels")) {
            Map<String,Object> l = (Map<String,Object>) lvlObj;
            LevelConfig L = new LevelConfig();

            // Basic info
            L.name = (String) l.get("levelName");
            L.diff = (String) l.get("difficulty");

            // Player
            Map<String,Object> p = (Map<String,Object>) l.get("player");
            L.player = new PlayerConfig();
            Map<String,Object> sp = (Map<String,Object>) p.get("startPosition");
            L.player.pos    = new Point(
                ((Number)sp.get("x")).intValue(),
                ((Number)sp.get("y")).intValue()
            );
            L.player.layer  = ((Number)p.get("layer")).intValue();
            L.player.angle  = ((Number)p.get("angle")).doubleValue();
            L.player.scale  = ((Number)p.get("scale")).doubleValue();
            L.player.health = ((Number)p.get("maxHealth")).intValue();
            L.player.speed  = ((Number)p.get("movingSpeed")).doubleValue();
            L.player.roll   = ((Number)p.get("rollingSpeed")).doubleValue();
            L.player.startingWeapon = (String) p.get("startingWeapon");

            // Enemy blueprints for this level
            L.blueprints = new ArrayList<>();
            for (String type : (List<String>) l.get("enemyTypes")) {
                EnemyBlueprint bp = enemyCache.get(type);
                if (bp == null) throw new RuntimeException("Missing enemy blueprint: " + type);
                L.blueprints.add(bp);
            }

            // Enemy spawns
            L.spawns = new ArrayList<>();
            for (Object spObj : (List<Object>) l.get("enemySpawns")) {
                Map<String,Object> m = (Map<String,Object>) spObj;
                EnemySpawn es = new EnemySpawn();
                Map<String,Object> pm = (Map<String,Object>) m.get("spawnPoint");
                es.spawn = new Point(
                    ((Number)pm.get("x")).intValue(),
                    ((Number)pm.get("y")).intValue()
                );
                es.patrols = new ArrayList<>();
                for (Object pt : (List<Object>) m.get("patrolPoints")) {
                    Map<String,Object> pp = (Map<String,Object>) pt;
                    es.patrols.add(new Point(
                        ((Number)pp.get("x")).intValue(),
                        ((Number)pp.get("y")).intValue()
                    ));
                }
                L.spawns.add(es);
            }

            // Enemy count & chances
            L.count = ((Number) l.get("enemyCount")).intValue();
            L.chances = new LinkedHashMap<>();
            for (Map.Entry<String,Object> e : ((Map<String,Object>)l.get("enemyChances")).entrySet()) {
                L.chances.put(e.getKey(), ((Number)e.getValue()).doubleValue());
            }

            // Weapon blueprints for this level
            L.weapons = new ArrayList<>();
            for (String type : (List<String>) l.get("weaponTypes")) {
                WeaponBlueprint wp = weaponCache.get(type);
                if (wp == null) throw new RuntimeException("Missing weapon blueprint: " + type);
                L.weapons.add(wp);
            }

            levels.add(L);
        }

        return levels;
    }
}