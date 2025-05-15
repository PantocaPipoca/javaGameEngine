package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import Game.Camera;

public class GUI extends JFrame {
    private List<IGameObject> gameObjects = new CopyOnWriteArrayList<>(); // Objetos para renderizar
    private InputEvent ie = new InputEvent(); // Evento de entrada atual
    private Camera camera;

    public GUI() {
        setTitle("Game Engine GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de renderização
        GameCanvas canvas = new GameCanvas();
        add(canvas, BorderLayout.CENTER);

        // Adicionar listeners para teclado e mouse
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
    }

    public InputEvent ie() {
        return ie;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Atualiza a lista de objetos a serem renderizados.
     * @param gameObjects lista de objetos habilitados
     */
    public void renderGameObjects(List<IGameObject> gameObjects) {
        this.gameObjects = new CopyOnWriteArrayList<>(gameObjects);
        repaint();
    }

    /**
     * Painel de renderização para os objetos do jogo.
     */
    private class GameCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Fundo
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Camera
            double camX = 0, camY = 0;
            if (camera != null) {
                camX = camera.getPosition().x();
                camY = camera.getPosition().y();
            }
            int screenCX = getWidth() / 2;
            int screenCY = getHeight() / 2;

            Graphics2D g2 = (Graphics2D) g;
            for (IGameObject go : gameObjects) {
                if (go.transform() == null || go.shape() == null) continue;

                // world coords
                double wx = go.transform().position().x();
                double wy = go.transform().position().y();

                // desired screen coords
                int drawX = (int) ((wx - camX) + screenCX);
                int drawY = (int) ((wy - camY) + screenCY);

                // quick cull
                if (drawX + 100 < 0 || drawX - 100 > getWidth() ||
                    drawY + 100 < 0 || drawY - 100 > getHeight()) {
                    continue;
                }

                // 1) save original transform
                AffineTransform old = g2.getTransform();

                // 2) translate so world(wx,wy) → screen(drawX,drawY)
                g2.translate(drawX - wx, drawY - wy);

                // 3) draw sprite
                go.shape().render(g2, go.transform());

                // 4) debug outline (always on)
                if (go.collider() instanceof ColliderCircle) {
                    ((ColliderCircle) go.collider()).drawOutline(g2);
                } else if (go.collider() instanceof ColliderPolygon) {
                    ((ColliderPolygon) go.collider()).drawOutline(g2);
                }

                // 5) restore
                g2.setTransform(old);
            }
        }
    }
}
