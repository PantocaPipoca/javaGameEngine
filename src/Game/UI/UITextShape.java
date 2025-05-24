package Game.UI;

import GameEngine.Shape;
import GameEngine.ITransform;

import java.awt.*;

/**
 * Shape for rendering UI text in the game.
 * Used for displaying dynamic text such as health, score, and ammo in the UI.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class UITextShape extends Shape {
    private String text;

    /**
     * Constructs a UITextShape with the specified text.
     * @param text the text to display
     */
    public UITextShape(String text) {
        super("ui_text", 0, 0, 0, 0);
        this.text = text;
    }

    /**
     * Sets the text to display.
     * @param t the new text
     */
    public void setText(String t) { this.text = t; }

    /**
     * Gets the current text.
     * @return the text
     */
    public String getText() { return text; }

    /**
     * Renders the text on the screen.
     * @param g the graphics context
     * @param t the transform for positioning
     * @param flip whether to flip the text (unused)
     * @param angle the rotation angle (unused)
     */
    @Override
    public void render(Graphics g, ITransform t, boolean flip, double angle) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(text, (int) t.position().x(), (int) t.position().y());
    }
}