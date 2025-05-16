package Game.Gun;

import java.util.List;

import Figures.Point;
import GameEngine.GameEngine;
import GameEngine.IBehaviour;
import GameEngine.IGameObject;
import GameEngine.InputEvent;

public abstract class Weapon implements IBehaviour{
    protected IGameObject owner;
    protected GameEngine gameEngine;
    protected IGameObject go;
    protected double fireRate;
    private double distanceFromOwner;
    private String name;

    protected double fireCooldown = 0.0;

    public Weapon(IGameObject owner, String name, double damage, double fireRate, double distanceFromOwner) {
        this.owner = owner;
        this.name = name;
        this.fireRate = fireRate;
        this.gameEngine = GameEngine.getInstance();
        this.distanceFromOwner = distanceFromOwner;

    }

    public void updateRotation(Point mousePosition) {
        if (go == null) {
            throw new IllegalStateException("GameObject for the gun is not initialized.");
        }

        // Calculate the angle to the mouse position
        Point ownerPosition = owner.transform().position();
        double dx = mousePosition.x() - ownerPosition.x();
        double dy = mousePosition.y() - ownerPosition.y();
        double targetRotation = Math.atan2(dy, dx); // Angle in radians

        // Update the gun's rotation
        go.transform().setAngle(Math.toDegrees(targetRotation)); // Rotate the gun to face the target

        // Update the gun's position to rotate around the owner
        double gunX = ownerPosition.x() + Math.cos(targetRotation) * distanceFromOwner;
        double gunY = ownerPosition.y() + Math.sin(targetRotation) * distanceFromOwner;
        go.transform().move(new Point(gunX - go.transform().position().x(), gunY - go.transform().position().y()), 0);
    }

    public void shoot() {
        if (go == null) {
            System.out.println("Gun is not initialized.");
            return;
        }
        if (fireCooldown > 0) {
            System.out.println("Gun is cooling down.");
            return;
        }
        fireCooldown = 1.0 / fireRate;
    }
    
    public IGameObject gameObject() {
        return go;
    }
    public void gameObject(IGameObject go) {
        this.go = go;
    }
    public void onInit() {

    }
    public void onEnabled() {

    }
    public void onDisabled() {

    }
    public void onDestroy() {

    }
    public void onUpdate(double dT, InputEvent ie) { 
        Point target = new Point(ie.getMouseWorldPosition().getX(), ie.getMouseWorldPosition().getY()); //To-do seguir o mouse ou algo assim
        updateRotation(target);
        go.update();
        if (fireCooldown > 0) {
            fireCooldown -= dT;
            if (fireCooldown < 0) fireCooldown = 0;
        }
    }
    public void onCollision(List<IGameObject> gol) {

    }
    public String name() {
        return name;
    }
}
