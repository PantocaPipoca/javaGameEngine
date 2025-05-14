package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GUI extends JFrame {
    private List<IGameObject> gameObjects = new CopyOnWriteArrayList<>(); // Objetos para renderizar
    private InputEvent ie = new InputEvent(); // Evento de entrada atual

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
                ie.keyPressed(e.getKeyCode()); // Atualizar estado no InputManager
            }

            @Override
            public void keyReleased(KeyEvent e) {
                ie.keyReleased(e.getKeyCode()); // Atualizar estado no InputManager
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ie.mouseButtonPressed(e.getButton()); // Atualizar estado no InputManager
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                ie.mouseButtonReleased(e.getButton()); // Atualizar estado no InputManager
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                ie.updateMousePosition(e.getX(), e.getY()); // Atualizar posição do mouse
            }
        });

        setVisible(true);
    }

    public InputEvent getIe() {
        return ie; // Retornar o evento de entrada atual
    }

    /**
     * Atualiza a lista de objetos a serem renderizados.
     * @param gameObjects lista de objetos habilitados
     */
    public void renderGameObjects(List<IGameObject> gameObjects) {
        this.gameObjects = new CopyOnWriteArrayList<>(gameObjects); // Atualizar a lista de objetos
        repaint(); // Solicitar a renderização
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

            // Renderização de cada GameObject
            for (IGameObject go : gameObjects) {
                if (go.transform() == null || go.shape() == null) continue;

                int x = (int) go.transform().position().x();
                int y = (int) go.transform().position().y();

                // Renderiza apenas se estiver visível
                if (x + 100 > 0 && x < getWidth() && y + 100 > 0 && y < getHeight()) {
                    go.shape().render(g, go.transform());
                }
            }
        }
    }
}