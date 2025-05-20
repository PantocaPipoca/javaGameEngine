package GameEngine;

/**
 * Factory class for creating Shape objects based on type and scale.
 * Handles creation logic for different game entity shapes.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class ShapeFactory {

    /**
     * Creates a Shape object based on the given name and scale.
     * @param name the name/type of the shape
     * @param scale the scale factor for the shape
     * @return the created Shape object
     */
    public static Shape createShape(String name, int scale) {
        String type = name.replaceAll("_[0-9]+$", "");

        switch (type) {
            case "player":
            case "striker":
            case "gunner":
            case "bomber_ghost":
            case "bomber_striker":
                return new Shape(name, 100 * scale, 100 * scale, 0, 0);
            case "pistol":
                return new Shape(name, 50 * scale, 50 * scale, 0, 0);
            case "bullet":
                return new Shape(name, 20 * scale, 20 * scale, 0, 0);
            case "bomb":
                return new Shape(name, 20 * scale, 20 * scale, 0, 0);
            default:
                System.err.println("Tipo de shape desconhecido: " + type);
                return new Shape(name, 100 * scale, 100 * scale, 0, 0);
        }
    }
}