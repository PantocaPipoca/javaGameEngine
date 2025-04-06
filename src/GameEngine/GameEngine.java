package GameEngine;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private List<IGameObject> gameObjects;
    private List<LayerGroup> layerGroups;

    public GameEngine() {
        gameObjects = new ArrayList<>();
        layerGroups = new ArrayList<>();
    }

    public void add(GameObject go) {
        gameObjects.add(go);
        int layer = go.transform().layer();
        LayerGroup group = getOrCreateLayerGroup(layer);
        group.add(go);
    }

    public void destroy(GameObject go) {
        gameObjects.remove(go);
        LayerGroup group = getLayerGroup(go.transform().layer());
        if (group != null) { //Essa verificao pouco importa
            group.remove(go);
        }
    }

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

    private void updateObjectLayer(IGameObject go, int oldLayer, int newLayer) {
        LayerGroup oldGroup = getLayerGroup(oldLayer);
        if (oldGroup != null) {
            oldGroup.remove(go);
        }
        LayerGroup newGroup = getOrCreateLayerGroup(newLayer);
        newGroup.add(go);
    }

    private LayerGroup getLayerGroup(int layer) {
        for (LayerGroup group : layerGroups) {
            if (group.getLayer() == layer) {
                return group;
            }
        }
        return null;
    }

    private LayerGroup getOrCreateLayerGroup(int layer) {
        LayerGroup group = getLayerGroup(layer);
        if (group == null) {
            group = new LayerGroup(layer);
            layerGroups.add(group);
        }
        return group;
    }

    public List<String> getColisoes() {
        List<String> resultados = new ArrayList<>();
        for (LayerGroup group : layerGroups) {
            resultados.addAll(verificarColisoesEmGrupo(group.getObjects()));
        }
        return resultados;
    }

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
