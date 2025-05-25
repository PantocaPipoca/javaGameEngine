package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Camera;
import GameEngine.*;
import Game.Game;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // After 10 seconds, reload level 0 and reset the victory screen
        Timer timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the victory sprite if still present
                GameEngine.getInstance().destroy(victorySprite);
                active = false;
                // Reload level 0
                Game.getInstance().loadRoom(0);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}