package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import Game.Camera;
import Game.UI.GameUI;

/**
 * Class that represents the main GUI window for the game engine.
 * Handles rendering, input events, and camera management.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (17/05/25)
 * @inv GUI must always have a valid InputEvent and render enabled game objects.
 */
public class GUI extends JFrame {

    private List<IGameObject> gameObjects = new CopyOnWriteArrayList<>(); // Objects to render
    private InputEvent ie = new InputEvent(); // Current input event
    private Camera camera;

    /**
     * Constructs the GUI window, sets up rendering panel and input listeners.
     */
    public GUI() {
        setTitle("Enter The Ualg");
        setSize(16*80, 9*80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Rendering panel
        GameCanvas canvas = new GameCanvas();
        add(canvas, BorderLayout.CENTER);

        // Keyboard listeners
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                ie.keyPressed(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                ie.keyReleased(e.getKeyCode());
            }
        });

        // Mouse listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ie.mouseButtonPressed(e.getButton());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ie.mouseButtonReleased(e.getButton());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                ie.updateMousePosition(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                ie.updateMousePosition(e.getX(), e.getY());
            }
        });

        setVisible(true);

        // Hide the default cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image invisibleCursorImg = toolkit.createImage(new byte[0]);
        Cursor invisibleCursor = toolkit.createCustomCursor(invisibleCursorImg, new Point(0, 0), "invisible");
        setCursor(invisibleCursor);
    }

    ///////////////// Inner Classes /////////////////

    /**
     * Inner class for the rendering panel.
     */
    private class GameCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Background
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Camera
            double camX = 0, camY = 0;
            if (camera != null) {
                camX = camera.position().x();
                camY = camera.position().y();
            }
            int screenCX = getWidth() / 2;
            int screenCY = getHeight() / 2;

            Graphics2D g2 = (Graphics2D) g;
            for (IGameObject go : gameObjects) {

                // Draw wall polygons
                if (go.name().equals("wall")) {
                    ColliderPolygon col = (ColliderPolygon) go.collider();
                    List<Figures.Point> pontos = col.points();

                    int[] xs = new int[pontos.size()];
                    int[] ys = new int[pontos.size()];
                    for (int i = 0; i < pontos.size(); i++) {
                        Figures.Point p = pontos.get(i);
                        xs[i] = (int) (p.x() - camX + screenCX);
                        ys[i] = (int) (p.y() - camY + screenCY);
                    }

                    g.setColor(Color.BLACK);
                    g.fillPolygon(xs, ys, pontos.size());
                }
                else if (go.name().equals("door")) {
                    ColliderPolygon col = (ColliderPolygon) go.collider();
                    List<Figures.Point> pontos = col.points();

                    int[] xs = new int[pontos.size()];
                    int[] ys = new int[pontos.size()];
                    for (int i = 0; i < pontos.size(); i++) {
                        Figures.Point p = pontos.get(i);
                        xs[i] = (int) (p.x() - camX + screenCX);
                        ys[i] = (int) (p.y() - camY + screenCY);
                    }

                    g.setColor(Color.RED);
                    g.fillPolygon(xs, ys, pontos.size());
                }

                if (go.transform() == null || go.shape() == null) continue;

                // World coordinates
                double wx = go.transform().position().x();
                double wy = go.transform().position().y();

                // Desired screen coordinates
                int drawX = (int) ((wx - camX) + screenCX);
                int drawY = (int) ((wy - camY) + screenCY);

                // Quick cull
                if (drawX + 100 < 0 || drawX - 100 > getWidth() ||
                    drawY + 100 < 0 || drawY - 100 > getHeight()) {
                    continue;
                }

                // 1) Save original transform
                AffineTransform old = g2.getTransform();

                // 2) Translate so world(wx,wy) → screen(drawX,drawY)
                double offsetX = screenCX - camX;
                double offsetY = screenCY - camY;
                g2.translate(offsetX, offsetY);

                // 3) Draw sprite
                go.shape().render(g2, go.transform(), go.isFlipped(), go.transform().angle());

                // 4) Debug outline (always on)
                if (go.collider() instanceof ColliderCircle) {
                    ((ColliderCircle) go.collider()).drawOutline(g2);
                } else if (go.collider() instanceof ColliderPolygon) {
                    ((ColliderPolygon) go.collider()).drawOutline(g2);
                }

                // 5) Restore
                g2.setTransform(old);

                // 6) Draw crosshair at mouse position (in world coordinates)
                try {
                    Image crosshair = new ImageIcon("sprites/crosshair.png").getImage();
                    Point mouseWorld = ie.mouseWorldPosition();

                    int crossX = (int) ((mouseWorld.x - camX) + screenCX);
                    int crossY = (int) ((mouseWorld.y - camY) + screenCY);

                    int size = 32;
                    g.drawImage(crosshair, crossX - size / 2, crossY - size / 2, size, size, null);
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem da mira: " + e.getMessage());
                }
            }

            GameUI.getInstance().render(g2);
        }
    }

    /**
     * Updates the list of game objects to be rendered and repaints the canvas.
     * @param gameObjects list of enabled game objects
     */
    public void renderGameObjects(List<IGameObject> gameObjects) {
        this.gameObjects = new CopyOnWriteArrayList<>(gameObjects);
        repaint();
    }

    ///////////////// Getters /////////////////

    /**
     * Gets the current InputEvent instance.
     * @return the InputEvent
     */
    public InputEvent ie() {
        return ie;
    }

    ///////////////// Setters /////////////////

    /**
     * Sets the camera used for rendering.
     * @param camera the Camera instance
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}