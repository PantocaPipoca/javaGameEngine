package Game.Entities.Enemies;

import java.util.List;

import Figures.Point;
import Game.Entities.Health;
import GameEngine.IGameObject;

public class BomberGhost extends Bomber {

    public BomberGhost(Health health, IGameObject player, List<Point> patrolPoints,
                  double patrolSpeed, double detectionRadius, double attackRadius,
                  double chaseSpeed, double forgetfullRadius) {
        super(health, player, patrolPoints, patrolSpeed, detectionRadius, attackRadius, chaseSpeed, forgetfullRadius);
    }
    
}
