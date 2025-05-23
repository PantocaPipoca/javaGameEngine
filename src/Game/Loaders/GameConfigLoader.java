package Game.Loaders;

import Game.Loaders.ConfigModels.*;
import Figures.Point;
import java.util.*;

/**
 * Loads game configuration from a JSON file and builds level configuration objects.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class GameConfigLoader {

    ////////////////////// Core Methods //////////////////////

    /**
     * Loads all levels from the given JSON file path.
     * @param path the path to the JSON file
     * @return a list of LevelConfig objects
     * @throws RuntimeException if the file or JSON is invalid
     */
    @SuppressWarnings("unchecked")
    public static List<LevelConfig> load(String path) {
        // Parse entire JSON
        Map<String,Object> root = (Map<String,Object>) JsonParser.parseFile(path);

        // Build global caches
        Map<String,EnemyBlueprint> enemyCache   = new HashMap<>();
        Map<String,WeaponBlueprint> weaponCache = new HashMap<>();

        // Weapon blueprints (global)
        for (Object wbObj : (List<Object>) root.get("weaponBlueprints")) {
            Map<String,Object> m = (Map<String,Object>) wbObj;
            String type = (String) m.get("type");

            WeaponBlueprint wp = new WeaponBlueprint();
            wp.type        = type;
            wp.damage      = ((Number) m.get("damage")).doubleValue();
            wp.fireRate    = ((Number) m.get("fireRate")).doubleValue();
            wp.distanceFromOwner = ((Number) m.get("distanceFromOwner")).doubleValue();

            // Only set if present
            if (m.containsKey("bulletSpeed"))   wp.bulletSpeed   = ((Number) m.get("bulletSpeed")).doubleValue();
            if (m.containsKey("reloadTime"))    wp.reloadTime    = ((Number) m.get("reloadTime")).doubleValue();
            if (m.containsKey("magazineSize"))  wp.magazineSize  = ((Number) m.get("magazineSize")).intValue();
            if (m.containsKey("maxAmmo"))       wp.maxAmmo       = ((Number) m.get("maxAmmo")).intValue();
            if (m.containsKey("blastRadius"))   wp.blastRadius   = ((Number) m.get("blastRadius")).doubleValue();

            weaponCache.put(type, wp);
        }

        // Enemy blueprints (global)
        for (Object ebObj : (List<Object>) root.get("enemyBlueprints")) {
            Map<String,Object> m = (Map<String,Object>) ebObj;
            String type = (String) m.get("type");

            EnemyBlueprint bp = new EnemyBlueprint();
            bp.type            = type;
            bp.enemyWeapons    = new ArrayList<>();
            for (String weaponType : (List<String>) m.get("weapons")) {
                WeaponBlueprint wp = weaponCache.get(weaponType);
                if (wp == null) throw new RuntimeException("Missing weapon blueprint: " + weaponType);
                bp.enemyWeapons.add(wp);
            }
            bp.health          = ((Number) m.get("maxHealth")).intValue();
            bp.patrol          = ((Number) m.get("patrolSpeed")).doubleValue();
            bp.chase           = ((Number) m.get("chaseSpeed")).doubleValue();
            bp.detectionRadius = ((Number) m.get("detectionRadius")).doubleValue();
            bp.attackRadius    = ((Number) m.get("attackRadius")).doubleValue();
            bp.forgetfulRadius = ((Number) m.get("forgetfullRadius")).doubleValue();
            if (m.containsKey("outOfRangeRadius")) {
                bp.outOfRangeRadius = ((Number) m.get("outOfRangeRadius")).doubleValue();
            } else {
                bp.outOfRangeRadius = 0;
            }
            // bp.drops is not used

            enemyCache.put(type, bp);
        }

        // Load each level by referencing the caches
        List<LevelConfig> levels = new ArrayList<>();
        for (Object lvlObj : (List<Object>) root.get("levels")) {
            Map<String,Object> l = (Map<String,Object>) lvlObj;
            LevelConfig L = new LevelConfig();

            // Basic info
            L.name = (String) l.get("levelName");
            L.diff = (String) l.get("difficulty");

            // Figures
            L.figures = new ArrayList<>();
            List<Object> figures = (List<Object>) l.get("figures");
            if (figures != null) {
                for (Object fo : figures) {
                    Map<String,Object> m = (Map<String,Object>) fo;
                    FigureBlueprint fb = new FigureBlueprint();
                    fb.type = (String) m.get("type");

                    if ("polygon".equals(fb.type)) {
                        fb.vertices = (List<List<Number>>) m.get("vertices");
                    } else if ("circle".equals(fb.type)) {
                        fb.center = (List<Number>) m.get("center");
                        fb.radius = ((Number) m.get("radius")).doubleValue();
                    } else {
                        throw new RuntimeException("Unknown figure type: " + fb.type);
                    }
                    fb.layer = ((Number) m.get("layer")).intValue();
                    fb.objectType = (String) m.get("objectType");
                    L.figures.add(fb);
                }
            }

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
            L.player.playerWeapons = new ArrayList<>();

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
            L.globalWeapons = new ArrayList<>();
            for (String type : (List<String>) l.get("globalWeapons")) {
                WeaponBlueprint wp = weaponCache.get(type);
                if (wp == null) throw new RuntimeException("Missing weapon blueprint: " + type);
                L.globalWeapons.add(wp);
            }

            // Player weapons
            List<String> playerWeaponTypes = (List<String>) p.get("playerWeapons");
            for (String type : playerWeaponTypes) {
                WeaponBlueprint wp = weaponCache.get(type);
                if (wp == null) throw new RuntimeException("Missing weapon blueprint: " + type);
                if (!((List<String>) l.get("globalWeapons")).contains(type)) {
                    System.err.println("Warning: Player weapon " + type + " is not in globalWeapons for level " + L.name);
                }
                L.player.playerWeapons.add(wp);
            }

            levels.add(L);
        }

        return levels;
    }
}