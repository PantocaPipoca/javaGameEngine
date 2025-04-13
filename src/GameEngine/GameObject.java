package GameEngine;

import Figuras.FiguraGeometrica;
import Figuras.Ponto;

/**
 * Classe que representa um objeto de jogo.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (12/04/25)
 **/
public class GameObject implements IGameObject {
    private final FiguraGeometrica originalFigura;
    private String name;
    private ITransform transform;
    private ICollider collider;
    private Movement movement;
    
    /**
     * Construtor para um objeto de jogo
     * @param name nome do objeto
     * @param transform transformacao do objeto
     * @param figura figura geometrica do objeto
     * @param movement movimento do objeto
     */
    public GameObject(String name, ITransform transform, FiguraGeometrica figura, Movement movement) {
        this.movement = movement;
        this.name = name;
        this.transform = transform;
        this.originalFigura = figura;
        this.collider = figura.colliderInit(transform);
    }
    
    public String name() {
        return name;
    }
    
    public ITransform transform() {
        return transform;
    }
    
    public ICollider collider() {
        return collider;
    }
    
    public String toString() {
        return name + "\n" + transform.toString() + "\n" + collider.toString();
    }

    /**
     * Atualiza o objeto de jogo
     */
    public void update() {
        transform.move(new Ponto(movement.dx(), movement.dy()), movement.dLayer());
        transform.rotate(movement.dAngle());
        transform.scale(movement.dScale());
        updateCollider();
    }

    /**
     * Atualiza o collider do objeto de jogo
     */
    public void updateCollider() {
        collider = originalFigura.colliderInit(transform);
    }

}