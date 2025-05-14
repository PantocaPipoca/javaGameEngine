package GameEngine;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class InputEvent {
    private Set<Integer> pressedKeys = new HashSet<>(); // Teclas atualmente pressionadas
    private Set<Integer> pressedMouseButtons = new HashSet<>(); // Botões do mouse pressionados
    private Point mousePosition = new Point(0, 0); // Posição atual do mouse

    /**
     * Atualiza o estado das teclas pressionadas.
     * @param keyCode código da tecla pressionada
     */
    public void keyPressed(int keyCode) {
        pressedKeys.add(keyCode);
    }

    /**
     * Atualiza o estado das teclas liberadas.
     * @param keyCode código da tecla liberada
     */
    public void keyReleased(int keyCode) {
        pressedKeys.remove(keyCode);
    }

    /**
     * Atualiza o estado dos botões do mouse pressionados.
     * @param button código do botão do mouse pressionado
     */
    public void mouseButtonPressed(int button) {
        pressedMouseButtons.add(button);
    }

    /**
     * Atualiza o estado dos botões do mouse liberados.
     * @param button código do botão do mouse liberado
     */
    public void mouseButtonReleased(int button) {
        pressedMouseButtons.remove(button);
    }

    /**
     * Atualiza a posição atual do mouse.
     * @param x posição X do mouse
     * @param y posição Y do mouse
     */
    public void updateMousePosition(int x, int y) {
        mousePosition.setLocation(x, y);
    }

    /**
     * Verifica se uma tecla está pressionada.
     * @param keyCode código da tecla
     * @return true se a tecla estiver pressionada, false caso contrário
     */
    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    /**
     * Verifica se um botão do mouse está pressionado.
     * @param button código do botão do mouse
     * @return true se o botão estiver pressionado, false caso contrário
     */
    public boolean isMouseButtonPressed(int button) {
        return pressedMouseButtons.contains(button);
    }

    /**
     * Retorna a posição atual do mouse.
     * @return posição do mouse como um objeto Point
     */
    public Point getMousePosition() {
        return mousePosition;
    }
}