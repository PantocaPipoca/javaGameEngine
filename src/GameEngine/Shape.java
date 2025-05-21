package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a drawable shape with image, size, and offset.
 * Handles loading images, rendering, and animation frame loading.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 */
public class Shape {
    private Image image;
    private int width, height;
    private int offsetX, offsetY;

    /**
     * Constructs a Shape with the given parameters.
     * @param name the name of the image file (without extension)
     * @param width the width of the shape
     * @param height the height of the shape
     * @param offsetX x-offset for rendering
     * @param offsetY y-offset for rendering
     */
    public Shape(String name, int width, int height, int offsetX, int offsetY) {
        this.image = loadImage(name);
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    /**
     * Loads an image from the sprites directory.
     * @param name the image name
     * @return the loaded Image
     */
    private Image loadImage(String name) {
        String path = "sprites/" + name + ".png";
        Image img = new ImageIcon(path).getImage();

        // If the image fails, try using the base name (without _0, _1, ...)
        if (img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
            String fallback = name.replaceAll("_[0-9]+$", "");
            path = "sprites/" + fallback + ".png";
            img = new ImageIcon(path).getImage();
        }

        return img;
    }

    /**
     * Renders the shape using the given graphics context, transform, flip, and angle.
     * @param g the Graphics context
     * @param t the transform to apply
     * @param flip whether to flip horizontally
     * @param angle the rotation angle in degrees
     */
    public void render(Graphics g, ITransform t, boolean flip, double angle) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        int x = (int) t.position().x();
        int y = (int) t.position().y();

        g2d.translate(x, y);

        if (flip) {
            g2d.scale(-1, 1);
            if (angle > 0) {
                g2d.rotate(Math.toRadians(180 - angle));
            }
        } else {
            g2d.rotate(Math.toRadians(angle));
        }

        g2d.translate(offsetX, offsetY);
        g2d.drawImage(image, -width / 2, -height / 2, width, height, null);

        g2d.setTransform(old);
    }

    /////////////////////////////////////////////////// Getters ///////////////////////////////////////////////////

    /**
     * Gets the width of the shape.
     * @return the width
     */
    public int width() {
        return width;
    }

    /**
     * Gets the height of the shape.
     * @return the height
     */
    public int height() {
        return height;
    }

    /**
     * Gets the x offset for rendering.
     * @return the x offset
     */
    public int offsetX() {
        return offsetX;
    }

    /**
     * Gets the y offset for rendering.
     * @return the y offset
     */
    public int offsetY() {
        return offsetY;
    }

    /////////////////////////////////////////////////// Setters ///////////////////////////////////////////////////

    /**
     * Sets the width of the shape.
     * @param width the width to set
     */
    public void width(int width) {
        this.width = width;
    }

    /**
     * Sets the height of the shape.
     * @param height the height to set
     */
    public void height(int height) {
        this.height = height;
    }

    /**
     * Sets the x offset for rendering.
     * @param offsetX the x offset to set
     */
    public void offsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * Sets the y offset for rendering.
     * @param offsetY the y offset to set
     */
    public void offsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    /////////////////////////////////////////////////// Static Methods ///////////////////////////////////////////////////

    /**
     * Loads a list of Shape frames for an animation.
     * @param baseName the base name of the animation frames
     * @param frameCount the number of frames
     * @param scale the scale factor for each frame
     * @return a list of Shape objects representing the animation frames
     */
    public static List<Shape> loadAnimation(String baseName, int frameCount, int scale) {
        List<Shape> frames = new ArrayList<>();
        for (int i = 0; i < frameCount; i++) {
            String name = baseName + "_" + i;
            frames.add(new Shape(name, 100 * scale, 100 * scale, 0, 0));
        }
        return frames;
    }
}