package Game.Loaders;

import Game.Loaders.ConfigModels.*;
import Game.Obstacles.Wall;
import Game.Entities.Health;
import Game.Entities.Enemies.*;
import Game.Entities.Player.Player;
import Game.Gun.*;
import Game.Room;
import Figures.Point;
import Figures.Polygon;
import Figures.Circle;
import GameEngine.*;
import java.util.*;

public class RoomFactory {
    public static Room make(LevelConfig lvl) {
        // build figures
        List<IGameObject> figures = new ArrayList<>();
        for (FigureBlueprint fb : lvl.figures) {
            GameObject go;

            if("polygon".equals(fb.type)) {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.vertices.size());
                for (List<Number> v : fb.vertices) {
                    sb.append(" ").append(v.get(0).doubleValue()).append(" ").append(v.get(1).doubleValue());
                }
                Polygon poly = new Polygon(sb.toString());
                Point center = poly.centroid();
                Transform t = new Transform(center, fb.layer, 0, 1);
                go = new GameObject("wall", t, poly, new Wall());
                go.behaviour().gameObject(go);
            }
            else if("circle".equals(fb.type)) {
                double cx = fb.center.get(0).doubleValue();
                double cy = fb.center.get(1).doubleValue();
                double r = fb.radius;

                Circle circle = new Circle(cx+" "+cy+" "+r);
                Transform t = new Transform(new Point(cx, cy), fb.layer, 0, 1);
                go = new GameObject("circle", t, circle, new Wall());
                go.behaviour().gameObject(go);

            } else {
                throw new RuntimeException("Unknown figure type: " + fb.type);
            }
            figures.add(go);
        }

        // build player
        PlayerConfig pc = lvl.player;
        Player pl = new Player(new Health(pc.health), pc.speed, pc.roll);
        Transform t = new Transform(pc.pos, pc.layer, pc.angle, pc.scale);
        GameObject go = new GameObject("player", t, new Circle(pc.pos.x()+" "+pc.pos.y()+" 20"), pl);
        pl.gameObject(go);

        if (lvl.player != null && lvl.player.playerWeapons != null) {
            for (WeaponBlueprint wp : lvl.player.playerWeapons) {
                Weapon gun;
                switch (wp.type) {
                    case "pistol":
                        gun = new Pistol(pl.gameObject(), wp.bulletSpeed, wp.damage, wp.fireRate, wp.reloadTime, wp.magazineSize, wp.maxAmmo, wp.distanceFromOwner);
                        IGameObject pistolObject = new GameObject(
                            gun.name() + "_0",
                            new Transform(go.transform().position(), go.transform().layer() + 1, 0, 1),
                            new Circle("0 0 20"),
                            gun
                        );
                        gun.gameObject(pistolObject);
                        break;
                    
                    default:
                        throw new RuntimeException("Unknown weapon: " + wp.type);
                }
                pl.addGun(gun);
            }
        }


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

        return new Room(pl, enemies, figures);
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
            case "gunner":         return new Gunner(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.detectionRadius, bp.attackRadius, bp.chase, bp.forgetfulRadius);
            case "bomber_striker": return new BomberStriker(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.detectionRadius, bp.attackRadius, bp.chase, bp.forgetfulRadius);
            case "bomber_ghost":   return new BomberGhost(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.detectionRadius, bp.attackRadius, bp.chase, bp.forgetfulRadius);
            case "striker":        return new Striker(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.detectionRadius, bp.attackRadius, bp.chase, bp.forgetfulRadius);
            default:                throw new RuntimeException("Unknown: " + bp.type);
        }
    }
}
