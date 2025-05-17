package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Shape {
    private Image image;
    private int width, height;
    private int offsetX, offsetY;

    public Shape(String name, int width, int height, int offsetX, int offsetY) {
        this.image = loadImage(name);
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    private Image loadImage(String name) {
        String path = "sprites/" + name + ".png";
        Image img = new ImageIcon(path).getImage();

        // se a imagem falhar, tenta usar o nome base (sem _0, _1...)
        if (img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
            String fallback = name.replaceAll("_[0-9]+$", "");
            path = "sprites/" + fallback + ".png";
            img = new ImageIcon(path).getImage();
        }

        return img;
    }

    public static List<Shape> loadAnimation(String baseName, int frameCount) {
        List<Shape> frames = new ArrayList<>();
        for (int i = 0; i < frameCount; i++) {
            String name = baseName + "_" + i;
            frames.add(new Shape(name, 100, 100, 0, 0));
        }
        return frames;
    }

    public void render(Graphics g, ITransform t, boolean flip, double angle) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        int x = (int) t.position().x();
        int y = (int) t.position().y();

        g2d.translate(x, y);

        if (flip) {
            g2d.scale(-1, 1);
            if(angle > 0) { g2d.rotate(Math.toRadians(180 - angle)); }
        } else {
            g2d.rotate(Math.toRadians(angle));
        }

        g2d.translate(offsetX, offsetY); // só desloca a imagem
        g2d.drawImage(image, -width / 2, -height / 2, width, height, null);

        g2d.setTransform(old);
    }

}
