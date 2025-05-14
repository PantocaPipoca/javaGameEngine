package Game.Gun;

import java.util.List;

import Figures.Point;
import GameEngine.IGameObject;
import GameEngine.InputEvent;
import GameEngine.GameEngine;
import GameEngine.IBehaviour;

public class Bullet implements IBehaviour {
    private double rotation;
    private double speed;
    private IGameObject go;
    private GameEngine gameEngine = GameEngine.getInstance();

    public Bullet(double rotation, double speed) {
        this.rotation = rotation;
        this.speed = speed;
    }

    @Override
    public void onUpdate(double dT, InputEvent ie) {
        // Move the bullet in the direction it is facing
        double dx = Math.cos(rotation) * speed * dT;
        double dy = Math.sin(rotation) * speed * dT;
        go.transform().move(new Point(dx, dy), 0);
    }

    @Override
    public void onCollision(List<IGameObject> gol) {
        // Destroy the bullet on collision
        for (IGameObject other : gol) {
            System.out.println("Bullet collided with: " + other.name());
        }
        // Destroy the bullet (remove it from the game engine)
        gameEngine.destroy(go);
    }

    @Override
    public void onInit() {}

    @Override
    public void onEnabled() {}

    @Override
    public void onDisabled() {}

    @Override
    public void onDestroy() {}

    public IGameObject gameObject() {
        return go;
    }
    public void gameObject(IGameObject go) {
        this.go = go;
    }
}