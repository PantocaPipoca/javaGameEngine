package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Observer.GameListener;
import GameEngine.*;

public class GameUI implements GameListener {
    private static GameUI instance;
    private GameObject healthUI, scoreUI, ammoUI;

    private GameUI() {}

    public void initUI(GameEngine engine, Point cam) {
        if (healthUI != null && scoreUI != null && ammoUI != null) return;

        UIBehaviour healthBehaviour = new UIBehaviour();
        healthUI = new GameObject("ui_health", new Transform(new Point(cam.x(), cam.y()), 3, 0, 1), new Circle("0 0 1"), healthBehaviour);
        healthBehaviour.gameObject(healthUI);
        healthUI.setShape(new UITextShape("Health: ---"));
        engine.addEnabled(healthUI);

        UIBehaviour scoreBehaviour = new UIBehaviour();
        scoreUI = new GameObject("ui_score", new Transform(new Point(cam.x(), cam.y()), 3, 0, 1), new Circle("0 0 1"), scoreBehaviour);
        scoreBehaviour.gameObject(scoreUI);
        scoreUI.setShape(new UITextShape("Score: ---"));
        engine.addEnabled(scoreUI);

        UIBehaviour ammoBehaviour = new UIBehaviour();
        ammoUI = new GameObject("ui_ammo", new Transform(new Point(cam.x(), cam.y()), 3, 0, 1), new Circle("0 0 1"), ammoBehaviour);
        ammoBehaviour.gameObject(ammoUI);
        ammoUI.setShape(new UITextShape("Ammo: ---"));
        engine.addEnabled(ammoUI);
    }

    @Override
    public void onPlayerHealthChanged(int health) {
        ((UITextShape) healthUI.shape()).setText("Health: " + health);
    }
    @Override
    public void onAmmoChanged(int currentAmmo, int reserveAmmo) {
        ((UITextShape) ammoUI.shape()).setText("Ammo: " + currentAmmo + " / " + reserveAmmo);
    }
    @Override
    public void onScoreChanged(float score) {
        ((UITextShape) scoreUI.shape()).setText("Score: " + score);
    }

    public static GameUI getInstance() {
        if (instance == null) instance = new GameUI();
        return instance;
    }
}