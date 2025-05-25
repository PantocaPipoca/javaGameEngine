package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Camera;
import GameEngine.*;
import Game.Game;
import Game.Audio.SoundPlayer;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that manages the Victory UI.
 * Responsible for displaying the victory screen and handling its timed removal and level reload.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (25/05/25)
 * @inv Only one victory screen is active at a time.
 */
public class VictoryUI {
    private boolean active = false;
    private GameObject victorySprite;

    /**
     * Shows the victory UI, creating and enabling the victory sprite.
     * After 10 seconds, removes the sprite and reloads the first level.
     */
    public void showVictory() {
        if (active) return;
        SoundPlayer.stopBackgroundMusic();
        SoundPlayer.playSound("songs/victory.wav");
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
                GameEngine.getInstance().destroy(victorySprite);
                active = false;
                Game.getInstance().loadRoom(0);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}