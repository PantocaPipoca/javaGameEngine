package GameEngine;

public class ShapeFactory {
    public static Shape createShape(String name) {
        String type = name.replaceAll("_[0-9]+$", "");

        switch (type) {
            case "player":
            case "striker":
            case "gunner":
            case "bomber_ghost":
            case "bomber_striker":
                return new Shape(name, 100, 100, 0, 0);
            case "pistol":
                return new Shape(name, 50, 50, 0, 0);
            case "bullet":
                return new Shape(name, 20, 20, 0, 0);
            default:
                System.err.println("Tipo de shape desconhecido: " + type);
                return new Shape(name, 100, 100, 0, 0); // fallback
        }
    }
}
