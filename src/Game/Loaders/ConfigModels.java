package Game.Loaders;

import Figures.Point;
import java.util.List;
import java.util.Map;

/**
 * Contains configuration models for loading game data from JSON or other sources.
 * Each inner class represents a structure for a specific game entity or configuration.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class ConfigModels {

    /**
     * Configuration for the player.
     */
    public static class PlayerConfig {
        public Point pos; // Initial position
        public int layer;
        public double angle, scale;
        public int health;
        public double speed, roll;
        public List<WeaponBlueprint> playerWeapons;
    }

    /**
     * Blueprint for an enemy type.
     */
    public static class EnemyBlueprint {
        public String type;
        public int health;
        public double patrol, chase;
        public Map<String, Object> drops;
        public double detectionRadius, attackRadius, forgetfulRadius;
    }

    /**
     * Enemy spawn configuration.
     */
    public static class EnemySpawn {
        public Point spawn;
        public List<Point> patrols;
    }

    /**
     * Configuration for a game level.
     */
    public static class LevelConfig {
        public String name;
        public String diff;
        public PlayerConfig player;
        public List<EnemyBlueprint> blueprints;
        public List<EnemySpawn> spawns;
        public int count;
        public Map<String, Double> chances;
        public List<WeaponBlueprint> globalWeapons;
        public List<FigureBlueprint> figures;
    }

    /**
     * Blueprint for a weapon.
     */
    public static class WeaponBlueprint {
        public String type;
        public double bulletSpeed;
        public double damage;
        public double fireRate;
        public double reloadTime;
        public int magazineSize;
        public int maxAmmo;
        public double distanceFromOwner;
    }

    /**
     * Blueprint for a static figure (polygon, circle, etc).
     */
    public static class FigureBlueprint {
        public String type;
        public List<List<Number>> vertices;
        public List<Number> center;
        public double radius;
        public int layer;
    }
}