package Game.UI;

import GameEngine.Shape;
import GameEngine.ITransform;
import java.awt.*;

/**
 * Shape class for rendering UI text.
 * Responsible for displaying a string at a given position in the UI.
 * Extends the Shape class for integration with the rendering system.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (25/05/25)
 * @inv Text must not be null.
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
     * @return the current text
     */
    public String getText() { return text; }

    /**
     * Renders the text at the specified transform position.
     * @param g the graphics context
     * @param t the transform
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