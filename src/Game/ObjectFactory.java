package Game;

import Figures.GeometricFigure;
import GameEngine.GameObject;
import GameEngine.IBehaviour;
import GameEngine.ITransform;
import GameEngine.Movement;


public class ObjectFactory {
    
    public static GameObject createPlayer(Health health, Movement movement, String name, ITransform transform, GeometricFigure figure, IBehaviour behaviour) {

        GameObject player = new GameObject(name, transform, figure, movement, behaviour);
        player.behaviour().gameObject(player);

        return player;
    }

}
