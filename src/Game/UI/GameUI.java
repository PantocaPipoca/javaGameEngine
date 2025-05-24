package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Camera;
import Game.Observer.GameListener;
import GameEngine.GameObject;
import GameEngine.Transform;
import GameEngine.GameEngine;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GameUI implements GameListener {

    private static GameUI instance;
    private GameUI() {}
    private GameObject healthUI;
    private GameObject scoreUI;
    private GameObject ammoUI;
    private ArrayList<GameObject> uiObjects = new ArrayList<>();

    public void initUI(GameEngine engine, Point cam) {

        if (healthUI != null && scoreUI != null && ammoUI != null) {
            return; // Já foi inicializado, não precisas de criar de novo
        }

        UITextShape healthText = new UITextShape("Health: ---");
        healthUI = new GameObject("ui_health", new Transform(new Point(cam.x(), cam.y()), 3, 0, 1), new Circle("0 0 1"), new UIBehaviour());
        UITextShape scoreText = new UITextShape("Score: ---");
        scoreUI = new GameObject("ui_score", new Transform(new Point(cam.x(), cam.y()), 3, 0, 1), new Circle("0 0 1"), new UIBehaviour());

        UITextShape ammoText = new UITextShape("Ammo: ---");
        ammoUI = new GameObject("ui_ammo", new Transform(new Point(cam.x(), cam.y()), 3, 0, 1), new Circle("0 0 1"), new UIBehaviour());

        healthUI.setShape(healthText);
        scoreUI.setShape(scoreText);
        ammoUI.setShape(ammoText);

        uiObjects.add(healthUI);
        uiObjects.add(scoreUI);
        uiObjects.add(ammoUI);
    }

    public void updatePositions(Point cam) {
        healthUI.transform().position(new Point(cam.x() - 830, cam.y() - 490));
        scoreUI.transform().position(new Point(cam.x() + 730, cam.y() - 490));
        ammoUI.transform().position(new Point(cam.x() + 730, cam.y() + 400));
    }

    public void render(Graphics2D g) {
        if (uiObjects.isEmpty()) return;

        // Obter posição da câmara
        Point cam = Camera.getInstance(null).position(); // Usa getter existente
        int screenCX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
        int screenCY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;

        double offsetX = screenCX - cam.x();
        double offsetY = screenCY - cam.y();

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform old = g2.getTransform(); // guardar transform original
        g2.translate(offsetX, offsetY);          // aplicar transform da câmara

        for (GameObject go : uiObjects) {
            if (go.shape() != null && go.transform() != null) {
                go.shape().render(g2, go.transform(), go.isFlipped(), go.transform().angle());
            }
        }

        g2.setTransform(old); // repor transform original
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
        if (instance == null) {
            instance = new GameUI();
        }
        return instance;
    }
}