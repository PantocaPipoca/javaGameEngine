package GameEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um collider de um objeto.
 * @author Daniel Pantyukhov a83896 Gustavo Silva a83994 Alexandre Goncalves a83892
 * @version 1.0 (12/04/25)
 **/
public class GameEngine {
    private List<IGameObject> gameObjects;
    private List<LayerGroup> layerGroups;

    /**
     * Construtor para o GameEngine
     */
    public GameEngine() {
        gameObjects = new ArrayList<>();
        layerGroups = new ArrayList<>();
    }

    /**
     * Adiciona um objeto ao GameEngine
     * @param go objeto a ser adicionado
     */
    public void add(GameObject go) {
        gameObjects.add(go);
        int layer = go.transform().layer();
        LayerGroup group = getOrCreateLayerGroup(layer);
        group.add(go);
    }

    /**
     * Remove um objeto do GameEngine
     * @param go objeto a ser removido
     */
    public void destroy(GameObject go) {
        gameObjects.remove(go);
        LayerGroup group = getLayerGroup(go.transform().layer());
        if (group != null) { //Essa verificao pouco importa
            group.remove(go);
        }
    }

    /**
     * Simula o GameEngine por um numero de frames
     * @param frames
     */
    public void simulateFrames(int frames) {
        for (int i = 0; i < frames; i++) {
            for (IGameObject go : gameObjects) {
                int oldLayer = go.transform().layer();
                go.update();
                int newLayer = go.transform().layer();
                if (oldLayer != newLayer) {
                    updateObjectLayer(go, oldLayer, newLayer);
                }
            }
        }
    }

    /**
     * Atualiza a camada de um objeto
     * @param go objeto a ser atualizado
     * @param oldLayer o layer antigo
     * @param newLayer o novo layer
     */
    private void updateObjectLayer(IGameObject go, int oldLayer, int newLayer) {
        LayerGroup oldGroup = getLayerGroup(oldLayer);
        if (oldGroup != null) {
            oldGroup.remove(go);
        }
        LayerGroup newGroup = getOrCreateLayerGroup(newLayer);
        newGroup.add(go);
    }

    /**
     * Retorna o grupo de camadas correspondente a um layer
     * @param layer layer a ser verificado
     * @return o grupo de camadas correspondente
     */
    private LayerGroup getLayerGroup(int layer) {
        for (LayerGroup group : layerGroups) {
            if (group.getLayer() == layer) {
                return group;
            }
        }
        return null;
    }

    /**
     * Cria um grupo de camadas caso ele não exista
     * @param layer layer a ser verificado
     * @return o grupo de camadas correspondente
     */
    private LayerGroup getOrCreateLayerGroup(int layer) {
        LayerGroup group = getLayerGroup(layer);
        if (group == null) {
            group = new LayerGroup(layer);
            layerGroups.add(group);
        }
        return group;
    }

    /**
     * Verifica as colisões entre os objetos do GameEngine
     * @return uma lista de strings com os resultados das colisões
     */
    public List<String> getColisoes() {
        List<String> resultados = new ArrayList<>();
        for (LayerGroup group : layerGroups) {
            resultados.addAll(verificarColisoesEmGrupo(group.getObjects()));
        }
        return resultados;
    }

    /**
     * Verifica as colisões entre os objetos de um grupo
     * @param grupo grupo de objetos a ser verificado
     * @return uma lista de strings com os resultados das colisões
     */
    private List<String> verificarColisoesEmGrupo(List<IGameObject> grupo) {
        List<String> resultadosGrupo = new ArrayList<>();
        int size = grupo.size();
        for (int i = 0; i < size; i++) {
            IGameObject go1 = grupo.get(i);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < size; j++) {
                if (i == j) continue;
                IGameObject go2 = grupo.get(j);
                if (go1.collider().colide(go2.collider())) {
                    sb.append(" ").append(go2.name());
                }
            }
            if (sb.length() > 0) {
                resultadosGrupo.add(go1.name() + sb.toString());
            }
        }
        return resultadosGrupo;
    }
}
