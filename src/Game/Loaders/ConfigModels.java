package Game.Loaders;

import Figures.Point;
import java.util.List;
import java.util.Map;

public class ConfigModels {
    public static class PlayerConfig {
        public Point pos; public int layer;
        public double angle, scale;
        public int health; public double speed, roll;
        public List<WeaponBlueprint> playerWeapons;
    }
    public static class EnemyBlueprint {
        public String type; public int health;
        public double patrol, chase;
        public Map<String,Object> drops;
        public double detectionRadius, attackRadius, forgetfulRadius;
    }
    public static class EnemySpawn {
        public Point spawn; public List<Point> patrols;
    }
    public static class LevelConfig {
        public String name; public String diff;
        public PlayerConfig player;
        public List<EnemyBlueprint> blueprints;
        public List<EnemySpawn> spawns;
        public int count;
        public Map<String,Double> chances;
        public List<WeaponBlueprint> globalWeapons;
    }
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
}