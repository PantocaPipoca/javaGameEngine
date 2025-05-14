package Game.Entities.Enemies;

import java.util.List;

import Figures.Point;
import Game.Entities.Health;
import GameEngine.IGameObject;

public class BomberStriker extends Bomber {
    
    public BomberStriker(Health health, IGameObject player, List<Point> patrolPoints, double patrolSpeed, double chaseSpeed) {
        super(health, player, patrolPoints);
    }

}
