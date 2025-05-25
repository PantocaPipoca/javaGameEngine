package Game.UI;

import Figures.Circle;
import Figures.Point;
import Figures.Polygon;
import Game.Camera;
import GameEngine.*;

public class GameOverUI {
    private static GameOverUI instance;
    private boolean active = false;
    private GameObject blackout, gameOverSprite, yesButton, noButton;

    private GameOverUI() {}

    public static GameOverUI getInstance() {
        if (instance == null) {
            instance = new GameOverUI();
        }
        return instance;
    }

    public void show() {
        if (active) return;
        active = true;
        Point center = Camera.getInstance(null).position();

        UIBehaviour blackoutBehaviour = new UIBehaviour();
        blackout = new GameObject("ui_blackout", new Transform(center, 999, 0, 1), new Circle("0 0 1"), blackoutBehaviour);
        blackoutBehaviour.gameObject(blackout);
        blackout.setShape(ShapeFactory.createShape("blackout", 1));
        GameEngine.getInstance().addEnabled(blackout);

        UIBehaviour gameOverBehaviour = new UIBehaviour();
        gameOverSprite = new GameObject("ui_gameover_sprite", new Transform(center, 1000, 0, 1), new Circle("0 0 1"), gameOverBehaviour);
        gameOverBehaviour.gameObject(gameOverSprite);
        gameOverSprite.setShape(ShapeFactory.createShape("gameover", 1));
        GameEngine.getInstance().addEnabled(gameOverSprite);

        UIBehaviour yesBehaviour = new UIBehaviour();
        yesButton = new GameObject("ui_yes_button", new Transform(new Point(center.x() - 160, center.y() + 140), 1001, 0, 1), new Polygon("4 0 0 120 0 120 50 0 50"), yesBehaviour);
        yesBehaviour.gameObject(yesButton);
        yesButton.setShape(ShapeFactory.createShape("button_yes", 1));
        GameEngine.getInstance().addEnabled(yesButton);

        UIBehaviour noBehaviour = new UIBehaviour();
        noButton = new GameObject("ui_no_button", new Transform(new Point(center.x() + 20, center.y() + 145), 1001, 0, 1), new Polygon("4 0 0 120 0 120 50 0 50"), noBehaviour);
        noBehaviour.gameObject(noButton);
        noButton.setShape(ShapeFactory.createShape("button_no", 1));
        GameEngine.getInstance().addEnabled(noButton);
    }

    public void reset() {
        active = false;
        GameEngine engine = GameEngine.getInstance();
        engine.destroy(noButton);
        engine.destroy(blackout);
        engine.destroy(yesButton);
        engine.destroy(gameOverSprite);
    }

    public boolean isActive() {
        return active;
    }

    public boolean isInYes(int x, int y) {
        Point center = Camera.getInstance(null).position();
        int bx = (int) (center.x() - 160);
        int by = (int) (center.y() + 140);
        return x >= bx && x <= bx + 120 && y >= by && y <= by + 50;
    }

    public boolean isInNo(int x, int y) {
        Point center = Camera.getInstance(null).position();
        int bx = (int) (center.x() + 20);
        int by = (int) (center.y() + 145);
        return x >= bx && x <= bx + 120 && y >= by && y <= by + 50;
    }
}