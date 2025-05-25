package Game.UI;

import Figures.Circle;
import Figures.Point;
import Game.Camera;
import GameEngine.*;
import javax.swing.*;
import java.awt.*;

import GameEngine.Shape;

/**
 * Class that manages the main menu UI.
 * Responsible for displaying the main menu, handling button creation, and user interaction.
 * Singleton pattern is used to ensure only one instance exists.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (25/05/25)
 * @inv Only one instance of MainMenuUI exists at a time.
 */
public class MainMenuUI {
    private static MainMenuUI instance;
    private boolean active = false;
    private GameObject menuSprite;

    ///////////////////////////// Constructors /////////////////////////////

    /**
     * Constructs the MainMenuUI and sets the singleton instance.
     */
    public MainMenuUI() {
        instance = this;
    }

    ///////////////////////////// Singleton Access /////////////////////////////

    /**
     * Returns the singleton instance of MainMenuUI.
     * @return the singleton instance
     */
    public static MainMenuUI getInstance() {
        return instance;
    }

    ///////////////////////////// UI Logic /////////////////////////////

    /**
     * Checks if the main menu is currently visible.
     * @return true if visible, false otherwise
     */
    public boolean isVisible() {
        return active;
    }

    /**
     * Checks if the given coordinates are within the "Play" button.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the coordinates are within the "Play" button, false otherwise
     */
    public boolean isInPlay(int x, int y) {
        return MainMenuShape.isInPlay(x, y);
    }

    /**
     * Checks if the given coordinates are within the "Quit" button.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the coordinates are within the "Quit" button, false otherwise
     */
    public boolean isInQuit(int x, int y) {
        return MainMenuShape.isInQuit(x, y);
    }

    /**
     * Renders the main menu UI.
     * @param g the graphics context
     */
    public void render(Graphics g) {
        if (menuSprite != null && menuSprite.shape() != null)
            menuSprite.shape().render(g, menuSprite.transform(), false, 0);
    }

    /**
     * Shows the main menu UI, creating and enabling the menu sprite.
     */
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

    /**
     * Hides the main menu UI, destroying the menu sprite.
     */
    public void hideMenu() {
        if (!active) return;
        active = false;
        GameEngine.getInstance().destroy(menuSprite);
    }
}

/**
 * Custom shape for main menu rendering.
 * Responsible for drawing the main menu title and buttons, and for hit detection.
 */
class MainMenuShape extends Shape {
    private static final Rectangle playBox = new Rectangle(880, 600, 160, 60);
    private static final Rectangle quitBox = new Rectangle(880, 680, 160, 60);
    private static final Image title = new ImageIcon("sprites/titleLogo.png").getImage();

    /**
     * Constructs the MainMenuShape.
     */
    public MainMenuShape() {
        super("mainmenu", 0, 0, 0, 0);
    }

    /**
     * Renders the main menu shape, including title and buttons.
     * @param g the graphics context
     * @param t the transform
     * @param flip whether to flip the image
     * @param angle the rotation angle
     */
    @Override
    public void render(Graphics g, ITransform t, boolean flip, double angle) {
        int scaledWidth = 600;
        int scaledHeight = 400;
        int x = (1930 - scaledWidth) / 2;
        int y = 250;

        g.drawImage(title, x, y, scaledWidth, scaledHeight, null);

        g.setFont(new Font("Arial", Font.BOLD, 32));
        g.setColor(Color.WHITE);

        g.drawRect(playBox.x, playBox.y, playBox.width, playBox.height);
        g.drawString("PLAY", playBox.x + 40, playBox.y + 40);

        g.drawRect(quitBox.x, quitBox.y, quitBox.width, quitBox.height);
        g.drawString("QUIT", quitBox.x + 40, quitBox.y + 40);
    }

    /**
     * Checks if the given coordinates are within the "Play" button.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if within the "Play" button, false otherwise
     */
    public static boolean isInPlay(int x, int y) {
        return playBox.contains(x, y);
    }

    /**
     * Checks if the given coordinates are within the "Quit" button.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if within the "Quit" button, false otherwise
     */
    public static boolean isInQuit(int x, int y) {
        return quitBox.contains(x, y);
    }
}