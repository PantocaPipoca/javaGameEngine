// Ficheiro: Game/UI/UITextShape.java
package Game.UI;

import GameEngine.Shape;
import GameEngine.ITransform;

import java.awt.*;

public class UITextShape extends Shape {
    private String text;

    public UITextShape(String text) {
        super("ui_text", 0, 0, 0, 0);
        this.text = text;
    }

    public void setText(String t) { this.text = t; }

    public String getText() { return text; }

    @Override
    public void render(Graphics g, ITransform t, boolean flip, double angle) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(text, (int) t.position().x(), (int) t.position().y());
    }
}
