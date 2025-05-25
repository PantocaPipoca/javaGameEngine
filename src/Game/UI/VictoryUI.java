package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Camera;
import GameEngine.*;

public class VictoryUI {
    private boolean active = false;
    private GameObject victorySprite;

    public void showVictory() {
        if (active) return;
        active = true;
        Point center = Camera.getInstance(null).position();

        UIBehaviour victoryBehaviour = new UIBehaviour();
        victorySprite = new GameObject("ui_victory", new Transform(center, 1000, 0, 1), new Circle("0 0 1"), victoryBehaviour);
        victoryBehaviour.gameObject(victorySprite);
        victorySprite.setShape(ShapeFactory.createShape("victory", 1));
        GameEngine.getInstance().addEnabled(victorySprite);
    }
}