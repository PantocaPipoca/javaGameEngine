package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Camera;
import GameEngine.GameObject;
import GameEngine.ShapeFactory;
import GameEngine.Transform;
import GameEngine.GameEngine;

import java.awt.*;

public class GameOverUI {
    private static final Rectangle yesBox = new Rectangle(645, 625, 120, 50);
    private static final Rectangle noBox  = new Rectangle(800, 510, 120, 50);
    private static boolean active = false;
    private static Point spriteWorldPosition = null;

    public static void showBlackout() {
        if (active) return; // já está a mostrar
        active = true;

        GameObject blackout = new GameObject(
                "ui_blackout",
                new Transform(Camera.getInstance(null).position(), 999, 0, 1),
                new Circle("0 0 1"),
                new UIBehaviour()
        );
        blackout.setShape(ShapeFactory.createShape("blackout", 1));
        GameEngine.getInstance().addEnabled(blackout);

        GameObject gameOverSprite = new GameObject(
                "ui_gameover_sprite",
                new Transform(Camera.getInstance(null).position(), 1000, 0, 1),
                new Circle("0 0 1"),
                new UIBehaviour()
        );
        gameOverSprite.setShape(ShapeFactory.createShape("gameover", 1));
        GameEngine.getInstance().addEnabled(gameOverSprite);
        spriteWorldPosition = gameOverSprite.transform().position();
    }

    public static boolean isGameOverShown() {
        return active;
    }

    public static boolean isInYesBox(int x, int y) {
        return yesBox.contains(x, y);
    }

    public static boolean isInNoBox(int x, int y) {
        return noBox.contains(x, y);
    }

    public static Rectangle getYesBox() {
        if (spriteWorldPosition == null) return new Rectangle(0, 0, 0, 0);
        return new Rectangle((int) (spriteWorldPosition.x() - 165), (int) (spriteWorldPosition.y() + 140), 120, 50);
    }

    public static Rectangle getNoBox() {
        if (spriteWorldPosition == null) return new Rectangle(0, 0, 0, 0);
        return new Rectangle((int) (spriteWorldPosition.x() + 35), (int) (spriteWorldPosition.y() + 140), 120, 50);
    }

    public static void reset() {
        active = false;
    }
}