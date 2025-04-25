import java.util.ArrayList;
import java.util.List;

import Figures.*;
import Game.*;
import GameEngine.*;

/**
 * Main class that only reads input data and creates the necessary objects. In this case, it reads the data to create a geometric figure and prints its representation.
 * Author: Daniel Pantyukhov a83896
 * Version: 1.0 (18/03/25)
 **/
public class Main {
    public static void main(String[] args) {

        Health playerHealth = new Health(100);
        IEntity playerBehaviour = new Player(playerHealth);
        ITransform playerTransform = new Transform(new Point(0, 0), 0, 0, 1);
        GeometricFigure playerFigure = new Circle("0 0 1");
        Movement playerMovement = new Movement(1, 1, 0, 0, 0);
        GameObject player = new GameObject("Player", playerTransform, playerFigure, playerMovement, playerBehaviour);
        player.behaviour().gameObject(player);
        playerBehaviour.getStateMachine().setOwner(player);

        Health enemyHealth = new Health(100);
        List<Point> patrolPoints = new ArrayList<>();
        patrolPoints.add(new Point(0, 0));
        patrolPoints.add(new Point(1, 1));
        patrolPoints.add(new Point(2, 2));
        IEntity enemyBehaviour = new Enemy(patrolPoints, enemyHealth);
        ITransform enemyTransform = new Transform(new Point(0, 0), 0, 0, 1);
        GeometricFigure enemyFigure = new Circle("0 0 1");
        Movement enemyMovement = new Movement(1, 1, 0, 0, 0);
        GameObject enemy = new GameObject("Enemy", enemyTransform, enemyFigure, enemyMovement, enemyBehaviour);
        enemy.behaviour().gameObject(enemy);
        enemyBehaviour.getStateMachine().setOwner(enemy);


        GameEngine gameEngine = new GameEngine();
        gameEngine.addEnabled(player);
        gameEngine.addEnabled(enemy);
        gameEngine.run();

    }
}