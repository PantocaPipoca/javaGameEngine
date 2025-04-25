package Game;

import Figures.*;
import GameEngine.*;

public class PlayerLoader {

    public static Player parseFromBlock(String block) {
        // Extrair dados base
        int maxHealth = JsonUtils.extractInt(block, "\"maxHealth\":");
        String initialState = JsonUtils.extractString(block, "\"initialState\"");

        // Criar componentes do Player
        Health health = new Health(maxHealth);
        StateMachine sm = new StateMachine(initialState);
        Player player = new Player(health, sm);

        // Extrair transform
        String posBlock = JsonUtils.extractBlock(block, "\"startPosition\"", "\"layer\"");
        int x = JsonUtils.extractInt(posBlock, "\"x\":");
        int y = JsonUtils.extractInt(posBlock, "\"y\":");
        int layer = JsonUtils.extractInt(block, "\"layer\":");
        double angle = JsonUtils.extractDouble(block, "\"angle\":");
        double scale = JsonUtils.extractDouble(block, "\"scale\":");
        Transform transform = new Transform(new Point(x, y), layer, angle, scale);

        // Extrair movimento
        String moveBlock = JsonUtils.extractBlock(block, "\"movement\"", "\"maxHealth\"");
        double dx = JsonUtils.extractDouble(moveBlock, "\"dx\":");
        double dy = JsonUtils.extractDouble(moveBlock, "\"dy\":");
        int dLayer = JsonUtils.extractInt(moveBlock, "\"dLayer\":");
        double dAngle = JsonUtils.extractDouble(moveBlock, "\"dAngle\":");
        double dScale = JsonUtils.extractDouble(moveBlock, "\"dScale\":");
        Movement movement = new Movement(dx, dy, dLayer, dAngle, dScale);

        // Extrair figura geométrica
        String figBlock = JsonUtils.extractBlock(block, "\"figure\"", "\"movement\"");
        GeometricFigure figure = parseFigureFromBlock(figBlock);

        // Criar GameObject e associar ao player
        String name = JsonUtils.extractString(block, "\"name\"");
        GameObject go = new GameObject(name, transform, figure, movement, player);
        player.gameObject(go);

        return player;
    }

    private static GeometricFigure parseFigureFromBlock(String block) {
        String type = JsonUtils.extractString(block, "\"type\"");
        if ("circle".equalsIgnoreCase(type)) {
            double cx = JsonUtils.extractDouble(block, "\"x\":");
            double cy = JsonUtils.extractDouble(block, "\"y\":");
            double radius = JsonUtils.extractDouble(block, "\"radius\":");
            return new Circle(cx + " " + cy + " " + radius);
        }
        return null;
    }
}