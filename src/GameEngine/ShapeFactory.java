package GameEngine;

public class ShapeFactory {
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
            default:
                System.err.println("Tipo de shape desconhecido: " + type);
                return new Shape(name, 100 * scale, 100 * scale, 0, 0); // fallback
        }
    }
}
