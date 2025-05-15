package GameEngine;

import javax.swing.*;
import java.awt.*;

public class Shape {
    private Image image;
    private int width = 100;
    private int height = 100;

    public Shape(String objectName) {
        this.image = loadImage(objectName);
    }

    private Image loadImage(String name) {
        String base = name.replaceAll("_[0-9]+$", "");
        String path = "sprites/" + base.toLowerCase() + ".png";
        Image img = new ImageIcon(path).getImage();

        if (img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
            System.err.println("Image not loaded: " + path);
            return null;
        }

        return img;
    }

    public void render(Graphics g, ITransform t) {
        if (image == null) return;

        int x = (int) t.position().x();
        int y = (int) t.position().y();

        g.drawImage(image, x - width / 2, y - height / 2, width, height, null);
    }
}