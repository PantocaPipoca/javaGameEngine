package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Camera;
import GameEngine.*;
import javax.swing.*;
import java.awt.*;

import GameEngine.Shape;

public class MainMenuUI {
    private static MainMenuUI instance; // Add this for singleton access
    private boolean active = false;
    private GameObject menuSprite;

    public MainMenuUI() {
        instance = this;
    }

    public static MainMenuUI getInstance() {
        return instance;
    }

    public boolean isVisible() {
        return active;
    }

    public boolean isInPlay(int x, int y) {
        return MainMenuShape.isInPlay(x, y);
    }

    public boolean isInQuit(int x, int y) {
        return MainMenuShape.isInQuit(x, y);
    }

    public void render(Graphics g) {
        if (menuSprite != null && menuSprite.shape() != null)
            menuSprite.shape().render(g, menuSprite.transform(), false, 0);
    }

    public void showMenu() {
        if (active) return;
        active = true;
        Point center = Camera.getInstance(null).position();

        UIBehaviour menuBehaviour = new UIBehaviour();
        menuSprite = new GameObject("ui_mainmenu", new Transform(center, 1000, 0, 1), new Circle("0 0 1"), menuBehaviour);
        menuBehaviour.gameObject(menuSprite);
        menuSprite.setShape(new MainMenuShape());
        GameEngine.getInstance().addEnabled(menuSprite);
    }

    public void hideMenu() {
        if (!active) return;
        active = false;
        GameEngine.getInstance().destroy(menuSprite);
    }
}

// Custom shape for main menu rendering
class MainMenuShape extends Shape {
    private static final Rectangle playBox = new Rectangle(880, 600, 160, 60);
    private static final Rectangle quitBox = new Rectangle(880, 680, 160, 60);
    private static final Image title = new ImageIcon("sprites/titleLogo.png").getImage();

    public MainMenuShape() {
        super("mainmenu", 0, 0, 0, 0);
    }

    @Override
    public void render(Graphics g, ITransform t, boolean flip, double angle) {
        // --- Imagem por cima com tamanho customizado ---
        int scaledWidth = 600;
        int scaledHeight = 400;
        int x = (1930 - scaledWidth) / 2;
        int y = 250;

        g.drawImage(title, x, y, scaledWidth, scaledHeight, null);

        // Botões
        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.setColor(Color.WHITE);

        g.drawRect(playBox.x, playBox.y, playBox.width, playBox.height);
        g.drawString("PLAY", playBox.x + 40, playBox.y + 40);

        g.drawRect(quitBox.x, quitBox.y, quitBox.width, quitBox.height);
        g.drawString("QUIT", quitBox.x + 40, quitBox.y + 40);
    }

    public static boolean isInPlay(int x, int y) {
        return playBox.contains(x, y);
    }

    public static boolean isInQuit(int x, int y) {
        return quitBox.contains(x, y);
    }
}