package Game.Loaders;

import Game.Loaders.ConfigModels.*;
import Game.Entities.Health;
import Game.Entities.Enemies.*;
import Game.Entities.Player.Player;
import Game.Room;
import Figures.Point;
import Figures.Circle;
import Game.Gun.*;
import GameEngine.*;
import java.util.*;

public class RoomFactory {
    public static Room make(LevelConfig lvl) {
        // build player
        PlayerConfig pc = lvl.player;
        Player pl = new Player(new Health(pc.health), pc.speed, pc.roll);
        Transform t = new Transform(pc.pos, pc.layer, pc.angle, pc.scale);
        GameObject go = new GameObject("player", t, new Circle(pc.pos.x()+" "+pc.pos.y()+" 20"), pl);
        pl.gameObject(go);

        // Build pistol
        pl.initializePistol();


        // build enemies
        List<Enemy> enemies = new ArrayList<>();
        Collections.shuffle(lvl.spawns);
        Random rnd = new Random();
        for (int i = 0; i < lvl.count && i < lvl.spawns.size(); i++) {
            EnemySpawn es = lvl.spawns.get(i);
            double r = rnd.nextDouble();
            String type = chooseByChance(r, lvl.chances);
            EnemyBlueprint bp = findBlueprint(type, lvl.blueprints);
            Enemy e = makeEnemy(bp, pl, es.patrols);
            Transform et = new Transform(es.spawn, 1, 0, 1);
            GameObject ego = new GameObject(type+"_"+i, et, new Circle(es.spawn.x()+" "+es.spawn.y()+" 20"), e);
            e.gameObject(ego);
            enemies.add(e);
        }

        return new Room(pl, enemies, Collections.emptyList());
    }

    private static String chooseByChance(double r, Map<String,Double> m) {
        double acc = 0;
        for (Map.Entry<String,Double> e : m.entrySet()) {
            acc += e.getValue();
            if (r <= acc) return e.getKey();
        }
        return m.keySet().iterator().next();
    }
    private static EnemyBlueprint findBlueprint(String t, List<EnemyBlueprint> bs) {
        for (EnemyBlueprint b : bs) if (b.type.equals(t)) return b;
        throw new RuntimeException("No blueprint: " + t);
    }
    private static Enemy makeEnemy(EnemyBlueprint bp, Player pl, List<Point> patrols) {
        switch (bp.type) {
            case "gunner":         return new Gunner(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.chase);
            case "bomber_striker": return new BomberStriker(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.chase);
            case "bomber_ghost":   return new BomberGhost(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.chase);
            case "striker":        return new Striker(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.chase);
            default:                throw new RuntimeException("Unknown: " + bp.type);
        }
    }
}
