package GameEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um collider de um objeto.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (23/03/25)
 **/
public class LayerGroup {
    private int layer;
    private List<IGameObject> objects;

    /**
     * Construtor para o LayerGroup
     * @param layer camada do grupo
     */
    public LayerGroup(int layer) {
        this.layer = layer;
        objects = new ArrayList<>();
    }

    /**
     * Retorna a camada do grupo
     * @return camada do grupo
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Retorna a lista de objetos do grupo
     * @return lista de objetos do grupo
     */
    public List<IGameObject> getObjects() {
        return objects;
    }

    /**
     * Adiciona um objeto ao grupo
     * @param go objeto a ser adicionado
     */
    public void add(IGameObject go) {
        if (!objects.contains(go)) {
            objects.add(go);
        }
    }

    /**
     * Remove um objeto do grupo
     * @param go objeto a ser removido
     */
    public void remove(IGameObject go) {
        objects.remove(go);
    }

    /**
     * Verifica se o grupo contém um objeto
     * @param go objeto a ser verificado
     * @return true se o grupo contém o objeto, false caso contrário
     */
    public boolean contains(IGameObject go) {
        return objects.contains(go);
    }
}
