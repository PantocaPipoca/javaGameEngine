package Game.Loaders;

import Game.Loaders.ConfigModels.*;
import Game.Obstacles.Door;
import Game.Obstacles.Wall;
import Game.Entities.Commons.Health;
import Game.Entities.Enemies.*;
import Game.Entities.Player.Player;
import Game.Gun.*;
import Game.Room;
import Figures.Point;
import Figures.Polygon;
import Figures.Circle;
import GameEngine.*;
import java.util.*;

/**
 * Factory for constructing Room objects from configuration models.
 * Handles creation of figures, player, and enemies for a level.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class RoomFactory {

    /**
     * Builds a Room from the given LevelConfig.
     * @param lvl the level configuration
     * @return the constructed Room
     */
    public static Room make(LevelConfig lvl) {
        // Build figures
        List<IGameObject> figures = new ArrayList<>();
        for (FigureBlueprint fb : lvl.figures) {
            GameObject go;
            if ("polygon".equals(fb.type)) {
                StringBuilder sb = new StringBuilder();
                sb.append(fb.vertices.size());
                for (List<Number> v : fb.vertices) {
                    sb.append(" ").append(v.get(0).doubleValue()).append(" ").append(v.get(1).doubleValue());
                }
                Polygon poly = new Polygon(sb.toString());
                Point center = poly.centroid();
                Transform t = new Transform(center, fb.layer, 0, 1);
                if (fb.objectType != null && fb.objectType.equals("door")) {
                    go = new GameObject("door", t, poly, new Door());
                } else {
                    go = new GameObject("wall", t, poly, new Wall());
                }
                go.behaviour().gameObject(go);
            }
            else if ("circle".equals(fb.type)) {
                double cx = fb.center.get(0).doubleValue();
                double cy = fb.center.get(1).doubleValue();
                double r = fb.radius;
                Circle circle = new Circle(cx + " " + cy + " " + r);
                Transform t = new Transform(new Point(cx, cy), fb.layer, 0, 1);
                go = new GameObject("circle", t, circle, new Wall());
                go.behaviour().gameObject(go);
            }
            else throw new RuntimeException("Unknown figure type: " + fb.type);
            figures.add(go);
        }

        // Build player
        PlayerConfig pc = lvl.player;
        Player pl = new Player(new Health(pc.health), pc.speed, pc.roll);
        Transform t = new Transform(pc.pos, pc.layer, pc.angle, pc.scale);
        GameObject go = new GameObject("player", t, new Circle(pc.pos.x() + " " + pc.pos.y() + " 20"), pl);
        pl.gameObject(go);

        if (pc.playerWeapons != null) {
            for (WeaponBlueprint wp : pc.playerWeapons) {
                Weapon gun = makeWeapon(wp, pl.gameObject());
                pl.addGun(gun);
            }
        }

        // Build enemies
        List<Enemy> enemies = new ArrayList<>();
        Collections.shuffle(lvl.spawns);
        Random rnd = new Random();
        for (int i = 0; i < lvl.count && i < lvl.spawns.size(); i++) {
            EnemySpawn es = lvl.spawns.get(i);
            double r = rnd.nextDouble();
            String type = chooseByChance(r, lvl.chances);
            EnemyBlueprint bp = findBlueprint(type, lvl.blueprints);
            Enemy e = makeEnemy(bp, pl, es.patrols, i); // Pass index for unique naming
            for (WeaponBlueprint wp : bp.enemyWeapons) {
                Weapon gun = makeWeapon(wp, e.gameObject());
                if (e.gameObject().name().startsWith("bomber_ghost")) {
                    gun.gameObject().transform().move(new Point(0, 0), -1);
                }
                e.addGun(gun); // Always add the weapon
            }
            enemies.add(e);
        }

        return new Room(pl, enemies, figures);
    }

    /**
     * Chooses a key from the map based on a random value and the associated chances.
     * @param r random value between 0 and 1
     * @param m map of keys to their chance values
     * @return the chosen key
     */
    private static String chooseByChance(double r, Map<String, Double> m) {
        double acc = 0;
        for (Map.Entry<String, Double> e : m.entrySet()) {
            acc += e.getValue();
            if (r <= acc) return e.getKey();
        }
        return m.keySet().iterator().next();
    }

    /**
     * Finds an EnemyBlueprint by type.
     * @param t the type string
     * @param bs list of blueprints
     * @return the matching blueprint
     * @throws RuntimeException if not found
     */
    private static EnemyBlueprint findBlueprint(String t, List<EnemyBlueprint> bs) {
        for (EnemyBlueprint b : bs) if (b.type.equals(t)) return b;
        throw new RuntimeException("No blueprint: " + t);
    }

    /**
     * Creates an Enemy instance from a blueprint.
     * @param bp the blueprint
     * @param pl the player
     * @param patrols patrol points
     * @return the created Enemy
     */
    private static Enemy makeEnemy(EnemyBlueprint bp, Player pl, List<Point> patrols, int index) {
        Enemy e;
        switch (bp.type) {
            case "gunner":
                e = new Gunner(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.detectionRadius, bp.attackRadius, bp.chase, bp.forgetfulRadius, bp.outOfRangeRadius);
                break;
            case "bomber_striker":
                e = new BomberStriker(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.detectionRadius, bp.attackRadius, bp.chase, bp.forgetfulRadius);
                break;
            case "bomber_ghost":
                e = new BomberGhost(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.detectionRadius, bp.attackRadius, bp.chase, bp.forgetfulRadius);
                break;
            case "striker":
                e = new Striker(new Health(bp.health), pl.gameObject(), patrols, bp.patrol, bp.detectionRadius, bp.attackRadius, bp.chase, bp.forgetfulRadius);
                break;
            default:
                throw new RuntimeException("Unknown: " + bp.type);
        }
        int layer = bp.type.equals("bomber_ghost") ? 2 : 1;
        Point spawn = patrols.isEmpty() ? new Point(0, 0) : patrols.get(0);
        Transform et = new Transform(spawn, layer, 0, 2);
        GameObject ego = new GameObject(bp.type + "_" + index, et, new Circle(spawn.x() + " " + spawn.y() + " 20"), e);
        e.gameObject(ego);
        return e;
    }
    public static Weapon makeWeapon(WeaponBlueprint wp, IGameObject owner) {
        Weapon gun;
        switch (wp.type) {
            case "pistol":
                gun = new Pistol(owner, wp.bulletSpeed, wp.damage, wp.fireRate, wp.reloadTime, wp.magazineSize, wp.maxAmmo, wp.distanceFromOwner);
                IGameObject pistolObject = new GameObject(
                    gun.name(),
                    new Transform(owner.transform().position(), owner.transform().layer() + 1, 0, 2),
                    new Circle("0 0 20"),
                    gun
                );
                gun.gameObject(pistolObject);
                break;
            case "rifle":
                gun = new Rifle(owner, wp.bulletSpeed, wp.damage, wp.fireRate, wp.reloadTime, wp.magazineSize, wp.maxAmmo, wp.distanceFromOwner);
                IGameObject rifleGameObject = new GameObject(
                    gun.name(),
                    new Transform(owner.transform().position(), owner.transform().layer() + 1, 0, 2),
                    new Circle("0 0 20"),
                    gun
                );
                gun.gameObject(rifleGameObject);
                break;
            case "bomb":
                gun = new Bomb(owner, "bomb", wp.damage, wp.fireRate, wp.blastDamage, wp.distanceFromOwner);
                IGameObject bombObject = new GameObject(
                    gun.name(),
                    new Transform(owner.transform().position(), owner.transform().layer(), 0, 2),
                    new Circle("0 0 " + wp.blastRadius),
                    gun
                );
                gun.gameObject(bombObject);
                break;
            default:
                throw new RuntimeException("Unknown weapon: " + wp.type);
        }
        return gun;
    }
}